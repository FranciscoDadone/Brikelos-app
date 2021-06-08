package com.brikelos.gui;

import javax.swing.*;

public class Handler {

    public static void main() {
        new MainGUI("Brikelos");
    }

    public static void agregarVenta(JPanel panel) {

        panel.add(new AgregarVentaPanel().getPanel());
        panel.revalidate();
        panel.repaint();

    }

}
