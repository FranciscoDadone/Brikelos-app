package com.brikelos.gui;

import com.brikelos.database.databaseHandler.DatabaseHandler;

import javax.swing.*;
import java.sql.Connection;

public class AgregarClientePanel {

    private JPanel panel;
    private JTextField nameAndSurname;
    private JTextField dni;
    private JTextField phoneNum;
    private JTextField email;
    private JButton saveButton;

    public AgregarClientePanel() {

        /**
         * Triggers when the button is clicked.
         */
        saveButton.addActionListener(e -> {

//            Connection connection = new DatabaseHandler().connect();


        });
    }


    public JPanel getPanel() {
        return panel;
    }


}
