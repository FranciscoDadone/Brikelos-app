package com.brikelos.view;

import com.brikelos.controller.DeletePurchase;
import com.brikelos.controller.EditPurchase;
import com.brikelos.model.models.Purchase;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class JPurchase extends JPanel {

    /**
     * JPanel to show a purchase.
     * @param id
     */
    public JPurchase(int id, Purchase purchase, ShowClientsPanel view) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Font font = new Font("Arial", Font.PLAIN, 20);

        this.add(new JSeparator(SwingConstants.HORIZONTAL));
        this.add(new JLabel(" "));
        this.add(new JLabelFont("<html><b>#" + id + "</b></html>", font));
        this.add(new JLabel(" "));
        this.add(new JLabelFont("<html><b>Fecha: </b>" + purchase.getDate() + "</html>", font));
        this.add(new JLabelFont("<html><b>Precio: $</b>" + purchase.getPrice() + "</html>", font));
        this.add(new JLabelFont("<html><b>Título: </b>" + purchase.getTitle() + "</html>", font));

        this.add(new JLabelFont("<html><b>Descripción:</b></html>", font));

        for(String s: Arrays.asList(purchase.getDescription().split("\n"))) {
            if(s.length() > 80) {
                int index = 0;
                while (index < s.length()) {
                    this.add(new JLabelFont((s.substring(index, Math.min(index + 80, s.length()))), font));
                    index += 80;
                }
            } else {
                this.add(new JLabelFont(s, font));
            }
        }
        this.add(new JLabel(" "));

        JPanel buttons = new JPanel();

        editBtn = new JButton("Editar");
        deleteBtn = new JButton("\uD83D\uDDD1️");

        buttons.setLayout(new BorderLayout());
        buttons.add(editBtn, BorderLayout.CENTER);
        buttons.add(deleteBtn, BorderLayout.LINE_END);

        editBtn.addActionListener(new EditPurchase(purchase, this));
        deleteBtn.addActionListener(new DeletePurchase(purchase));

        this.add(buttons);
    }

    private JButton editBtn;
    private static JButton deleteBtn;
}
