package com.brikelos.view;

import com.brikelos.controller.AddSellController;
import org.jdesktop.swingx.prompt.PromptSupport;
import javax.swing.*;
import java.awt.*;

public class AddSellPanel {
    public AddSellPanel() {

        clientSearch.addKeyListener(new AddSellController(this));
        clientList.addMouseListener(new AddSellController(this));
        button.addActionListener(new AddSellController(this));

        /**
         * Style to the client search.
         */
        PromptSupport.setPrompt("Buscar cliente...", clientSearch);
        clientSearch.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    /**
     * Returns the JPanel.
     * @return JPanel
     */
    public JPanel getPanel() {
        return panel;
    }

    private JPanel panel;
    public JList clientList;
    public JTextField clientSearch;
    public JTextField sellDate;
    public JTextField sellTitle;
    public JTextArea sellDescription;
    public JButton button;
    public JTextField sellPrice;
}
