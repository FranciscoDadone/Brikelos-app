package com.brikelos.view;

import com.brikelos.controller.DeletePurchase;
import com.brikelos.controller.EditPurchase;
import com.brikelos.model.models.Purchase;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class JPurchase extends JPanel {

    /**
     * JPanel to show a purchase.
     * @param id
     */
    public JPurchase(int id, Purchase purchase) {
        this.setLayout(new BorderLayout());
        Font font = new Font("Arial", Font.PLAIN, 20);

        mainInfo = new JPanel();
        mainInfo.setLayout(new BoxLayout(mainInfo, BoxLayout.PAGE_AXIS));

        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black), new EmptyBorder(10, 10, 10, 10)));

        mainInfo.add(new JLabel(" "));

        mainInfo.add(new JLabelFont("<html>&nbsp<b>#" + id + "</b></html>", font));

        mainInfo.add(new JLabel(" "));
        mainInfo.add(new JLabelFont("<html><b>Fecha: </b>" + purchase.getDate() + "</html>", font));
        mainInfo.add(new JLabelFont("<html><b>Precio: $</b>" + purchase.getPrice() + "</html>", font));
        mainInfo.add(new JLabelFont("<html><b>Título: </b>" + purchase.getTitle() + "</html>", font));

        mainInfo.add(new JLabelFont("<html><b>Descripción:</b></html>", font));

        for(String s: Arrays.asList(purchase.getDescription().split("\n"))) {
            if(s.length() > 80) {
                int index = 0;
                while (index < s.length()) {
                    mainInfo.add(new JLabelFont((s.substring(index, Math.min(index + 80, s.length()))), font));
                    index += 80;
                }
            } else {
                mainInfo.add(new JLabelFont(s, font));
            }
        }

        this.add(mainInfo, BorderLayout.CENTER);

        buttons = new JPanel();


        editBtn = new JButton("Editar");
        editBtn.setFont(font);
        deleteBtn = new JButton("Eliminar");
        deleteBtn.setFont(font);

        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(editBtn);
        buttons.add(deleteBtn);
        this.add(buttons, BorderLayout.PAGE_END);
        editBtn.addActionListener(new EditPurchase(purchase, this));
        deleteBtn.addActionListener(new DeletePurchase(purchase));
    }

    private JButton editBtn;
    private static JButton deleteBtn;
    private JPanel buttons, mainInfo;
}
