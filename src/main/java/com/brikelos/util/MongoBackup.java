package com.brikelos.util;

import com.brikelos.model.mongo.MongoConnection;
import com.brikelos.model.SQLiteConnection;
import com.mongodb.client.MongoDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MongoBackup {

    public static void Backup() {

        boolean connected = true;

        mongoDatabase  = MongoConnection.connect();
        sqliteDatabase = SQLiteConnection.connect();

        try {
            mongoDatabase.listCollectionNames().iterator();
        } catch (Exception e) {
            System.out.println("Invalid mongo credentials!");
            connected = false;
        }

        if(connected) {
            System.out.println("Connected to mongoDB!");
            checkCollections();

            MongoConnection.close();
        }


        try {
            sqliteDatabase.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Checks if there are the same tables in the sqlite database and the mongo.
     * If the mongo leaks a table (collection) it creates it.
     */
    private static void checkCollections() {
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

        ArrayList<String> mongoCollections = new ArrayList<>();
        mongoDatabase.listCollectionNames().forEach((collection) -> {
            mongoCollections.add(collection);
        });

        for(String sqliteTableName : tables) {
            if(!sqliteTableName.equals("sqlite_sequence")) {
                if(!mongoCollections.contains(sqliteTableName)) {
                    mongoDatabase.createCollection(sqliteTableName);
                    System.out.println("Mongo collection created: " + sqliteTableName);
                }
            }
        }
    }

    private static MongoDatabase       mongoDatabase;
    private static java.sql.Connection sqliteDatabase;

}
