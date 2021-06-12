package com.brikelos.controller;

import com.brikelos.model.ClientQueries;
import com.brikelos.model.Connection;
import com.brikelos.model.Sell;
import com.brikelos.model.SellQueries;
import com.brikelos.util.Util;
import com.brikelos.view.AddSellPanel;
import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddSellController implements ActionListener, MouseListener, KeyListener {
    DefaultListModel defaultListModel = new DefaultListModel();

    public AddSellController(AddSellPanel view) {
        this.view  = view;
        bindData();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(view.button)) {
            if(view.clientList.getSelectedValue() == null || view.clientList.getSelectedValue().toString().equals("null")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Tienes que seleccionar un cliente para asignarle la venta.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if(view.sellTitle.getText() == null || view.sellTitle.getText().equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "El título no puede quedar en blanco.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if(view.sellPrice.getText() == null || view.sellPrice.getText().equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "El precio no puede quedar en blanco.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                String date;
                if(view.sellDate.getText() == null || view.sellDate.getText().equals("")) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    date = formatter.format(new Date());
                } else {
                    date = view.sellDate.getText();
                }

                SellQueries.addSell(new Sell(
                        ClientQueries.getIdByName(view.clientList.getSelectedValue().toString()),
                        date,
                        view.sellTitle.getText(),
                        view.sellDescription.getText(),
                        Double.parseDouble(view.sellPrice.getText())
                ));
            }
        }
    }

    /**
     * Adds all the clients to the JList.
     */
    public void bindData() {
        ClientQueries.getAllClients().forEach((client) -> {
            defaultListModel.addElement(client);
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
            String name = client.getName().toLowerCase();
            if(Util.isNumeric(searchTerm) && client.getDni() == Integer.parseInt(searchTerm) || name.contains(searchTerm.toLowerCase())) {
                filteredItems.addElement(client);
            }
        });
        defaultListModel = filteredItems;
        view.clientList.setModel(defaultListModel);
    }

    private AddSellPanel view;

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == view.clientList) {
            System.out.println(view.clientList.getSelectedValue());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource().equals(view.clientSearch)) {
            searchFilter(view.clientSearch.getText());
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}
}
