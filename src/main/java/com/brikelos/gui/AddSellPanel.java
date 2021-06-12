package com.brikelos.gui;


import com.brikelos.database.databaseHandler.DatabaseHandler;
import com.brikelos.util.Util;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

public class AddSellPanel {

    DefaultListModel defaultListModel = new DefaultListModel();

    public AddSellPanel() {

        this.bindData();

        /**
         * Style to the client search.
         */
        PromptSupport.setPrompt("Buscar cliente...", clientSearch);
        clientSearch.setFont(new Font("Arial", Font.PLAIN, 20));

        /**
         * Key down listener in the JList
         */
        clientSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                searchFilter(clientSearch.getText());
            }
        });
        /**
         * Mouse click listener in the JList
         */
        clientList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(clientList.getSelectedValue());
            }
        });
        /**
         * Button press listener.
         * Handles the save action to the database.
         */
        button.addActionListener(e -> {

            Connection connection = new DatabaseHandler().connect();
            try {
                connection.createStatement().execute("");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        });
    }

    /**
     * Adds all the clients to the JList.
     */
    private void bindData() {
        new DatabaseHandler().getAllClients().forEach((client) -> {
            defaultListModel.addElement(client);
        });
        clientList.setModel(defaultListModel);
        clientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Filters the names from the database to the searchTerm and if
     * there are coincidences it saves it in the JList to show them.
     * @param searchTerm
     */
    private void searchFilter(String searchTerm) {
        DefaultListModel filteredItems = new DefaultListModel();
        new DatabaseHandler().getAllClients().forEach((client) -> {
            String name = client.getName().toLowerCase();
            if(Util.isNumeric(searchTerm) && client.getDni() == Integer.parseInt(searchTerm) || name.contains(searchTerm.toLowerCase())) {
                filteredItems.addElement(client);
            }
        });
        defaultListModel = filteredItems;
        clientList.setModel(defaultListModel);
    }

    /**
     * Returns the JPanel.
     * @return JPanel
     */
    JPanel getPanel() {
        return panel;
    }

    private JPanel panel;
    private JList clientList;
    private JTextField clientSearch;
    private JTextField sellDate;
    private JTextField sellTitle;
    private JTextArea sellDescription;
    private JButton button;
    private JTextField sellPrice;
}
