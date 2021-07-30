package com.brikelos.controller;

import com.brikelos.model.Local.models.Purchase;
import com.brikelos.model.Local.queries.ClientQueries;
import com.brikelos.model.Local.queries.SellQueries;
import com.brikelos.model.Remote.queries.MongoBackup;
import com.brikelos.model.Remote.queries.RemoteClientQueries;
import com.brikelos.view.JCustomOptionPane;
import com.brikelos.view.ShowClientsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteClient implements ActionListener {
    public DeleteClient(ShowClientsPanel view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int res = JCustomOptionPane.confirmDialog(
                "<html>Seguro que quiere eliminar el cliente:<br><b>" + Cache.selectedClient.getName() + "</b></html>",
                "Confirmar"
        );

        if(res == JOptionPane.YES_OPTION) {

            // Deleting all purchases prior to client deletion.
            ArrayList<Purchase> purchases = SellQueries.getAllClientSells(Cache.selectedClient);
            if(!purchases.isEmpty()) {
                purchases.forEach((purchase) -> {
                    SellQueries.deletePurchase(purchase);
                });
            }

            ClientQueries.deleteClient(Cache.selectedClient);
            RemoteClientQueries.deleteClient(Cache.selectedClient);
            Cache.selectedClient = null;
            ShowClientsController.displayClientInfo(null);
            ShowClientsController.bindData();
            view.editClientBtn.setEnabled(false);
            view.deleteClient.setEnabled(false);
        }

    }

    private ShowClientsPanel view;
}
