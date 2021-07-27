package com.brikelos.controller;

import com.brikelos.model.models.Client;
import com.brikelos.model.models.Sell;
import com.brikelos.model.queries.ClientQueries;
import com.brikelos.model.queries.SellQueries;
import com.brikelos.util.GUIHandler;
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

    DefaultListModel defaultListModel = new DefaultListModel();

    public ShowClientsController(ShowClientsPanel view) {
        this.view = view;
        bindData();
    }

    private void bindData() {
        defaultListModel.removeAllElements();
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

        if(view.listOfClients.getSelectedValue() != null) {
            Client client = ClientQueries.getClientByName(view.listOfClients.getSelectedValue().toString());

            view.clientName.setText("Nombre: " + client.getName());
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
            view.editClientBtn.setEnabled(true);
            view.purchasesPanel.revalidate();
            view.purchasesPanel.repaint();
        } else {
            view.editClientBtn.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Client client = ClientQueries.getClientByName(view.listOfClients.getSelectedValue().toString());

        JTextField name = new JTextField();
        JTextField phone = new JTextField();
        JLabelFont nameTxt = new JLabelFont("Nombre", new Font("Arial", Font.PLAIN, 24));
        JLabelFont telTxt = new JLabelFont("Teléfono", new Font("Arial", Font.PLAIN, 24));

        Object[] fields = {
                nameTxt, name,
                telTxt, phone
        };
        name.setFont(new Font("Arial", Font.PLAIN, 24));
        name.setText(client.getName());
        phone.setFont(new Font("Arial", Font.PLAIN, 24));
        phone.setText(client.getPhone());

        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,20)));
        int res = JOptionPane.showConfirmDialog(null, fields, "Editar cliente", JOptionPane.OK_CANCEL_OPTION);

        if(res == JOptionPane.YES_OPTION) {
            client.setName(name.getText());
            client.setPhone(phone.getText());
            ClientQueries.modifyClientInfo(client.getId(), client);
            bindData();
            view.clientName.setText("Nombre: " + client.getName());
            view.clientPhone.setText("Tel.: " + client.getPhone());
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

    private ShowClientsPanel view;

}
