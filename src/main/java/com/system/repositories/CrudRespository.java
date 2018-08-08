/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.repositories;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.system.models.login.Usuario;
import com.system.repositories.utils.CursorsToObjects;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author jgarfias2
 */
public class CrudRespository extends MongoConfig {

    public CursorsToObjects cto = new CursorsToObjects();

    public Usuario findByName(String username) {
        MongoCollection<Document> collection = getDatabase().getCollection("Usuarios");
        Usuario u = new Usuario();
        try {
            MongoCursor<Document> cursor = collection.find(eq("username", username)).iterator();
            u = cto.cursorToObj(cursor, new Usuario());
        } catch (Exception e) {
            System.out.println("Error " + e.toString());
        } finally {
            closeConection();
        }
        return u;
    }

}
