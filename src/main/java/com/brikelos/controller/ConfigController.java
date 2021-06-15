package com.brikelos.controller;

import com.brikelos.model.queries.ConfigQueries;
import com.brikelos.util.Util;
import com.brikelos.view.ConfigPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigController implements ActionListener {
    public ConfigController(ConfigPanel view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(view.saveButton)) {
            if(Util.isNumeric(view.moneyAlertField.getText())) {
                ConfigQueries.setMoneyAlert(Double.parseDouble(view.moneyAlertField.getText()));
                JOptionPane.showMessageDialog(
                        null,
                        "Configuración guardada exitosamente!",
                        "Configuración",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "El valor de la alerta tiene que ser numérico.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    private ConfigPanel view;
}
