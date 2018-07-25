/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.repository;

import com.cognitiva.la.model.mongodb.Campaign;
import com.cognitiva.la.model.mongodb.CampaignCliente;
import com.cognitiva.la.model.mongodb.Cliente;
import com.cognitiva.la.model.mongodb.ClienteCuenta;
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
import static com.mongodb.client.model.Filters.in;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author jgarfias
 */
public class ClienteRepository {

    public String insertMany(List<Document> documents) {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");

        MongoCollection<Document> collection = database.getCollection("Clientes");

        if (!documents.isEmpty()) {
            collection.insertMany(documents);
        }

        mongoClient.close();
        return "Insertados";
    }

    protected BasicDBObject makeBasicDBObjectClientEdit(Cliente cliente) {
        BasicDBObject documentClient = new BasicDBObject();
        documentClient.append("rfc", cliente.getRfc())
                .append("claveCliente", cliente.getClaveCliente())
                .append("nombreCliente", cliente.getNombreCliente())
                .append("calleNoCliente", cliente.getCalleNoCliente())
                .append("coloniaCliente", cliente.getColoniaCliente())
                .append("ciudadCliente", cliente.getCiudadCliente())
                .append("estado", cliente.getEstado())
                .append("codPostal", cliente.getCodPostal())
                .append("telSms", cliente.getTelSms())
                .append("correo", cliente.getCorreo());

        return documentClient;
    }

    public Document makeBsonDocument(Cliente cliente) {
        Document doc = new Document("rfc", cliente.getRfc())
                .append("claveCliente", cliente.getClaveCliente())
                .append("nombreCliente", cliente.getNombreCliente())
                .append("calleNoCliente", cliente.getCalleNoCliente())
                .append("coloniaCliente", cliente.getColoniaCliente())
                .append("ciudadCliente", cliente.getCiudadCliente())
                .append("estado", cliente.getEstado())
                .append("codPostal", cliente.getCodPostal())
                .append("telSms", cliente.getTelSms())
                .append("correo", cliente.getCorreo());

        ArrayList< DBObject> array = new ArrayList< DBObject>();

        for (ClienteCuenta clienteCuenta : cliente.getClienteCuentas()) {
            BasicDBObject document = new BasicDBObject();
            document.put("noCuenta", clienteCuenta.getNoCuenta());
            document.put("fechaLimitePago", clienteCuenta.getFechaLimitePago());
            document.put("diasFaltantes", clienteCuenta.getDiasFaltantes());
            document.put("fechaAperturaCuenta", clienteCuenta.getFechaAperturaCuenta());
            document.put("montoMoroso", clienteCuenta.getMontoMoroso());
            document.put("montoAsignadoAgencia", clienteCuenta.getMontoAsignadoAgencia());
            document.put("pagoMinimo", clienteCuenta.getPagoMinimo());
            document.put("descProducto", clienteCuenta.getDescProducto());
            document.put("montoUltimoPago", clienteCuenta.getMontoUltimoPago());
            document.put("fechaUltimoPago", clienteCuenta.getFechaUltimoPago());
            document.put("fechaLimitePago", clienteCuenta.getFechaLimitePago());
            document.put("diaCorte", clienteCuenta.getDiaCorte());
            document.put("totalDeudor", clienteCuenta.getTotalDeudor());

            array.add(document);
        }

        doc.put("cuentas", array);

        return doc;
    }

