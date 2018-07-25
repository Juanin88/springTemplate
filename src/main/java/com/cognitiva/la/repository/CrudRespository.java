/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.repository;

import com.cognitiva.la.model.mongodb.Gestion;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author jgarfias2
 */
public class CrudRespository extends MongoConectionV2 {

    public CursorsToObjects cto = new CursorsToObjects();

    public boolean insert(Gestion ges) {

        try {
            MongoCollection<Document> collection = getDatabase().getCollection("Gestion");

            collection.insertOne(cto.objToDocument(ges));

        } catch (Exception e) {
            return false;
        } finally {
            closeConection();
        }

        return true;
    }

    public boolean update(Gestion ges) {

        try {
            MongoCollection<Document> collection = getDatabase().getCollection("Gestion");
            collection.updateOne(
                    new BasicDBObject("_id", new ObjectId(ges.getId())),
                    new Document("$set", cto.objToDocument(ges))
            );
        } catch (Exception e) {
            closeConection();

        }
        return true;
    }

    public Gestion findById(Gestion ges) {
        MongoCollection<Document> collection = getDatabase().getCollection("Gestion");
        Gestion gestion = new Gestion();
        try {
            MongoCursor<Document> cursor = collection.find(eq("_id", new ObjectId(ges.getId()))).iterator();
            gestion = cto.cursorToObj(cursor, new Gestion());            
        } finally {
            closeConection();
        }
        return gestion;
    }

}
