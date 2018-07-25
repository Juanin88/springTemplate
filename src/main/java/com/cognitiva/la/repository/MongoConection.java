/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

/**
 *
 * @author jgarfias
 */
public class MongoConection {

    private MongoClient mongoClient;

    public MongoConection() {

        //this.mongoClient = new MongoClient("localhost", 27017);
        this.mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://jgarfias:admin123571113@cluster0-ea6qn.mongodb.net/SantanderClientesPagos?connectTimeoutMS=3000&retryWrites=true"));

    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

}
