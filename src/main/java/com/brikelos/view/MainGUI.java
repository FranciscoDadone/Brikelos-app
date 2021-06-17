package com.brikelos.view;

import com.brikelos.controller.MainGUIController;
import com.brikelos.util.GUIHandler;
import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    /**
     * Returns the contentPanel (panel in the center of the screen).
     * @return JPanel
     */
    public static JPanelBackground getContentPanel() {
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
         * Code to load the logo in the app icon.
         */
        this.setIconImage(new ImageIcon(getClass().getResource("/images/logo.jpg")).getImage());

        /**
         * Image as background
         */
        contentPanel = new JPanelBackground(new ImageIcon(getClass().getResource("/images/libros.jpg")).getImage().getScaledInstance(1613, 955, Image.SCALE_DEFAULT));
        mainPanel.add(new JScrollPane(contentPanel));

        this.setVisible(true);
        GUIHandler.changeScreen(new AddSellPanel().getPanel());

        /**
         * Listeners
         */
        addSellButton.addActionListener    (new MainGUIController(this));
        showClientsButton.addActionListener(new MainGUIController(this));
        newClientButton.addActionListener  (new MainGUIController(this));
        configButton.addActionListener     (new MainGUIController(this));
        configButton.addMouseListener      (new MainGUIController(this));
        newClientButton.addMouseListener   (new MainGUIController(this));
        showClientsButton.addMouseListener (new MainGUIController(this));
        addSellButton.addMouseListener     (new MainGUIController(this));

    }


    private JPanel mainPanel;
    public JButton newClientButton;
    public JButton addSellButton;
    public JButton showClientsButton;
    private JPanel sidebar;
    public JButton configButton;
    private static JPanelBackground contentPanel;
}
