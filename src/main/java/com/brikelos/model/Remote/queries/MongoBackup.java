package com.brikelos.model.Remote.queries;

import com.brikelos.model.Remote.MongoConnection;
import com.brikelos.model.Remote.MongoStatus;
import com.brikelos.view.JCustomOptionPane;
import javax.swing.*;

public class MongoBackup {

    public static void Backup() {
        try {
            new MongoConnection().close();
        } catch (Exception e) {
            System.out.println("Please fill up the MongoDB credentials under Brikelos/database/mongoCrendentials.yml to backup the data! Or check your internet connection!");
        }
        if(MongoStatus.connected) {
            RemoteGlobalQueries.checkCollections();

            boolean isClientsOutdated = RemoteClientQueries.isDatabaseOutdated();
            System.out.println("Done");
            boolean isSellsOutdated   = RemoteSellsQueries.isDatabaseOutdated();
            System.out.println("Done");

            if(isSellsOutdated || isClientsOutdated) {
                int res = JCustomOptionPane.confirmDialog("<html>Se ha detectado que la base de datos local está desactualizada.<br>¿Desea actualizarla?</html>", "Actualizar base de datos");
                if(res == JOptionPane.YES_OPTION) {
                    if(isClientsOutdated) RemoteClientQueries.retrieveFromRemote();
                    if(isSellsOutdated)   RemoteSellsQueries.retrieveFromRemote();

                    int res1 = JCustomOptionPane.confirmDialog("<html>Se ha recuperado la información de manera exitosa.<br>¿Desea reiniciar la aplicación para aplicar los cambios?</html>", "Reiniciar");
                    if(res1 == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            }
        }
    }
}
