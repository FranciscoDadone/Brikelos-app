package com.brikelos.view;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class JPurchase extends JPanel {

    /**
     * JPanel to show a purchase.
     * @param id
     * @param date
     * @param title
     * @param description
     * @param price
     */
    public JPurchase(int id, String date, String title, String description, double price) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Font font = new Font("Arial", Font.PLAIN, 20);

        this.add(new JSeparator(SwingConstants.HORIZONTAL));
        this.add(new JLabel(" "));
        this.add(new JLabelFont("<html><b>#" + id + "</b></html>", font));
        this.add(new JLabel(" "));
        this.add(new JLabelFont("<html><b>Fecha: </b>" + date + "</html>", font));
        this.add(new JLabelFont("<html><b>Precio: $</b>" + price + "</html>", font));
        this.add(new JLabelFont("<html><b>Título: </b>" + title + "</html>", font));

        this.add(new JLabelFont("<html><b>Descripción:</b></html>", font));

        for(String s: Arrays.asList(description.split("\n"))) {
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
    }
}