    public List<Cliente> findByFechaLimitePago(Date fechaLimitePago) throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("ClienteCredito");
        MongoCursor<Document> cursor = collection.find(eq("fechaLimitePago", fechaLimitePago)).iterator();
        mongoClient.close();
        return this.makeClientesList(cursor, mongoClient);
    }

    public Campaign findByClaveCampaign(String clave) throws JsonProcessingException {
        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        Campaign camp = null;
        try {
            MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
            MongoCollection<Document> collection = database.getCollection("Campaigns");
            MongoCursor<Document> cursor = collection.find(eq("clave", clave)).iterator();
            CampaignRepository campaignRepository = new CampaignRepository();
            camp = campaignRepository.makeCampaign(cursor, mongoClient);
            CursorsToObjects cto = new CursorsToObjects();

            for (CampaignCliente cli : camp.getCampaignClientes()) {
                collection = database.getCollection("Clientes");
                cursor = collection.find(eq("claveCliente", cli.getClaveCliente())).iterator();
                Cliente cliente = cto.makeClientesList(cursor).get(0);
                cli.setTelSms(cliente.getTelSms());
            }
        } finally {
            mongoClient.close();
        }
        return camp;

    }

    public List<Cliente> makeClientesList(MongoCursor<Document> cursor, MongoClient mongoClient) throws JsonProcessingException {

        List<Cliente> clientes = new ArrayList<Cliente>();

        CursorsToObjects cto = new CursorsToObjects();

        clientes = cto.makeClientesList(cursor);

        mongoClient.close();
        return clientes;

    }

    public List<Cliente> findByRfc(String rfc) throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");
        MongoCursor<Document> cursor = collection.find(eq("rfc", rfc)).iterator();
        return this.makeClientesList(cursor, mongoClient);
    }

    public Cliente findClienteByClave(String claveCliente) throws JsonProcessingException {
        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");
        MongoCursor<Document> cursor = collection.find(eq("claveCliente", claveCliente)).iterator();
        List<Cliente> clientes = this.makeClientesList(cursor, mongoClient);
        mongoClient.close();
        return clientes.size() > 0 ? clientes.get(0) : null;
    }

    public Cliente findClienteByClaveAux(String claveCliente, MongoClient mongoClient) throws JsonProcessingException {

        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");
        MongoCursor<Document> cursor = collection.find(eq("claveCliente", claveCliente)).iterator();
        List<Cliente> clientes = this.makeClientesList(cursor, mongoClient);

        return clientes.size() > 0 ? clientes.get(0) : null;
    }

    public List<Cliente> findByClaveCliente(String claveCliente) throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");
        MongoCursor<Document> cursor = collection.find(eq("claveCliente", claveCliente)).iterator();
        return this.makeClientesList(cursor, mongoClient);
    }

    public Cliente findByTelAndNumCuenta(String tel, String NoCuenta) throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");
        MongoCursor<Document> cursor = collection.find(and(eq("telSms", tel), eq("cuentas.noCuenta", NoCuenta))).iterator();
        mongoClient.close();
        Cliente cli;

        List<Cliente> clientes = this.makeClientesList(cursor, mongoClient);

        if (clientes.isEmpty()) {
            cli = null;
        } else {
            cli = clientes.get(0);
        }

        return cli;

    }

    public List<Cliente> findByRfcAndCuenta(String rfc, String NoCuenta) throws JsonProcessingException {
        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");
        MongoCursor<Document> cursor = collection.find(
                and(eq("rfc", rfc), eq("cuentas.noCuenta", NoCuenta))
        ).iterator();
        return this.makeClientesList(cursor, mongoClient);
    }

    public List<Cliente> findByClaveClienteAndCuenta(String claveCliente, String NoCuenta) throws JsonProcessingException {
        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");
        MongoCursor<Document> cursor = collection.find(
                and(eq("claveCliente", claveCliente), eq("cuentas.noCuenta", NoCuenta))
        ).iterator();
        return this.makeClientesList(cursor, mongoClient);
    }

    public String insertCuenta(ClienteCuenta clienteCuenta, String claveCliente, String noCuenta) {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");

        BasicDBObject document = new BasicDBObject();
        document.put("noCuenta", clienteCuenta.getNoCuenta());
        document.put("fechaLimitePago", clienteCuenta.getFechaLimitePago());
        document.put("diasFaltantes", clienteCuenta.getDiasFaltantes());
        document.put("diaCorte", clienteCuenta.getDiaCorte());
        document.put("fechaAperturaCuenta", clienteCuenta.getFechaAperturaCuenta());
        document.put("montoMoroso", clienteCuenta.getMontoMoroso());
        document.put("totalDeudor", clienteCuenta.getTotalDeudor());
        document.put("montoAsignadoAgencia", clienteCuenta.getMontoAsignadoAgencia());
        //document.put("agenciaActual", clienteCuenta.getAgenciaActual());

        collection.updateOne(
                eq("claveCliente", claveCliente),
                new Document("$addToSet", new Document("cuentas", document))
        );
        mongoClient.close();
        return "Cuenta Agregada";

    }

    public Cliente editClient(Cliente client) throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");

        BasicDBObject documentClient = makeBasicDBObjectClientEdit(client);

        collection.updateOne(
                eq("claveCliente", client.getClaveCliente()),
                new Document("$set", documentClient)
        );
        mongoClient.close();
        return findClienteByClave(client.getClaveCliente());
    }

    public Cliente editAccountsClient(Cliente client) throws JsonProcessingException {
        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");

        Document documentClient = makeBsonDocument(client);
        collection.updateOne(
                eq("claveCliente", client.getClaveCliente()),
                new Document("$set", documentClient)
        );
        return findClienteByClave(client.getClaveCliente());
    }

    public List<Cliente> getClientes() throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");
        MongoCursor<Document> cursor = collection.find().iterator();
        return this.makeClientesList(cursor, mongoClient);
    }

    public List<Cliente> searchByKey(String key) throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");
        MongoCursor<Document> cursor = collection.find(
                eq("claveCliente", key)
        ).iterator();
        return this.makeClientesList(cursor, mongoClient);
    }

    public String addClientTel(String claveCliente, String telSms) {

        MongoConection mc = new MongoConection();
        try (MongoClient mongoClient = mc.getMongoClient()) {
            MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");

            MongoCollection<Document> collectionClientes = database.getCollection("Clientes");

            // Get the date today using Calendar object.
            collectionClientes.updateOne(eq("claveCliente", claveCliente), new Document("$set", new Document("telSms", telSms)));
            mongoClient.close();
        }

        return "";
    }

    public Cliente editClientCuenta(Cliente client) throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");

        BasicDBObject documentClient = makeBasicDBObjectClientCuenta(client);

        collection.updateOne(
                eq("claveCliente", client.getClaveCliente()),
                new Document("$set", documentClient)
        );
        mongoClient.close();
        return findClienteByClave(client.getClaveCliente());
    }

    protected BasicDBObject makeBasicDBObjectClientCuenta(Cliente cliente) {
        BasicDBObject documentClient = new BasicDBObject();
        documentClient
                .append("totalDeudor", cliente.getClienteCuentas().get(0).getTotalDeudor())
                .append("montoMoroso", cliente.getClienteCuentas().get(0).getMontoMoroso())
                .append("pagoMinimo", cliente.getClienteCuentas().get(0).getPagoMinimo())
                .append("fechaAperturaCuenta", cliente.getClienteCuentas().get(0).getFechaAperturaCuenta())
                .append("montoUltimoPago", cliente.getClienteCuentas().get(0).getMontoUltimoPago())
                .append("fechaUltimoPago", cliente.getClienteCuentas().get(0).getFechaUltimoPago())
                .append("fechaLimitePago", cliente.getClienteCuentas().get(0).getFechaLimitePago());

        return documentClient;
    }

    public Cliente updateClientCuenta(Cliente clientFromCsv, Cliente clienteForUpdate) throws JsonProcessingException {

        MongoConection mc = new MongoConection();

        try (MongoClient mongoClient = mc.getMongoClient()) {

            MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
            MongoCollection<Document> collection = database.getCollection("Clientes");

            ClienteCuenta cuentaFromCsv = clientFromCsv.getClienteCuentas().get(0);

            for (ClienteCuenta cuentaIter : clienteForUpdate.getClienteCuentas()) {

                if (cuentaIter.getNoCuenta().equals(cuentaFromCsv.getNoCuenta())) {
                    cuentaIter.setTotalDeudor(cuentaFromCsv.getTotalDeudor());
                    cuentaIter.setMontoMoroso(cuentaFromCsv.getMontoMoroso());
                    cuentaIter.setPagoMinimo(cuentaFromCsv.getPagoMinimo());
                    cuentaIter.setFechaAperturaCuenta(cuentaFromCsv.getFechaAperturaCuenta());
                    cuentaIter.setMontoUltimoPago(cuentaFromCsv.getMontoUltimoPago());
                    cuentaIter.setFechaUltimoPago(cuentaFromCsv.getFechaUltimoPago());
                    cuentaIter.setFechaLimitePago(cuentaFromCsv.getFechaLimitePago());
                }
            }

            Document documentClient = makeBsonDocument(clienteForUpdate);

            collection.updateOne(
                    eq("claveCliente", clienteForUpdate.getClaveCliente()),
                    new Document("$set", documentClient)
            );

            mongoClient.close();

        }

        return clienteForUpdate;
    }

    public List<Cliente> findByClaveClienteArray(ArrayList al) throws JsonProcessingException {

        MongoConection mc = new MongoConection();
        MongoClient mongoClient = mc.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SantanderClientesPagos");
        MongoCollection<Document> collection = database.getCollection("Clientes");
        MongoCursor<Document> cursor = collection.find(in("claveCliente", al)).iterator();
        CampaignRepository campaignRepository = new CampaignRepository();

        return this.makeClientesList(cursor, mongoClient);

    }
}
