package com.brikelos.gui;

import com.brikelos.database.databaseHandler.DatabaseHandler;
import com.brikelos.util.Util;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddClientPanel {

    private JPanel panel;
    private JTextField nameAndSurname;
    private JTextField dni;
    private JTextField phoneNum;
    private JTextField email;
    private JButton saveButton;

    public AddClientPanel() {

        /**
         * Triggers when the button is clicked.
         */
        saveButton.addActionListener(e -> {
            /**
             * Checks if the name and dni is empty and displays a warning.
             */
            if(nameAndSurname.getText().equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "El nombre no puede quedar en blanco.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if(!Util.isNumeric(dni.getText())) {
                JOptionPane.showMessageDialog(
                        null,
                        "El DNI solo puede contener números.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                Connection connection = new DatabaseHandler().connect();
                String error = "";

                /**
                 * Checks if the same name already exists in the database.
                 */
                try {
                    ResultSet res = connection.createStatement().executeQuery(
                            "SELECT * FROM Clients WHERE upper(name)='" + nameAndSurname.getText().toUpperCase() + "';"
                    );
                    if(res.next()) {
                        error = "Ya hay un cliente registrado con ese nombre.";
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                /**
                 * Checks if the same DNI already exists in the database.
                 */
                try {
                    ResultSet res = connection.createStatement().executeQuery(
                            "SELECT * FROM Clients WHERE (dni=" + dni.getText() + ");"
                    );
                    if(res.next()) {
                        error = "Ya hay un cliente registrado con ese DNI.";
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                /**
                 * If there are errors, it display a warning to the user.
                 * Else: it displays a confirmation message and if the answer is 'yes'
                 * it saves the information to the database.
                 */
                if(error.length() != 0) {
                    JOptionPane.showMessageDialog(
                            null,
                            error,
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE
                    );
                    try {
                        connection.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    int reply = JOptionPane.showConfirmDialog(
                            null,
                            "<html>"                                                  +
                                    "¿Son correctos estos datos?" +                    "<br>" +
                                    "Nombre y apellido: " + nameAndSurname.getText() + "<br>" +
                                    "DNI: " + dni.getText() +                          "<br>" +
                                    "Tel.: " + phoneNum.getText() +                    "<br>" +
                                    "Email: " + email.getText() +                      "<br>" +
                                    "</html>",
                            "Confirmar datos",
                            JOptionPane.YES_NO_OPTION
                    );
                    if(reply == JOptionPane.YES_OPTION) {
                        try {
                            connection.createStatement().execute(
                                    "INSERT INTO Clients (name, dni, phone, email, moneySpent) VALUES (" +
                                    "'" + nameAndSurname.getText() + "', "
                                        + dni.getText()            + ", "  +
                                    "'" + phoneNum.getText()       + "', " +
                                    "'" + email.getText()          + "', " +
                                    "0" +
                                    ");"
                            );
                        } catch (SQLException e1) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    e1.getMessage(),
                                    "ERROR",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            e1.printStackTrace();
                        } finally {
                            try {
                                connection.close();
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }
                            JOptionPane.showMessageDialog(
                                    null,
                                    nameAndSurname.getText() + " fue agregado a la lista de clientes.",
                                    "Cliente agregado.",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            GUIHandler.changeScreen(new AddClientPanel().getPanel());
                        }
                    }
                }
            }
        });
    }

    /**
     * Returns the panel.
     * @return JPanel
     */
    public JPanel getPanel() {
        return panel;
    }


}
