package com.brikelos.util;

import com.brikelos.model.models.Client;
import com.brikelos.model.mongo.MongoConnection;
import com.brikelos.model.SQLiteConnection;
import com.brikelos.model.queries.ClientQueries;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.mongodb.client.model.Updates.set;

public class MongoBackup {

    public static void Backup() {
        checkCollections();
        checkClientsData();
    }

    private static long getActiveMongoClients() {
        MongoConnection mongoConnection = new MongoConnection();
        Document query = new Document("deleted", false);
        long count = mongoConnection.mongoClients.countDocuments(query);
        mongoConnection.close();

        return count;
    }

    /**
     * Checks if there are the same tables in the sqlite database and the mongo.
     * If the mongo leaks a table (collection) it creates it.
     */
    private static void checkCollections() {
        sqliteDatabase = SQLiteConnection.connect();
        ResultSet res;
        ArrayList<String> tables = new ArrayList<>();
        try {
            res = sqliteDatabase.createStatement().executeQuery("SELECT name FROM sqlite_master WHERE type='table';");
            while(res.next()) {
                tables.add(res.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            sqliteDatabase.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        MongoConnection mongoConnection = new MongoConnection();
        ArrayList<String> mongoCollections = new ArrayList<>();
        mongoConnection.mongoDatabase.listCollectionNames().forEach((collection) -> {
            mongoCollections.add(collection);
        });

        for(String sqliteTableName : tables) {
            if(!sqliteTableName.equals("sqlite_sequence")) {
                if(!mongoCollections.contains(sqliteTableName)) {
                    mongoConnection.mongoDatabase.createCollection(sqliteTableName);
                    System.out.println("Mongo collection created: " + sqliteTableName);
                }
            }
        }
        mongoConnection.close();
    }

    private static void checkClientsData() {
        MongoConnection mongoConnection = new MongoConnection();
        System.out.println("Cheking clients on Mongo...");
        ClientQueries.getAllClients().forEach((client) -> {
            Document mongoQuery = (Document) mongoConnection.mongoClients.find(Filters.eq("name", client.getName())).first();
            if(mongoQuery == null) {
                backupClient(client);
            }
        });

        mongoConnection.close();
    }

    public static void backupClient(Client client) {
        new Thread(() -> {
            MongoConnection mongoConnection = new MongoConnection();
            System.out.println("Making backup of client " + client.getName());
            mongoConnection.mongoClients.insertOne(new Document()
                    .append("id", client.getId())
                    .append("name", client.getName())
                    .append("phone", client.getPhone())
                    .append("moneySpent", client.getMoneySpent())
                    .append("deleted", false)
            );
            mongoConnection.close();
        }).start();
    }

    public static void deleteClient(Client client) {
        System.out.println("Deleting client " + client.getName());
        new Thread(() -> {
            MongoConnection mongoConnection = new MongoConnection();

            Bson filter = Filters.eq("name", client.getName());
            Bson updateOperation = set("deleted", true);
            mongoConnection.mongoClients.updateOne(filter, updateOperation);

            mongoConnection.close();
        }).start();
    }

    public static void editClient(Client client) {
        System.out.println("Editing client " + client.getName());
        new Thread(() -> {
            MongoConnection mongoConnection = new MongoConnection();

            Bson filter = Filters.eq("id", client.getId());
            Bson updateName = set("name", client.getName());
            Bson updatePhone = set("phone", client.getPhone());
            Bson updateMoneySpent = set("moneySpent", client.getMoneySpent());
            Bson updates = Updates.combine(updateName, updatePhone, updateMoneySpent);

            mongoConnection.mongoClients.updateOne(filter, updates);

            mongoConnection.close();
        }).start();
    }

    private static java.sql.Connection sqliteDatabase;

}
