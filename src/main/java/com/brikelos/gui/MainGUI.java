package com.brikelos.gui;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

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

        /**
         * Code to load the logo in the app icon (maybe not working now)
         */
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("logo.png");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(String.valueOf(is)));

        contentPanel = new JPanel();
        mainPanel.add(new JScrollPane(contentPanel));
        this.setVisible(true);
        Handler.changeScreen(contentPanel, new AgregarVentaPanel().getPanel());


        /**
         * Triggered when clicked on 'AGREGAR VENTA'
         */
        AGREGARVENTAButton.addActionListener(e -> {
            Handler.changeScreen(contentPanel, new AgregarVentaPanel().getPanel());
        });

        /**
         * Triggered when clicked on 'VER CLIENTES'
         */
        VERCLIENTESButton.addActionListener(e -> {
            Handler.changeScreen(contentPanel, new VerClientesPanel().getPanel());
        });

        /**
         * Triggered when clicked on 'NUEVO CLIENTE'
         */
        NUEVOCLIENTEButton.addActionListener(e -> {
            Handler.changeScreen(contentPanel, new AgregarClientePanel().getPanel());
        });
    }
}
