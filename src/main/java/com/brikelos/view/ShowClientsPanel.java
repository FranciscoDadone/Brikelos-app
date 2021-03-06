package com.brikelos.view;

import com.brikelos.controller.DeleteClient;
import com.brikelos.controller.ShowClientsController;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.*;

public class ShowClientsPanel {

    public ShowClientsPanel() {

        /**
         * Puts style to the client search.
         */
        PromptSupport.setPrompt("Buscar cliente...", searchClients);
        searchClients.setFont(new Font("Arial", Font.PLAIN, 20));

        /**
         * Listeners
         */
        searchClients.addKeyListener(new ShowClientsController(this));
        listOfClients.addMouseListener(new ShowClientsController(this));
        editClientBtn.addActionListener(new ShowClientsController(this));
        deleteClient.addActionListener(new DeleteClient(this));

        purchasesPanel.setLayout(new BoxLayout(purchasesPanel, BoxLayout.PAGE_AXIS));

    }


    public JPanel getPanel() {
        return panel;
    }

    private JPanel panel;
    public JTextField searchClients;
    public JList listOfClients;
    public JPanel purchasesPanel;
    public JLabel clientName;
    public JLabel clientPhone;
    public JLabel clientTotalSpent;
    public JButton editClientBtn;
    public JButton deleteClient;
}
