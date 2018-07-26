/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.repository;

import com.cognitiva.la.model.mongodb.Campaign;
import com.cognitiva.la.model.mongodb.CampaignCliente;
import com.cognitiva.la.model.mongodb.Cliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.primefaces.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author jgarfias
 */
@Service
public class CampaignRepository {

    public List<Campaign> getCampaigns() {

        List<Campaign> campaigns = new ArrayList<>();

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();

        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");

        MongoCollection<Document> collection = database.getCollection("Campaigns");

        MongoCursor<Document> cursor = collection.find().iterator();

        try {
            while (cursor.hasNext()) {
                Campaign camp = new Campaign();
                Document campaing = cursor.next();
                camp.setClave((String) campaing.get("clave"));
                camp.setFechaCreacion((Date) campaing.get("fechaCreacion"));
                camp.setFechaLimitePagoCampaign((Date) campaing.get("fechaLimitePagoCampaign"));
                camp.setProducto((String) campaing.get("producto"));
                campaigns.add(camp);
            }
        } finally {
            cursor.close();
        }
        mongoClient.close();
        return campaigns;
    }

    public Campaign addCampaign(Campaign campaign, List<CampaignCliente> campaignClientes) throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();

        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");

        MongoCollection<Document> collection = database.getCollection("Campaigns");

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();

        campaign.setFechaCreacion(today);
        campaign.setCampaignClientes(campaignClientes);

        CursorsToObjects cto = new CursorsToObjects();
        collection.insertOne(cto.objToDocumentCampaign(campaign));

        mongoClient.close();
        return campaign;
    }

    public Campaign findByClaveCampaign(String clave) throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Campaigns");

        //System.out.println("Clave : " + clave);
        MongoCursor<Document> cursor = collection.find(eq("clave", clave)).iterator();

        Campaign campaign = this.makeCampaign(cursor, mongoClient);

        return campaign;

    }

    public Campaign findByClaveCampaignWithClient(String clave) throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Campaigns");

        //System.out.println("Clave : " + clave);
        MongoCursor<Document> cursor = collection.find(eq("clave", clave)).iterator();

        CursorsToObjects cto = new CursorsToObjects();
        Campaign campaign = cto.makeCampaign(cursor);

        collection = database.getCollection("Clientes");
        Cliente cliente = new Cliente();

        for (CampaignCliente campCli : campaign.getCampaignClientes()) {
            cursor = collection.find(
                    eq("claveCliente", campCli.getClaveCliente())).iterator();

            cliente = cto.makeClientesList(
                    cursor).get(0);
            campCli.setCliente(cliente);
        }

        mongoClient.close();

        return campaign;

    }

    public Campaign makeCampaign(MongoCursor<Document> cursor, MongoClient mongoClient) throws JsonProcessingException {

        CursorsToObjects cto = new CursorsToObjects();
        Campaign campaign = cto.makeCampaign(cursor);

        return campaign;
    }

    public CampaignCliente mappingCampaignCliente(MongoCursor<Document> cursor) {
        CampaignCliente clienteData = new CampaignCliente();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                clienteData.setNoCuenta((String) doc.get("noCuenta"));
                clienteData.setClaveCliente((String) doc.get("claveCliente"));
            }
        } finally {
            cursor.close();
        }
        return clienteData;
    }

    public String insertClienteToCampaign(Cliente cliente) {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Campaigns");

        ArrayList< DBObject> array = new ArrayList<>();

        BasicDBObject document = new BasicDBObject();
        document.put("claveCliente", cliente.getClaveCliente());
        document.put("noCuenta", cliente.getClienteCuentas().get(0).getNoCuenta());
        document.put("status", true);

        //array.add(document);
        collection.updateOne(
                eq("clave", cliente.getClaveCampaign()),
                new Document("$addToSet", new Document("clientes", document))
        );
        mongoClient.close();

        return "Cliente Agregado";

    }

    public Campaign searchClienteOnCampaignByClaveAndCuenta(String claveCliente, String claveCampaign, String noCuenta) throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Campaigns");

        MongoCursor<Document> cursor = collection.find(
                and(
                        eq("clave", claveCampaign),
                        and(
                                eq("clientes.claveCliente", claveCliente),
                                eq("clientes.noCuenta", noCuenta)
                        )
                )).iterator();

        Campaign campaign = this.makeCampaign(cursor, mongoClient);

        return campaign;

    }

    public Campaign deleteClienteFromCampaign(Campaign campaignAux) throws JsonProcessingException {

        MongoConection mc = new MongoConection();

        try (MongoClient mongoClient = mc.getMongoClient()) {

            MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
            MongoCollection<Document> collection = database.getCollection("Campaigns");

            Campaign campaign = findByClaveCampaign(campaignAux.getClave());

            for (CampaignCliente clienteAux : campaignAux.getCampaignClientes()) {

                for (CampaignCliente cliente : campaign.getCampaignClientes()) {

                    if (clienteAux.getClaveCliente().equals(cliente.getClaveCliente()) && clienteAux.getNoCuenta().equals(cliente.getNoCuenta())) {
                        cliente.setStatus(false);
                    }
                }
            }

            CursorsToObjects cto = new CursorsToObjects();
            Document document = cto.objToDocumentCampaign(campaign);

            collection.updateOne(
                    eq("clave", campaign.getClave()),
                    new Document("$set", document)
            );

            mongoClient.close();

            return campaign;
        } catch (JsonProcessingException e) {
            return null;
        }

    }

    public Campaign update(Campaign campaign) {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        try {
            MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
            MongoCollection<Document> collection = database.getCollection("Campaigns");

            CursorsToObjects cto = new CursorsToObjects();

            Document document = cto.objToDocumentCampaign(campaign);

            collection.updateOne(
                    eq("clave", campaign.getClave()),
                    new Document("$set", document)
            );

        } finally {
            mongoClient.close();
            return campaign;
        }
    }

}
