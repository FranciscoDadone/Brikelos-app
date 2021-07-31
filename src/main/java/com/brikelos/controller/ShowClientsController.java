package com.brikelos.controller;

import com.brikelos.model.Local.models.Client;
import com.brikelos.model.Local.models.Purchase;
import com.brikelos.model.Local.queries.ClientQueries;
import com.brikelos.model.Local.queries.SellQueries;
import com.brikelos.model.Remote.queries.RemoteClientQueries;
import com.brikelos.util.Util;
import com.brikelos.view.JCustomOptionPane;
import com.brikelos.view.JLabelFont;
import com.brikelos.view.JPurchase;
import com.brikelos.view.ShowClientsPanel;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ShowClientsController implements KeyListener, MouseListener, ActionListener {

    static DefaultListModel defaultListModel = new DefaultListModel();

    public ShowClientsController(ShowClientsPanel view) {
        this.view = view;
        bindData();
    }

    public static void bindData() {
        defaultListModel.removeAllElements();
        view.listOfClients.removeAll();
        ClientQueries.getAllClients().stream().forEach((client) -> {
            if(!client.isDeleted()) defaultListModel.addElement(client);
        });
        view.listOfClients.setModel(defaultListModel);
        view.listOfClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void searchFilter(String searchTerm) {
        DefaultListModel filteredItems = new DefaultListModel();

        ClientQueries.getAllClients().stream().forEach((client) -> {
            if(!client.isDeleted()) {
                String starName = client.toString().toLowerCase();

                if(starName.contains(searchTerm.toLowerCase())) {
                    filteredItems.addElement(client);
                }
            }
        });
        defaultListModel = filteredItems;
        view.listOfClients.setModel(defaultListModel);
    }

    public static void displayClientInfo(Client client) {
        view.purchasesPanel.removeAll();
        if(client != null) {
            if(view.listOfClients.getSelectedValue() != null) {
                Cache.selectedClient = client;

                view.clientName.setText("Nombre: " + Cache.selectedClient.getName());
                view.clientPhone.setText("Tel.: " + Cache.selectedClient.getPhone());
                view.clientTotalSpent.setText("Total gastado: $" + Cache.selectedClient.getMoneySpent());

                ArrayList<Purchase> clientPurchases = SellQueries.getAllClientSells(Cache.selectedClient);

                AtomicInteger i = new AtomicInteger();
                clientPurchases.forEach((purchase) -> {
                    if(!purchase.isDeleted()) {
                        i.getAndIncrement();
                        view.purchasesPanel.add(
                                new JPurchase(
                                        i.get(),
                                        purchase
                                )
                        );
                    }
                });
                view.editClientBtn.setEnabled(true);
                view.deleteClient.setEnabled(true);
                view.purchasesPanel.revalidate();
                view.purchasesPanel.repaint();
            } else {
                view.editClientBtn.setEnabled(false);
                view.deleteClient.setEnabled(false);
            }
        } else {
            view.clientName.setText("Selecciona un cliente para ver sus datos.");
            view.clientPhone.setText("");
            view.clientTotalSpent.setText("");
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        searchFilter(view.searchClients.getText());
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            displayClientInfo(ClientQueries.getClientByName(view.listOfClients.getSelectedValue().toString()));
        } catch (Exception e1) {}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(view.editClientBtn)) {
            JTextField name = new JTextField(20);
            JTextField phone = new JTextField();
            JTextField money = new JTextField();

            JLabelFont nameTxt = new JLabelFont("Nombre", new Font("Arial", Font.PLAIN, 24));
            JLabelFont telTxt = new JLabelFont("Teléfono", new Font("Arial", Font.PLAIN, 24));
            JLabelFont moneyTxt = new JLabelFont("Dinero gastado", new Font("Arial", Font.PLAIN, 24));

            Object[] fields = {
                    nameTxt, name,
                    telTxt, phone,
                    moneyTxt, money
            };
            name.setFont(new Font("Arial", Font.PLAIN, 24));
            name.setText(Cache.selectedClient.getName());
            phone.setFont(new Font("Arial", Font.PLAIN, 24));
            phone.setText(Cache.selectedClient.getPhone());
            money.setFont(new Font("Arial", Font.PLAIN, 24));
            money.setText(String.valueOf(Cache.selectedClient.getMoneySpent()));

            UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,20)));
            int res = JOptionPane.showConfirmDialog(null, fields, "Editar cliente", JOptionPane.OK_CANCEL_OPTION);

            if(res == JOptionPane.YES_OPTION) {
                if(Util.isNumeric(money.getText())) {
                    Cache.selectedClient.setName(name.getText());
                    Cache.selectedClient.setPhone(phone.getText());
                    Cache.selectedClient.setMoneySpent(Double.parseDouble(money.getText()));
                    ClientQueries.modifyClientInfo(Cache.selectedClient.getId(), Cache.selectedClient);
                    bindData();
                    view.clientName.setText("Nombre: " + Cache.selectedClient.getName());
                    view.clientPhone.setText("Tel.: " + Cache.selectedClient.getPhone());
                    view.clientTotalSpent.setText("Total gastado: $" + Cache.selectedClient.getMoneySpent());

                    RemoteClientQueries.editClient(Cache.selectedClient);

                } else {
                    JCustomOptionPane.messageDialog(
                            "Error, el monto de dinero tiene que ser numérico.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
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

    private static ShowClientsPanel view;
}
