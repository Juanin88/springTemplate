/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inicio.repositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author jgarfias
 */
public class MongoConectionV2 {

    private MongoDatabase database;

    private MongoClient mongoClient;

    public MongoConectionV2() {

        //this.mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://jgarfias:admin123571113@cluster0-ea6qn.mongodb.net/SantanderClientesPagos?connectTimeoutMS=3000&retryWrites=true"));
        this.mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("SantanderClientesPagos");

        setDatabase(database);
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }

    public void closeConection() {
        mongoClient.close();
    }

}
