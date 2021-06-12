package com.brikelos.controller;

import com.brikelos.util.GUIHandler;
import com.brikelos.view.AddClientPanel;
import com.brikelos.view.AddSellPanel;
import com.brikelos.view.MainGUI;
import com.brikelos.view.ShowClientsPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUIController implements ActionListener {

    public MainGUIController(MainGUI view) {
        this.view = view;
    }

    /**
     * Changes the screen.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(view.addSellButton)) {
            GUIHandler.changeScreen(new AddSellPanel().getPanel());
        } else if(e.getSource().equals(view.newClientButton)) {
            GUIHandler.changeScreen(new AddClientPanel().getPanel());
        } else if(e.getSource().equals(view.showClientsButton)) {
            GUIHandler.changeScreen(new ShowClientsPanel().getPanel());
        }
    }

    private MainGUI view;
}
