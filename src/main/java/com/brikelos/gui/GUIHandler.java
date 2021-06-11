package com.brikelos.gui;

import javax.swing.*;

public class GUIHandler {

    public static void main() {
        new MainGUI("Brikelos");
    }

    public static void changeScreen(JPanel newScreen) {
        MainGUI.getContentPanel().removeAll();
        MainGUI.getContentPanel().add(newScreen);
        MainGUI.getContentPanel().revalidate();
        MainGUI.getContentPanel().repaint();
    }
}
