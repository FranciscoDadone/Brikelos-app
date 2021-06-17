package com.brikelos.view;

import java.awt.*;
import javax.swing.JPanel;

/**
 * Class to put a image as a background of JPanel.
 */
public class JPanelBackground extends JPanel {

    private static final long serialVersionUID = 1L;
    private Image image;
    private int iWidth2;
    private int iHeight2;

    public JPanelBackground(Image image)
    {
        this.image = image;
        this.iWidth2 = image.getWidth(this)/2;
        this.iHeight2 = image.getHeight(this)/2;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null)
        {
            g.drawImage(image, 0, 0, this);
        }
    }
}
