package com.brikelos.model.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {

    public static MongoDatabase connect() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://" + MongoCredentials.getCredentials().get("username") + ":" + MongoCredentials.getCredentials().get("password") + "@cluster0.4nn4i.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Brikelos");

        return database;
    }


    public static void close() {
        mongoClient.close();
    }

    private static MongoClient mongoClient;
}
