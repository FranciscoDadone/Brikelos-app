package com.brikelos.model.Remote.queries;

import com.brikelos.view.JCustomOptionPane;

import javax.swing.*;

public class MongoBackup {

    public static void Backup() {
        RemoteGlobalQueries.checkCollections();

        boolean isClientsOutdated = RemoteClientQueries.isDatabaseOutdated();
        System.out.println("Done");
        boolean isSellsOutdated   = RemoteSellsQueries.isDatabaseOutdated();
        System.out.println("Done");

        if(isSellsOutdated || isClientsOutdated) {
            int res = JCustomOptionPane.confirmDialog("<html>Se ha detectado que la base de datos local está desactualizada.<br>¿Desea actualizarla?</html>", "Actualizar base de datos");
            if(res == JOptionPane.YES_OPTION) {
                if(isClientsOutdated) RemoteClientQueries.retrieveFromRemote();
                if(isSellsOutdated) RemoteSellsQueries.retrieveFromRemote();
            }
        }

    }

}
