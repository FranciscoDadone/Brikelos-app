package com.brikelos.controller;

import com.brikelos.model.queries.ConfigQueries;
import com.brikelos.util.GUIHandler;
import com.brikelos.view.*;

import java.awt.*;
import java.awt.event.*;

public class MainGUIController extends MouseAdapter implements ActionListener {

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
        } else if(e.getSource().equals(view.configButton)) {
            GUIHandler.changeScreen(new ConfigPanel(
                    ConfigQueries.getConfig().getMoneyAlert()
            ).getPanel());
        }
    }

    /**
     * Changes the style of the wheel when the mouse is over.
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().setForeground(Color.GRAY);
    }

    /**
     * Changes the style of the wheel when the mouse exits.
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        e.getComponent().setForeground(new Color(53, 53, 53));
    }

    private MainGUI view;
}
