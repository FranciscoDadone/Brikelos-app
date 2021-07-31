package com.brikelos.controller;

import com.brikelos.model.Local.queries.ClientQueries;
import com.brikelos.model.Local.models.Purchase;
import com.brikelos.model.Local.queries.ConfigQueries;
import com.brikelos.model.Local.queries.SellQueries;
import com.brikelos.model.Remote.queries.RemoteSellsQueries;
import com.brikelos.util.GUIHandler;
import com.brikelos.util.Util;
import com.brikelos.view.AddSellPanel;
import com.brikelos.view.JCustomOptionPane;
import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddSellController implements ActionListener, KeyListener {
    DefaultListModel defaultListModel = new DefaultListModel();

    public AddSellController(AddSellPanel view) {
        this.view  = view;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        view.sellDate.setText(formatter.format(new Date()));

        bindData();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(view.button)) {
            if(view.clientList.getSelectedValue() == null || view.clientList.getSelectedValue().toString().equals("null")) {
                JCustomOptionPane.messageDialog(
                        "Tienes que seleccionar un cliente para asignarle la venta.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if(view.sellTitle.getText() == null || view.sellTitle.getText().equals("")) {
                JCustomOptionPane.messageDialog(
                        "El título no puede quedar en blanco.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if(view.sellPrice.getText() == null || view.sellPrice.getText().equals("")) {
                JCustomOptionPane.messageDialog(
                        "El precio no puede quedar en blanco.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if(!Util.isNumeric(view.sellPrice.getText())) {
                JCustomOptionPane.messageDialog(
                        "El precio tiene que ser un número.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                int clientID = ClientQueries.getIdByName(view.clientList.getSelectedValue().toString());
                Purchase purchase = new Purchase(
                        clientID,
                        view.sellDate.getText(),
                        view.sellTitle.getText(),
                        view.sellDescription.getText(),
                        Double.parseDouble(view.sellPrice.getText()),
                        false
                );

                int reply = JCustomOptionPane.confirmDialog(
                        purchase
                );

                if(reply == JOptionPane.YES_OPTION) {
                    double sellPrice        = Double.parseDouble(view.sellPrice.getText());
                    double clientMoneySpent = ClientQueries.getTotalSpent(ClientQueries.getClientByName(view.clientList.getSelectedValue().toString()));

                    int sellID = SellQueries.addSell(purchase);
                    JCustomOptionPane.messageDialog(
                            (sellID != -1) ? "La venta de '" + view.sellTitle.getText() + "' fue guardada correctamente." : "Error al agregar cliente.",
                            (sellID != -1) ? "Venta guardada." : "ERROR",
                            (sellID != -1) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
                    );
                    if(sellID != -1) {
                        double moneyAlertTrigger = ConfigQueries.getConfig().getMoneyAlert();
                        if((clientMoneySpent + sellPrice) >= moneyAlertTrigger) {
                            JCustomOptionPane.messageDialog(
                                    view.clientList.getSelectedValue().toString() + " ha superado los $" + moneyAlertTrigger,
                                    "Información",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            double clientBalance = ((sellPrice - moneyAlertTrigger) + clientMoneySpent);
                            while(clientBalance >= moneyAlertTrigger) {
                                clientBalance -= moneyAlertTrigger;
                            }

                            ClientQueries.setMoneySpent(
                                    clientID,
                                    clientBalance
                            );
                        } else {
                            double clientBalance = sellPrice + clientMoneySpent;
                            ClientQueries.setMoneySpent(
                                    clientID,
                                    clientBalance
                            );
                        }
                        GUIHandler.changeScreen(new AddSellPanel().getPanel());
                        RemoteSellsQueries.backupSell(new Purchase(sellID, purchase));
                    }
                }
            }
        }
    }

    /**
     * Adds all the clients to the JList.
     */
    public void bindData() {
        ClientQueries.getAllClients().forEach((client) -> {
            if(!client.isDeleted()) defaultListModel.addElement(client);
        });
        view.clientList.setModel(defaultListModel);
        view.clientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Filters the names from the database to the searchTerm and if
     * there are coincidences it saves it in the JList to show them.
     * @param searchTerm
     */
    private void searchFilter(String searchTerm) {
        DefaultListModel filteredItems = new DefaultListModel();
        ClientQueries.getAllClients().forEach((client) -> {
            if(!client.isDeleted()) {
                String name = client.getName().toLowerCase();
                if(name.contains(searchTerm.toLowerCase())) {
                    filteredItems.addElement(client);
                }
            }
        });
        defaultListModel = filteredItems;
        view.clientList.setModel(defaultListModel);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource().equals(view.clientSearch)) {
            searchFilter(view.clientSearch.getText());
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}

    private AddSellPanel view;
}
