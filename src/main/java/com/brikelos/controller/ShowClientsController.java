package com.brikelos.controller;

import com.brikelos.model.models.Client;
import com.brikelos.model.models.Sell;
import com.brikelos.model.queries.ClientQueries;
import com.brikelos.model.queries.SellQueries;
import com.brikelos.view.JPurchase;
import com.brikelos.view.ShowClientsPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ShowClientsController implements KeyListener, MouseListener {

    DefaultListModel defaultListModel = new DefaultListModel();

    public ShowClientsController(ShowClientsPanel view) {
        this.view = view;
        bindData();
    }

    private void bindData() {
        ClientQueries.getAllClients().stream().forEach((star) -> {
            defaultListModel.addElement(star);
        });
        view.listOfClients.setModel(defaultListModel);
        view.listOfClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void searchFilter(String searchTerm) {
        DefaultListModel filteredItems = new DefaultListModel();

        ClientQueries.getAllClients().stream().forEach((star) -> {
            String starName = star.toString().toLowerCase();

            if(starName.contains(searchTerm.toLowerCase())) {
                filteredItems.addElement(star);
            }

        });
        defaultListModel = filteredItems;
        view.listOfClients.setModel(defaultListModel);
    }


    @Override
    public void keyReleased(KeyEvent e) {
        searchFilter(view.searchClients.getText());
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        view.purchasesPanel.removeAll();

        Client client = ClientQueries.getClientByName(view.listOfClients.getSelectedValue().toString());

        view.clientName.setText("Nombre: " + client.getName());
        view.clientDNI.setText("DNI: " + client.getDni());
        view.clientEmail.setText("Email: " + client.getEmail());
        view.clientPhone.setText("Tel.: " + client.getPhone());
        view.clientTotalSpent.setText("Total gastado: $" + client.getMoneySpent());

        ArrayList<Sell> clientPurchases = SellQueries.getAllClientSells(client);

        AtomicInteger i = new AtomicInteger();
        clientPurchases.forEach((purchase) -> {
            i.getAndIncrement();
            view.purchasesPanel.add(
                    new JPurchase(
                            i.get(),
                            purchase.getDate(),
                            purchase.getTitle(),
                            purchase.getDescription(),
                            purchase.getPrice()
                    )
            );
        });
        view.purchasesPanel.revalidate();
        view.purchasesPanel.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}


    private ShowClientsPanel view;
}
