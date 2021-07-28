package com.brikelos.controller;

import com.brikelos.model.models.Purchase;
import com.brikelos.model.queries.ClientQueries;
import com.brikelos.model.queries.SellQueries;
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

        int res = JCustomOptionPane.confirmDialog("<html>Seguro que quiere eliminar la compra:<br>" + purchase.getTitle() + "</html>", "Confirmación");

        if(res == JOptionPane.YES_OPTION) {
            SellQueries.deletePurchase(purchase);

            double purchasePrice = purchase.getPrice();
            double clientMoney = ClientQueries.getClientById(purchase.getBuyerID()).getMoneySpent();
            double newBal = Math.abs(clientMoney - purchasePrice);

            ClientQueries.setMoneySpent(purchase.getBuyerID(), newBal);
            ShowClientsController.displayClientInfo();

        }
    }
    private Purchase purchase;
}
