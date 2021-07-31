package com.brikelos.controller;

import com.brikelos.model.Local.models.Client;
import com.brikelos.model.Local.models.Purchase;
import com.brikelos.model.Local.queries.ClientQueries;
import com.brikelos.model.Local.queries.SellQueries;
import com.brikelos.model.Remote.queries.RemoteClientQueries;
import com.brikelos.model.Remote.queries.RemoteSellsQueries;
import com.brikelos.view.JCustomOptionPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePurchase implements ActionListener {
    public DeletePurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int res = JCustomOptionPane.confirmDialog(
                "<html>Seguro que quiere eliminar la compra:<br><b>" + purchase.getTitle() + "</b><br>" +
                        "Del cliente: <b>" + ClientQueries.getClientById(purchase.getBuyerID()) + "</b></html>", "Confirmación");

        if(res == JOptionPane.YES_OPTION) {
            SellQueries.deletePurchase(purchase);
            RemoteSellsQueries.deleteSell(purchase);

            double purchasePrice = purchase.getPrice();
            double clientMoney = ClientQueries.getClientById(purchase.getBuyerID()).getMoneySpent();
            double newBal = Math.abs(clientMoney - purchasePrice);

            ClientQueries.setMoneySpent(purchase.getBuyerID(), newBal);

            RemoteClientQueries.editClient(ClientQueries.getClientById(purchase.getBuyerID()));

            ShowClientsController.displayClientInfo(Cache.selectedClient);
        }
    }
    private Purchase purchase;
}
