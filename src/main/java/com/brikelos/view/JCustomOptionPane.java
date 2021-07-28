package com.brikelos.view;

import com.brikelos.model.models.Purchase;
import com.brikelos.model.queries.ClientQueries;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Arrays;

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

    /**
     * Confirm dialog for the purchase.
     * @param purchase
     * @return
     */
    public static int confirmDialog(Purchase purchase) {
        String description = "";
        for(String s: Arrays.asList(purchase.getDescription().split("\\r?\\n"))) {
            if(s.length() > 80) {
                int index = 0;
                while (index < s.length()) {
                    description += (s.substring(index, Math.min(index + 80,s.length())) + "<br>");
                    index += 80;
                }
            } else {
                description += s + "<br>";
            }
        }

        String txt =
                "<html><b>¿Son correctos estos datos de la venta?</b><br><br>"          +
                "<b>Fecha</b>: "       + purchase.getDate()                                 + "<br>" +
                "<b>Comprador</b>: "   + ClientQueries.getClientById(purchase.getBuyerID()) + "<br>" +
                "<b>Precio</b>: "      + purchase.getPrice()                                + "<br>" +
                "<b>Título</b>: "      + purchase.getTitle()                                + "<br>" +
                "<b>Descripción</b>: <br>" + description                                + "</html>";

        Object[] options = { "Si", "No", "Cancelar" };
        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,20)));

        int result = JOptionPane.showOptionDialog(
                null,
                new JLabelFont(txt, new Font("Arial", Font.PLAIN, 24)),
                "Confirmar datos",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null
        );
        return result;
    }

}
