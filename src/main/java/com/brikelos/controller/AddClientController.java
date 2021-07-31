package com.brikelos.controller;

import com.brikelos.model.Local.models.Client;
import com.brikelos.model.Local.queries.ClientQueries;
import com.brikelos.model.Remote.queries.RemoteClientQueries;
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
        } else {
            Client client = new Client(view.nameAndSurname.getText(),
                    view.phoneNum.getText()
            );
            String error = "";

            if(ClientQueries.sameName(client)) {
                error = "Ya hay un cliente registrado con ese nombre.";
            }

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
                                "Tel.: " + view.phoneNum.getText() +                    "<br>" +
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
                    if(success) {
                        RemoteClientQueries.backupClient(ClientQueries.getClientByName(client.getName()));
                    }
                    GUIHandler.changeScreen(new AddClientPanel().getPanel());
                }
            }
        }
    }

    private AddClientPanel view;
}
