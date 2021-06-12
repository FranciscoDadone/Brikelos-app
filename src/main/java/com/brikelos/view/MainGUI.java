package com.brikelos.view;

import com.brikelos.controller.MainGUIController;
import com.brikelos.util.GUIHandler;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class MainGUI extends JFrame {

    /**
     * Returns the contentPanel (panel in the center of the screen).
     * @return JPanel
     */
    public static JPanel getContentPanel() {
        return contentPanel;
    }

    /**
     * Calls the JFrame constructor and sets up the main frame configuration.
     * @param title
     */
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
        GUIHandler.changeScreen(new AddSellPanel().getPanel());

        /**
         * Listeners
         */
        addSellButton.addActionListener(new MainGUIController(this));
        showClientsButton.addActionListener(new MainGUIController(this));
        newClientButton.addActionListener(new MainGUIController(this));
    }


    private JPanel mainPanel;
    public JButton newClientButton;
    public JButton addSellButton;
    public JButton showClientsButton;
    private JPanel sidebar;
    private static JPanel contentPanel;
}
