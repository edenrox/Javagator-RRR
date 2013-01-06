/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.stateeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author ian
 */
public class ImagePanel extends JPanel {
    
    protected BufferedImage image;
    protected double scale = 1.0;
    
    public BufferedImage getImage() { 
        return image;
    }
    public void setImage(BufferedImage value) {
        image = value;
        this.repaint();
    }
    public double getScale() {
        return scale;
    }
    public void setScale(double value) {
        scale = value;
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.drawRect(0, 0, getWidth(), getHeight());
        if (image != null) {
            int dx1, dx2, dy1, dy2, dw, dh, sx1, sx2, sy1, sy2;
            
            sx1 = 0;
            sy1 = 0;
            sx2 = image.getWidth();
            sy2 = image.getHeight();
            
            dw = (int) (sx2 * scale);
            dh = (int) (sy2 * scale);
            dx1 = (this.getWidth() - dw) / 2;
            dy1 = (this.getHeight() - dh) / 2;
            dx2 = dx1 + dw;
            dy2 = dy1 + dh;
            
            g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        }
    }
    
    
}
