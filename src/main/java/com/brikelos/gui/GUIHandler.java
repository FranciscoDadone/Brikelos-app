package com.brikelos.gui;

import javax.swing.*;

public class GUIHandler {

    public static void main() {
        new MainGUI("Brikelos");
    }

    public static void changeScreen(JPanel panel, JPanel newScreen) {
        panel.removeAll();
        panel.add(newScreen);
        panel.revalidate();
        panel.repaint();
    }
}
