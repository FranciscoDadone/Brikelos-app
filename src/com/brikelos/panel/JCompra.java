package com.brikelos.panel;

import javax.swing.*;

public class JCompra extends JPanel {

    public JCompra(String nombre, String dni, String tel, String email) {

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(new JLabel("Nombre: " + nombre));
        this.add(new JLabel("DNI: " + dni));
        this.add(new JLabel("Tel.: " + tel));
        this.add(new JLabel("Email: " + email));

    }

}
