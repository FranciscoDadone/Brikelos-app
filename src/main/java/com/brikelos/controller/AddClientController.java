package com.brikelos.controller;

import com.brikelos.model.models.Client;
import com.brikelos.model.queries.ClientQueries;
import com.brikelos.util.GUIHandler;
import com.brikelos.view.AddClientPanel;
import com.brikelos.view.JCustomOptionPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddClientController implements ActionListener {

    public AddClientController(AddClientPanel view) {
        this.view  = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * Checks if the name and dni is empty and displays a warning.
         */
        if(view.nameAndSurname.getText().equals("")) {
            JCustomOptionPane.messageDialog(
                    "El nombre no puede quedar en blanco.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
//        } else if(!Util.isNumeric(view.dni.getText())) {
//            JCustomOptionPane.messageDialog(
//                    "El DNI solo puede contener números.",
//                    "Advertencia",
//                    JOptionPane.WARNING_MESSAGE
//            );
//        } else {
        } else {
            Client client = new Client(view.nameAndSurname.getText(),
//                    Long.parseLong(view.dni.getText()),
//                    view.email.getText(),
                    view.phoneNum.getText()
            );
            String error = "";

            if(ClientQueries.sameName(client)) {
                error = "Ya hay un cliente registrado con ese nombre.";
            }

//            if(ClientQueries.sameDni(client)) {
//                error = "Ya hay un cliente registrado con ese DNI.";
//            }

            /**
             * If there are errors, it display a warning to the user.
             * Else: it displays a confirmation message and if the answer is 'yes'
             * it saves the information to the database.
             */
            if(error.length() != 0) {
                JCustomOptionPane.messageDialog(
                        error,
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                int reply = JCustomOptionPane.confirmDialog(
                        "<html>"                                                       +
                                "¿Son correctos estos datos?" +                         "<br>" +
                                "Nombre y apellido: " + view.nameAndSurname.getText() + "<br>" +
//                                "DNI: " + view.dni.getText() +                          "<br>" +
                                "Tel.: " + view.phoneNum.getText() +                    "<br>" +
//                                "Email: " + view.email.getText() +                      "<br>" +
                                "</html>",
                        "Confirmar datos"
                );
                if(reply == JOptionPane.YES_OPTION) {
                    boolean success = ClientQueries.addClient(client);
                    JCustomOptionPane.messageDialog(
                            (success) ? client.getName() + " fue agregado a la lista de clientes." : "Error al agregar cliente.",
                            (success) ? "Cliente agregado." : "ERROR",
                            (success) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
                    );
                    GUIHandler.changeScreen(new AddClientPanel().getPanel());
                }
            }
        }
    }

    private AddClientPanel view;
}
