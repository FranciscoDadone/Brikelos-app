package com.brikelos.templates;

import javax.swing.*;
import java.awt.*;

public class JPurchase extends JPanel {

    /**
     * JPanel to show a purchase.
     * @param id
     * @param fecha
     * @param titulo
     * @param descripcion
     * @param precio
     */
    public JPurchase(int id, String fecha, String titulo, String descripcion, double precio) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Font font = new Font("Arial", Font.PLAIN, 20);

        this.add(new JSeparator(SwingConstants.HORIZONTAL));
        this.add(new JLabel(" "));
        this.add(new JLabelFont("<html><b>#" + id + "</b></html>", font));
        this.add(new JLabel(" "));
        this.add(new JLabelFont("<html><b>Fecha: </b>" + fecha + "</html>", font));
        this.add(new JLabelFont("<html><b>Precio: $</b>" + precio + "</html>", font));
        this.add(new JLabelFont("<html><b>Título: </b>" + titulo + "</html>", font));

        this.add(new JLabelFont("<html><b>Descripción:</b></html>", font));
        String formattedDescription = "dhgffgdhghfdsgfhsghfhgfshsgfd";
        this.add(new JLabelFont(formattedDescription, font));
        this.add(new JLabel(" "));



    }

}
