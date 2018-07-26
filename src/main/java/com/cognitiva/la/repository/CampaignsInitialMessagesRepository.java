/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.repository;

import com.cognitiva.la.model.mongodb.CampaignsInitialMessages;
import com.cognitiva.la.model.mongodb.CampaignsInitialMessagesVars;
import com.cognitiva.la.model.mongodb.Cliente;
import com.cognitiva.la.model.mongodb.ClienteCuenta;
import com.cognitiva.la.model.mongodb.Mensaje;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.primefaces.json.JSONObject;

/**
 *
 * @author jgarfias2
 */
public class CampaignsInitialMessagesRepository extends MongoConectionV2 {

    public void makeCall() {
    }

    public List<CampaignsInitialMessagesVars> getVars() {

        List<CampaignsInitialMessagesVars> listCampaignsInitialMessagesVars = new ArrayList<>();

        MongoCollection<Document> collection = getDatabase().getCollection("CampaignsInitialMessagesVars");

        MongoCursor<Document> cursor = collection.find().iterator();

        try {
            while (cursor.hasNext()) {
                CampaignsInitialMessagesVars campaignsInitialMessagesVars = new CampaignsInitialMessagesVars();
                Document curs = cursor.next();
                campaignsInitialMessagesVars.setClaveVar(curs.get("claveVar").toString());
                campaignsInitialMessagesVars.setNameVar(curs.get("nameVar").toString());

                listCampaignsInitialMessagesVars.add(campaignsInitialMessagesVars);
            }
        } finally {
            cursor.close();
        }
        closeConection();
        return listCampaignsInitialMessagesVars;
    }

    public Document objToDocument(CampaignsInitialMessages cim) {

        Document doc = new Document("descripcion", cim.getDescripcion())
                .append("nombreMsg", cim.getNombreMsg());

        ArrayList< DBObject> array = new ArrayList< DBObject>();

        for (Mensaje msj : cim.getMensajes()) {
            BasicDBObject document = new BasicDBObject();
            document.put("content", msj.getContent());
            document.put("order", msj.getOrder());
            document.put("type", msj.getType());
            array.add(document);
        }

        doc.put("mensajes", array);

        return doc;
    }

    public boolean insert(CampaignsInitialMessages cim) {

        try {
            MongoCollection<Document> collection = getDatabase().getCollection("CampaignsInitialMessages");
            collection.insertOne(objToDocument(cim));
        } catch (Exception e) {
            return false;
        } finally {
            closeConection();
        }

        return true;
    }

    public List<CampaignsInitialMessages> update(CampaignsInitialMessages cim) throws JsonProcessingException {

        try {
            MongoCollection<Document> collection = getDatabase().getCollection("CampaignsInitialMessages");
            collection.updateOne(
                    new BasicDBObject("_id", new ObjectId(cim.getId())),
                    new Document("$set", objToDocument(cim))
            );
        } catch (Exception e) {
            closeConection();
            return null;
        }

        return getMensajesIniciales();
    }

    public List<CampaignsInitialMessages> getMensajesIniciales() throws JsonProcessingException {

        List<CampaignsInitialMessages> listCampaignsInitialMessages = new ArrayList<>();

        MongoCollection<Document> collection = getDatabase().getCollection("CampaignsInitialMessages");

        MongoCursor<Document> cursor = collection.find().iterator();

        try {
            while (cursor.hasNext()) {
                CampaignsInitialMessages campaignsInitialMessages = new CampaignsInitialMessages();
                Document doc = cursor.next();

                try {
                    campaignsInitialMessages.setId(doc.get("_id").toString());
                } catch (Exception e) {
                }

                try {
                    campaignsInitialMessages.setDescripcion(doc.get("descripcion").toString());

                } catch (Exception e) {
                }

                try {
                    campaignsInitialMessages.setNombreMsg(doc.get("nombreMsg").toString());

                } catch (Exception e) {
                }

                try {
                    campaignsInitialMessages.setMensajes(makeCampaignInitMsgs(doc));

                } catch (Exception e) {
                }

                listCampaignsInitialMessages.add(campaignsInitialMessages);
            }
        } finally {
            cursor.close();
        }

        closeConection();

        return listCampaignsInitialMessages;
    }

    public List<Mensaje> makeCampaignInitMsgs(Document doc) throws JsonProcessingException {

        List<Mensaje> mensajes = new ArrayList<>();
        List<Object> obj = (List<Object>) (Object) doc.get("mensajes");
        List<Mensaje> msj = new ArrayList<Mensaje>();
        for (Object obj2 : obj) {
            ObjectMapper mapper = new ObjectMapper();

            String jsonInString = mapper.writeValueAsString(obj2);
            JSONObject root = new JSONObject(jsonInString);

            Mensaje msj2 = new Mensaje();
            msj2.setContent(root.getString("content"));
            msj2.setOrder(root.getInt("order"));
            msj2.setType(root.getString("type"));
            mensajes.add(msj2);

        }

        return mensajes;
    }

    public CampaignsInitialMessages findById(String id) throws JsonProcessingException {

        MongoCollection<Document> collection = getDatabase().getCollection("CampaignsInitialMessages");
        CampaignsInitialMessages cim = new CampaignsInitialMessages();

        try {
            MongoCursor<Document> cursor = collection.find(eq("_id", new ObjectId(id))).iterator();
            cim = cursorToObj(cursor);

        } finally {
            closeConection();

        }
        return cim;
    }

    public CampaignsInitialMessages cursorToObj(MongoCursor<Document> cursor) throws JsonProcessingException {

        CampaignsInitialMessages cim = new CampaignsInitialMessages();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                cim.setId(doc.get("_id").toString());
                cim.setDescripcion(doc.get("descripcion").toString());
                cim.setNombreMsg(doc.get("nombreMsg").toString());

                ArrayList<Document> lista = (ArrayList<Document>) doc.get("mensajes");
                List<Mensaje> mensajes = new ArrayList<>();

                for (Document doc2 : lista) {
                    Mensaje mensaje = new Mensaje();

                    mensaje.setContent(doc2.get("content").toString());
                    mensaje.setOrder((int) doc2.get("order"));
                    mensaje.setType(doc2.get("type").toString());

                    mensajes.add(mensaje);
                }

                cim.setMensajes(mensajes);

            }
        } finally {
            cursor.close();
        }

        return cim;

    }

    public String makeInitialSentence(Cliente cliente, String id, String noCuenta) {

        MongoCollection<Document> collection = getDatabase().getCollection("CampaignsInitialMessages");

        ClienteCuenta ccAux = new ClienteCuenta();

        for (ClienteCuenta cc : cliente.getClienteCuentas()) {
            if (cc.getNoCuenta().equals(noCuenta)) {
                ccAux = cc;
            }
        }

        try {
            MongoCursor<Document> cursor = collection.find(eq("_id", new ObjectId(id))).iterator();
            CampaignsInitialMessages cim = cursorToObj(cursor);

            UtilsRepository utils = new UtilsRepository();
            String text = "";
            text = utils.makeSentence(cliente, ccAux, cim);

            closeConection();
            return text;
        } catch (JsonProcessingException e) {
            closeConection();
            return "Enunciado.";
        }

    }

}
