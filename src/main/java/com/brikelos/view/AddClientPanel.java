package com.brikelos.view;

import com.brikelos.controller.AddClientController;

import javax.swing.*;

public class AddClientPanel {

    private JPanel panel;
    public JTextField nameAndSurname;
//    public JTextField dni;
    public JTextField phoneNum;
//    public JTextField email;
    public JButton saveButton;

    public AddClientPanel() {
        saveButton.addActionListener(new AddClientController(this));
    }

    /**
     * Returns the panel.
     * @return JPanel
     */
    public JPanel getPanel() {
        return panel;
    }


}
