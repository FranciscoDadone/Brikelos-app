package com.brikelos.model.Remote.queries;

import com.brikelos.model.Local.models.Purchase;
import com.brikelos.model.Local.queries.SellQueries;
import com.brikelos.model.Remote.MongoConnection;
import com.brikelos.view.JCustomOptionPane;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.swing.*;
import java.util.ArrayList;

import static com.mongodb.client.model.Updates.set;

public class RemoteSellsQueries {

    private static long getActiveMongoSells() {
        MongoConnection mongoConnection = new MongoConnection();
        Document query = new Document("deleted", false);
        long count = mongoConnection.mongoSells.countDocuments(query);
        mongoConnection.close();

        return count;
    }

    protected static boolean isDatabaseOutdated() {
        MongoConnection mongoConnection = new MongoConnection();
        System.out.println("Cheking sells on Mongo...");

        long localRegisteredSells = SellQueries.getActiveSells();
        long remoteRegisteredSells = getActiveMongoSells();

        if(localRegisteredSells > remoteRegisteredSells) { // makes the backup on remote
            SellQueries.getAllSells().forEach((sell) -> {
                Document mongoQuery = (Document) mongoConnection.mongoSells.find(Filters.eq("id", sell.getId())).first();
                if(mongoQuery == null) {
                    backupSell(sell);
                }
            });
        } else if(localRegisteredSells == 0 && localRegisteredSells != remoteRegisteredSells) { // if the local database is empty, retrieves from remote
            return true;
        }

        ArrayList<Purchase> remoteSells = getAllSells();
        for(Purchase localSells : SellQueries.getAllSells()) {
            if(remoteSells.contains(localSells)) break;
            for(Purchase remoteSell : remoteSells) {
                if(localSells.getId() == remoteSell.getId()) {
                    if(localSells.isDeleted() != remoteSell.isDeleted() ||
                            localSells.getId() != remoteSell.getId() ||
                            !localSells.getTitle().equals(remoteSell.getTitle()) ||
                            localSells.getBuyerID() != remoteSell.getBuyerID() ||
                            !localSells.getDate().equals(remoteSell.getDate()) ||
                            !localSells.getDescription().equals(remoteSell.getDescription()) ||
                            localSells.getPrice() != remoteSell.getPrice()) {
                        editSell(localSells);
                    }
                    break;
                }
            }
        }

        mongoConnection.close();
        return false;
    }

    public static void retrieveFromRemote() {
        getAllSells().forEach((remoteSell) -> {
            SellQueries.addSell(remoteSell);
        });

        SellQueries.getAllSells().forEach((localSell) -> {
            updateSellID(localSell);
        });
    }

    private static void updateSellID(Purchase sell) {
        System.out.println("Updating remote id of " + sell.getTitle());
        new Thread(() -> {
            MongoConnection mongoConnection = new MongoConnection();

            Bson filter = Filters.eq("date", sell.getDate());
            Bson updateOperation = set("id", sell.getId());
            mongoConnection.mongoSells.updateOne(filter, updateOperation);

            mongoConnection.close();
        }).start();
    }

    public static void backupSell(Purchase sell) {
        new Thread(() -> {
            MongoConnection mongoConnection = new MongoConnection();
            System.out.println("Making backup of sell " + sell.getTitle());
            mongoConnection.mongoSells.insertOne(new Document()
                    .append("id", sell.getId())
                    .append("title", sell.getTitle())
                    .append("date", sell.getDate())
                    .append("description", sell.getDescription())
                    .append("price", sell.getPrice())
                    .append("buyerID", sell.getBuyerID())
                    .append("deleted", sell.isDeleted())
            );
            mongoConnection.close();
        }).start();
    }

    public static void deleteSell(Purchase sell) {
        System.out.println("Deleting sell " + sell.getTitle());
        new Thread(() -> {
            MongoConnection mongoConnection = new MongoConnection();

            Bson filter = Filters.eq("id", sell.getId());
            Bson updateOperation = set("deleted", true);
            mongoConnection.mongoSells.updateOne(filter, updateOperation);

            mongoConnection.close();
        }).start();
    }

    public static void editSell(Purchase sell) {
        System.out.println("Editing sell " + sell.getTitle());
        new Thread(() -> {
            MongoConnection mongoConnection = new MongoConnection();

            Bson filter = Filters.eq("id", sell.getId());
            Bson updateTitle = set("title", sell.getTitle());
            Bson updateDate = set("date", sell.getDate());
            Bson updateDescription = set("description", sell.getDescription());
            Bson updatePrice = set("price", sell.getPrice());
            Bson updateBuyerID = set("buyerID", sell.getBuyerID());
            Bson updateDeleted = set("deleted", sell.isDeleted());

            Bson updates = Updates.combine(updateTitle, updateDate, updateDescription, updatePrice, updateBuyerID, updateDeleted);

            mongoConnection.mongoSells.updateOne(filter, updates);

            mongoConnection.close();
        }).start();
    }

    private static ArrayList<Purchase> getAllSells() {
        MongoConnection mongoConnection = new MongoConnection();
        FindIterable remoteSells = mongoConnection.mongoSells.find(Filters.eq("deleted", false));

        ArrayList<Purchase> sells = new ArrayList<>();
        remoteSells.forEach((sell) -> {
            sells.add(new Purchase(
                    ((Document)sell).getInteger("id"),
                    ((Document)sell).getInteger("buyerID"),
                    ((Document)sell).getString("date"),
                    ((Document)sell).getString("title"),
                    ((Document)sell).getString("description"),
                    ((Document)sell).getDouble("price"),
                    ((Document)sell).getBoolean("deleted")
            ));
        });

        mongoConnection.close();
        return sells;
    }

}
