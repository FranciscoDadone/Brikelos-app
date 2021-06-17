package com.brikelos.util;

import com.brikelos.view.MainGUI;

import javax.swing.*;
import java.awt.*;

public class GUIHandler {

    /**
     * Creates the main app JFrame.
     */
    public static void main() {
        new MainGUI("Brikelos");
    }

    /**
     * Handles the screen change.
     * @param newScreen
     */
    public static void changeScreen(JPanel newScreen) {
        MainGUI.getContentPanel().removeAll();
        MainGUI.getContentPanel().add(newScreen);
        MainGUI.getContentPanel().revalidate();
        MainGUI.getContentPanel().repaint();
        MainGUI.getContentPanel().setBackground(new Color(251, 235, 222));
    }
}
