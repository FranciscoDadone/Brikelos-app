package com.brikelos.model.Remote.queries;

import com.brikelos.model.Local.models.Client;
import com.brikelos.model.Local.queries.ClientQueries;
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

        long localRegisteredClients = ClientQueries.getAllClients().size();
        long remoteRegisteredClients = getActiveMongoClients();

        if(localRegisteredClients > remoteRegisteredClients) { // makes the backup un remote
            ClientQueries.getAllClients().forEach((client) -> {
                Document mongoQuery = (Document) mongoConnection.mongoClients.find(Filters.eq("name", client.getName())).first();
                if(mongoQuery == null) {
                    backupClient(client);
                }
            });
        } else if(localRegisteredClients == 0) { // if the local database is empty, retrieves from remote
            int res = JCustomOptionPane.confirmDialog("<html>Se ha detectado que la base de datos local está desactualizada.<br>¿Desea actualizarla?</html>", "Actualizar base de datos");
            if(res == JOptionPane.YES_OPTION) {
                getAllClients().forEach((remoteClient) -> {
                    ClientQueries.addClient(remoteClient);
                });

                ClientQueries.getAllClients().forEach((localClient) -> {
                    updateClientID(localClient);
                });

                int res1 = JCustomOptionPane.confirmDialog("<html>Se ha recuperado la información de manera exitosa.<br>¿Desea reiniciar la aplicación para aplicar los cambios?</html>", "Reiniciar");
                if(res1 == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }

        ArrayList<Client> remoteClients = getAllClients();
        for(Client localClient : ClientQueries.getAllClients()) {
            if(remoteClients.contains(localClient)) break;
            for(Client remoteClient : remoteClients) {
                if(localClient.getId() == remoteClient.getId()) {
                    editClient(localClient);
                    break;
                }
            }
        }

        mongoConnection.close();
    }

    private static void updateClientID(Client client) {
        System.out.println("Updating remote id of " + client.getName());
        new Thread(() -> {
            MongoConnection mongoConnection = new MongoConnection();

            Bson filter = Filters.eq("name", client.getName());
            Bson updateOperation = set("id", client.getId());
            mongoConnection.mongoClients.updateOne(filter, updateOperation);

            mongoConnection.close();
        }).start();
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

    private static ArrayList<Client> getAllClients() {
        MongoConnection mongoConnection = new MongoConnection();
        FindIterable remoteClients = mongoConnection.mongoClients.find(Filters.eq("deleted", false));

        ArrayList<Client> clients = new ArrayList<>();
        remoteClients.forEach((client) -> {
            clients.add(new Client(
                    ((Document)client).getInteger("id"),
                    ((Document)client).getString("name"),
                    ((Document)client).getString("phone"),
                    ((Document)client).getDouble("moneySpent"),
                    ((Document)client).getBoolean("deleted")
            ));
        });

        mongoConnection.close();
        return clients;
    }

}
