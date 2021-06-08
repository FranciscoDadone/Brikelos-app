package com.brikelos.gui;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Locale;

public class AgregarVentaPanel {

    DefaultListModel defaultListModel = new DefaultListModel();

    public AgregarVentaPanel() {

        this.bindData();

        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                searchFilter(textField1.getText());
            }
        });
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(list1.getSelectedValue());
            }
        });
    }

    private ArrayList getStars() {
        ArrayList stars = new ArrayList();
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
        list1.setModel(defaultListModel);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        list1.setModel(defaultListModel);
    }

    JPanel getPanel() {
        return panel;
    }

    private JPanel panel;
    private JList list1;
    private JTextField textField1;
}
