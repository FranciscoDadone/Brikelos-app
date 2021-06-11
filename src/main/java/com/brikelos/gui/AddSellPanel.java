package com.brikelos.gui;


import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AddSellPanel {

    DefaultListModel defaultListModel = new DefaultListModel();

    public AddSellPanel() {

        this.bindData();

        /**
         * Puts style to the client search.
         */
        PromptSupport.setPrompt("Buscar cliente...", buscarCliente);
        buscarCliente.setFont(new Font("Arial", Font.PLAIN, 20));


        buscarCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                searchFilter(buscarCliente.getText());
            }
        });
        listadoClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(listadoClientes.getSelectedValue());
            }
        });

        guardarButton.addActionListener(e -> {

        });
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
        listadoClientes.setModel(defaultListModel);
        listadoClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        listadoClientes.setModel(defaultListModel);
    }

    JPanel getPanel() {
        return panel;
    }

    private JPanel panel;
    private JList listadoClientes;
    private JTextField buscarCliente;
    private JTextField textField1;
    private JTextField tituloVenta;
    private JTextArea descripcionVenta;
    private JButton guardarButton;
    private JTextField textField2;
}
