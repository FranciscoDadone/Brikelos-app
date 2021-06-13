package com.brikelos.controller;

import com.brikelos.model.queries.ClientQueries;
import com.brikelos.model.models.Sell;
import com.brikelos.model.queries.SellQueries;
import com.brikelos.util.GUIHandler;
import com.brikelos.util.Util;
import com.brikelos.view.AddSellPanel;
import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddSellController implements ActionListener, KeyListener {
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
            } else if(!Util.isNumeric(view.sellPrice.getText())) {
                JOptionPane.showMessageDialog(
                        null,
                        "El precio tiene que ser un número.",
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

                int reply = JOptionPane.showConfirmDialog(
                        null,
                        "<html>"                                             +
                                "¿Son correctos estos datos de la venta?"            + "<br>" +
                                "Fecha: "       + date                               + "<br>" +
                                "Comprador: "   + view.clientList.getSelectedValue() + "<br>" +
                                "Precio: "      + view.sellPrice.getText()           + "<br>" +
                                "Título: "      + view.sellTitle.getText()           + "<br>" +
                                "Descripción: " + view.sellDescription.getText(),
                        "Confirmar datos",
                        JOptionPane.YES_NO_OPTION
                );

                if(reply == JOptionPane.YES_OPTION) {
                    boolean success = SellQueries.addSell(new Sell(
                            ClientQueries.getIdByName(view.clientList.getSelectedValue().toString()),
                            date,
                            view.sellTitle.getText(),
                            view.sellDescription.getText(),
                            Double.parseDouble(view.sellPrice.getText())
                    ));

                    if(success) {
                        JOptionPane.showMessageDialog(
                                null,
                                (success) ? "La venta de '" + view.sellTitle.getText() + "' fue guardada correctamente." : "Error al agregar cliente.",
                                (success) ? "Venta guardada." : "ERROR",
                                (success) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
                        );
                        GUIHandler.changeScreen(new AddSellPanel().getPanel());
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
