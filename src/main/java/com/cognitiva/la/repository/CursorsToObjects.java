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
import com.cognitiva.la.model.mongodb.Gestion;
import com.cognitiva.la.model.mongodb.GestionCodigoResultado;
import com.cognitiva.la.model.mongodb.GestionPerfil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author jgarfias2
 */
public class CursorsToObjects {

    public Campaign makeCampaign(MongoCursor<Document> cursor) throws JsonProcessingException {
        ClienteRepository clientR = new ClienteRepository();
        Campaign campaign = new Campaign();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                campaign.setClave(doc.get("clave").toString());
                campaign.setFechaCreacion((Date) doc.get("fechaCreacion"));
                campaign.setFechaLimitePagoCampaign((Date) doc.get("fechaLimitePagoCampaign"));
                campaign.setNombre(doc.get("nombre").toString());
                campaign.setDescripcion(doc.get("descripcion").toString());

                try {
                    campaign.setIdCampaignsInitialMessages(doc.get("idCampaignsInitialMessages").toString());
                } catch (Exception e) {
                }

                ArrayList<Document> lista = (ArrayList<Document>) doc.get("clientes");

                List<CampaignCliente> campaignClientes = new ArrayList<>();

                for (Document doc2 : lista) {
                    CampaignCliente campaignCliente = new CampaignCliente();
                    campaignCliente.setClaveCliente(doc2.getString("claveCliente"));
                    campaignCliente.setNoCuenta(doc2.getString("noCuenta"));
                    campaignCliente.setStatus(doc2.getBoolean("status"));

                    campaignCliente.setFechaActividad(doc2.getDate("fechaActividad"));
                    campaignCliente.setFechaCreaPromesa(doc2.getDate("fechaCreaPromesa"));
                    campaignCliente.setFechaVencePromesa(doc2.getDate("fechaVencePromesa"));

                    try {
                        campaignCliente.setMontoPromesa(doc2.getDouble("montoPromesa"));
                    } catch (Exception e) {
                        campaignCliente.setMontoPromesa(0.0);
                    }

                    campaignClientes.add(campaignCliente);
                }

                campaign.setCampaignClientes(campaignClientes);
            }
        } finally {
            cursor.close();
        }
        return campaign;
    }

    public List<Cliente> makeClientesList(MongoCursor<Document> cursor) throws JsonProcessingException {

        List<Cliente> clientes = new ArrayList<Cliente>();

        if (cursor.hasNext()) {

            try {
                while (cursor.hasNext()) {
                    Cliente cliente = new Cliente();
                    Document doc = cursor.next();

                    cliente.setRfc((String) doc.get("rfc"));
                    cliente.setClaveCliente(doc.get("claveCliente").toString());
                    cliente.setCalleNoCliente((String) doc.get("calleNoCliente"));
                    cliente.setCiudadCliente((String) doc.get("ciudadCliente"));
                    cliente.setCodPostal((String) doc.get("codPostal"));
                    cliente.setColoniaCliente((String) doc.get("coloniaCliente"));
                    cliente.setCorreo((String) doc.get("correo"));
                    cliente.setEstado((String) doc.get("estado"));
                    cliente.setNombreCliente((String) doc.get("nombreCliente"));
                    try {
                        cliente.setTelSms(doc.get("telSms").toString());
                    } catch (Exception e) {
                        cliente.setTelSms("");
                    }

                    // Inicia Mapeo JsonObject para evitar el error de tipo de dato
                    // Document obtenido por mongo DB.
                    List<Object> obj = (List<Object>) (Object) doc.get("cuentas");
                    List<ClienteCuenta> clientesCuentas = new ArrayList<ClienteCuenta>();
                    for (Object obj2 : obj) {
                        ObjectMapper mapper = new ObjectMapper();

                        String jsonInString = mapper.writeValueAsString(obj2);
                        JSONObject root = new JSONObject(jsonInString);

                        ClienteCuenta clienteCuenta = new ClienteCuenta();

                        clienteCuenta.setNoCuenta(root.getString("noCuenta"));
                        clienteCuenta.setFechaAperturaCuenta(new Date(root.getLong("fechaAperturaCuenta")));
                        clienteCuenta.setMontoMoroso(root.getDouble("montoMoroso"));
                        clienteCuenta.setMontoAsignadoAgencia(root.getDouble("montoAsignadoAgencia"));
                        clienteCuenta.setDiasFaltantes(root.getInt("diasFaltantes"));

                        clienteCuenta.setFechaLimitePago(new Date(root.getLong("fechaLimitePago")));

                        try {
                            clienteCuenta.setDiaCorte(root.getInt("diaCorte"));
                        } catch (JSONException e) {
                            clienteCuenta.setDiaCorte(0);
                        }

                        try {
                            clienteCuenta.setDescProducto(root.getString("descProducto"));
                        } catch (JSONException e) {
                            clienteCuenta.setDescProducto("");
                        }

                        try {
                            clienteCuenta.setPagoMinimo(root.getDouble("pagoMinimo"));
                        } catch (JSONException e) {
                            clienteCuenta.setPagoMinimo(0.0);
                        }

                        try {
                            clienteCuenta.setTotalDeudor(root.getDouble("totalDeudor"));
                        } catch (JSONException e) {
                            clienteCuenta.setPagoMinimo(0.0);
                        }
                        clientesCuentas.add(clienteCuenta);
                    }
                    // Finaliza mapeo jsonObject.

                    cliente.setClienteCuentas(clientesCuentas);
                    clientes.add(cliente);
                }
            } finally {
                cursor.close();
            }
        }
        return clientes;

    }

    public Document objToDocumentCampaign(Campaign campaign) {

        Document doc = new Document("clave", campaign.getClave())
                .append("fechaCreacion", campaign.getFechaCreacion())
                .append("fechaLimitePagoCampaign", campaign.getFechaLimitePagoCampaign())
                .append("producto", campaign.getProducto())
                .append("nombre", campaign.getNombre())
                .append("descripcion", campaign.getDescripcion())
                .append("idCampaignsInitialMessages", campaign.getIdCampaignsInitialMessages());

        ArrayList< DBObject> array = new ArrayList< DBObject>();

        for (CampaignCliente cliente : campaign.getCampaignClientes()) {
            BasicDBObject document = new BasicDBObject();
            document.put("claveCliente", cliente.getClaveCliente());
            document.put("noCuenta", cliente.getNoCuenta());
            document.put("status", cliente.isStatus());
            document.put("fechaActividad", cliente.getFechaActividad());

            array.add(document);
        }

        doc.put("clientes", array);

        return doc;
    }

    public Document objToDocument(Gestion gestion) {

        Document doc = new Document("claveGestion", gestion.getClaveGestion())
                .append("tipo", gestion.getTipo())
                .append("nombre", gestion.getNombre())
                .append("descripcion", gestion.getDescripcion());

        ArrayList< DBObject> array = new ArrayList< DBObject>();

        for (GestionCodigoResultado gcr : gestion.getGestionCodigoResultados()) {
            BasicDBObject document = new BasicDBObject();
            document.put("codigo", gcr.getCodigo());
            document.put("descripcion", gcr.getDescripcion());

            array.add(document);
        }

        doc.put("codigoResultados", array);

        return doc;
    }

    public Document objToDocument(GestionPerfil gestion) {

        Document doc = new Document("clavePerfil", gestion.getClavePerfil())
                .append("claveGestion", gestion.getClaveGestion());

        ArrayList< DBObject> array = new ArrayList< DBObject>();

        for (GestionCodigoResultado gcr : gestion.getGestionCodigoResultados()) {
            BasicDBObject document = new BasicDBObject();
            document.put("codigo", gcr.getCodigo());
            document.put("descripcion", gcr.getDescripcion());

            array.add(document);
        }

        doc.put("codigoResultados", array);

        return doc;
    }

    public Gestion cursorToObj(MongoCursor<Document> cursor, Gestion gestion) {

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                gestion.setId(doc.get("_id").toString());
                gestion.setClaveGestion(doc.get("claveGestion").toString());
                gestion.setTipo(doc.get("tipo").toString());
                gestion.setNombre(doc.get("nombre").toString());
                gestion.setDescripcion(doc.get("descripcion").toString());

                ArrayList<Document> lista = (ArrayList<Document>) doc.get("codigoResultados");
                List<GestionCodigoResultado> lgcr = new ArrayList<>();

                for (Document doc2 : lista) {
                    GestionCodigoResultado gcr = new GestionCodigoResultado();
                    gcr.setCodigo(doc2.get("codigo").toString());
                    gcr.setDescripcion(doc2.get("descripcion").toString());
                    lgcr.add(gcr);
                }
                gestion.setGestionCodigoResultados(lgcr);
            }
        } catch (Exception e) {
            System.out.println("Error : " + e.toString());
        } finally {
            cursor.close();
        }

        return gestion;
    }

}
