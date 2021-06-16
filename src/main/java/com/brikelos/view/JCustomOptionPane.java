package com.brikelos.view;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class JCustomOptionPane {

    /**
     * Custom warning dialog with bigger font.
     * @param txt
     * @param title
     * @param warningType
     */
    public static void messageDialog(String txt, String title, final int warningType) {
        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,20)));
         JOptionPane.showMessageDialog(
                null,
                new JLabelFont(txt, new Font("Arial", Font.BOLD, 24)),
                title,
                warningType
        );
    }

    /**
     * Custom message dialog with bigger font and custom buttons.
     * @param txt
     * @param title
     * @return
     */
    public static int confirmDialog(String txt, String title) {
        Object[] options = { "Si", "No", "Cancelar" };
        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,20)));
        int result = JOptionPane.showOptionDialog(
                null,
                new JLabelFont(txt, new Font("Arial", Font.BOLD, 24)),
                title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null
        );
        return result;
    }

}
