package com.brikelos.model.Remote.queries;

import com.brikelos.model.Local.models.Client;
import com.brikelos.model.Local.queries.ClientQueries;
import com.brikelos.model.Remote.MongoConnection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Updates.set;

public class RemoteClientQueries {

    protected static long getActiveMongoClients() {
        MongoConnection mongoConnection = new MongoConnection();
        Document query = new Document("deleted", false);
        long count = mongoConnection.mongoClients.countDocuments(query);
        mongoConnection.close();

        return count;
    }

    protected static void checkClientsData() {
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

}
