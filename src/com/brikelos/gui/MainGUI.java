package com.brikelos.gui;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    private JPanel mainPanel;
    private JButton NUEVOCLIENTEButton;
    private JButton AGREGARVENTAButton;
    private JButton VERCLIENTESButton;
    private JPanel sidebar;
    private JPanel contentPanel;

    public MainGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("./../images/logo.png")));

        this.setVisible(true);

        AGREGARVENTAButton.addActionListener(e -> {

        });
        VERCLIENTESButton.addActionListener(e -> {

        });
        NUEVOCLIENTEButton.addActionListener(e -> {

        });
    }
}
