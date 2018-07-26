/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.repositories.utils;

import com.cognitiva.models.login.Usuario;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

/**
 *
 * @author jgarfias2
 */
public class CursorsToObjects {

    public Usuario cursorToObj(MongoCursor<Document> cursor, Usuario usuario) {

         try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                usuario.setName(doc.get("username").toString());
                usuario.setPassword(doc.get("password").toString());
                usuario.setRole(doc.get("role").toString());
                
            }
        } catch (Exception e) {
            System.out.println("Error : " + e.toString());
        } finally {
            cursor.close();
        }
        return usuario;
    }
}
