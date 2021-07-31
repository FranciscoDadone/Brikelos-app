package com.brikelos.controller;

import com.brikelos.model.Local.models.Purchase;
import com.brikelos.model.Local.queries.ClientQueries;
import com.brikelos.model.Local.queries.SellQueries;
import com.brikelos.model.Remote.queries.RemoteClientQueries;
import com.brikelos.model.Remote.queries.RemoteSellsQueries;
import com.brikelos.util.Util;
import com.brikelos.view.JCustomOptionPane;
import com.brikelos.view.JLabelFont;
import com.brikelos.view.JPurchase;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditPurchase implements ActionListener {
    public EditPurchase(Purchase purchase, JPurchase panel) {
        this.purchase = purchase;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JTextField title = new JTextField(purchase.getTitle(), 20);
        JTextArea description = new JTextArea(purchase.getDescription(), 12, 20);
        JScrollPane sp = new JScrollPane(description);
        JTextField price = new JTextField(String.valueOf(purchase.getPrice()));

        Object[] fields = {
                new JLabelFont("Título", new Font("Arial", Font.PLAIN, 24)), title,
                new JLabelFont("Descripción", new Font("Arial", Font.PLAIN, 24)), sp,
                new JLabelFont("Precio", new Font("Arial", Font.PLAIN, 24)), price
        };
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        description.setFont(new Font("Arial", Font.PLAIN, 24));
        price.setFont(new Font("Arial", Font.PLAIN, 24));

        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,20)));
        int res = JOptionPane.showConfirmDialog(null, fields, "Editar venta", JOptionPane.OK_CANCEL_OPTION);

        if(res == JOptionPane.YES_OPTION) {
            if(Util.isNumeric(price.getText())) {
                purchase.setTitle(title.getText());
                purchase.setDescription(description.getText());

                if(purchase.getPrice() != Double.parseDouble(price.getText())) {

                    double newPrice = Double.parseDouble(price.getText());
                    double oldPrice = purchase.getPrice();
                    double clientMoney = ClientQueries.getClientById(purchase.getBuyerID()).getMoneySpent();

                    double newBal = Math.abs((clientMoney - oldPrice) + newPrice);

                    ClientQueries.setMoneySpent(
                            purchase.getBuyerID(),
                            newBal
                            );
                }

                purchase.setPrice(Double.parseDouble(price.getText()));

                SellQueries.modifySellInfo(purchase.getId(), purchase);
                ShowClientsController.displayClientInfo(Cache.selectedClient);
                RemoteSellsQueries.editSell(purchase);
                RemoteClientQueries.editClient(ClientQueries.getClientById(purchase.getBuyerID()));

            } else {
                JCustomOptionPane.messageDialog(
                        "El precio tiene que ser un número!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        }


    }

    private Purchase purchase;
    private JPurchase panel;
}
