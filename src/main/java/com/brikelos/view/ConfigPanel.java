package com.brikelos.view;

import com.brikelos.controller.ConfigController;

import javax.swing.*;

public class ConfigPanel {

    public ConfigPanel(double money) {

        moneyAlertField.setText(Double.toString(money));

        /**
         * Listener
         */
        saveButton.addActionListener(new ConfigController(this));
    }

    public JPanel getPanel() {
        return panel;
    }

    private JPanel panel;
    public JTextField moneyAlertField;
    public JButton saveButton;
}
