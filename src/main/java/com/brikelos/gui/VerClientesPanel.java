package com.brikelos.gui;

import com.brikelos.templates.JCompra;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class VerClientesPanel {
    private JPanel panel;
    private JTextField searchClients;
    private JList listOfClients;
    private JPanel panelCompra;

    DefaultListModel defaultListModel = new DefaultListModel();


    public VerClientesPanel() {
        this.bindData();

        /**
         * Puts style to the client search.
         */
        PromptSupport.setPrompt("Buscar cliente...", searchClients);
        searchClients.setFont(new Font("Arial", Font.PLAIN, 20));


        searchClients.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                searchFilter(searchClients.getText());
            }
        });
        listOfClients.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(listOfClients.getSelectedValue());
            }
        });

        panelCompra.setLayout(new BoxLayout(panelCompra, BoxLayout.PAGE_AXIS));

        for(int i = 0; i < 20; i++) {
            panelCompra.add(new JCompra(0, "10/10/2021", "Los enanitos verdes", "A JLabel object can display either text, an image, or both. You can specify where in the label's display area the label's contents are aligned by setting the vertical and horizontal alignment. By default, labels are vertically centered in their display area. Text-only labels are leading edge aligned, by default; image-only labels are horizontally centered, by default.", 1000));
        }
    }

    private ArrayList getStars() {
        ArrayList stars = new ArrayList();
        stars.add("hola");
        stars.add("mundo");
        stars.add("dos");
        stars.add("tres");
        stars.add("cuatro");
        stars.add("hola");
        stars.add("mundo");
        stars.add("dos");
        stars.add("tres");
        stars.add("cuatro");
        stars.add("hola");
        stars.add("mundo");
        stars.add("dos");
        stars.add("tres");
        stars.add("cuatro");
        return stars;
    }

    private void bindData() {
        getStars().stream().forEach((star) -> {
            defaultListModel.addElement(star);
        });
        listOfClients.setModel(defaultListModel);
        listOfClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


    private void searchFilter(String searchTerm) {
        DefaultListModel filteredItems = new DefaultListModel();

        getStars().stream().forEach((star) -> {
            String starName = star.toString().toLowerCase();

            if(starName.contains(searchTerm.toLowerCase())) {
                filteredItems.addElement(star);
            }

        });
        defaultListModel = filteredItems;
        listOfClients.setModel(defaultListModel);
    }


    public JPanel getPanel() {
        return panel;
    }


}
