package com.brikelos.view;

import javax.swing.*;
import java.awt.*;

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
        this.add(new JLabelFont(description, font));
        this.add(new JLabel(" "));



    }

}
