package com.brikelos.model.Remote;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {

    public MongoConnection() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://" + MongoCredentials.getCredentials().get("username") + ":" + MongoCredentials.getCredentials().get("password") + "@cluster0.4nn4i.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Brikelos");

        boolean connected = true;

        try {
            database.listCollectionNames().iterator();
        } catch (Exception e) {
            System.out.println("Invalid mongo credentials!");
            connected = false;
        }

        if(connected) {
//            System.out.println("Connected to mongoDB!");
            mongoDatabase  = database;
            mongoClients = mongoDatabase.getCollection("Clients");
            mongoSells   = mongoDatabase.getCollection("Sells");
            mongoConfig  = mongoDatabase.getCollection("Config");
        }

    }

    public MongoDatabase getDatabase() {
        return mongoDatabase;
    }


    public void close() {
        mongoClient.close();
//        System.out.println("Mongo connection closed.");
    }

    private MongoClient         mongoClient;
    public MongoDatabase       mongoDatabase;
    public MongoCollection     mongoClients;
    public MongoCollection     mongoSells;
    public MongoCollection     mongoConfig;
}
