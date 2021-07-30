package com.brikelos.model.Remote.queries;

public class MongoBackup {

    public static void Backup() {
        RemoteGlobalQueries.checkCollections();
        RemoteClientQueries.checkClientsData();
    }

}
