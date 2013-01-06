/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public abstract class ViewWithBackground extends AppView {
    
    protected BufferedImage bgBuffer = null;
    
    protected abstract void renderBackground(Graphics g);
    protected abstract void renderForeground(Graphics g, long ticks);
    
    @Override
    public void render(Graphics g, long ticks) {
        if (bgBuffer == null) {
            bgBuffer = new BufferedImage(Screen.WIDTH, Screen.HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics bgg = bgBuffer.createGraphics();
            renderBackground(bgg);
            bgg.dispose();
        }
        g.drawImage(bgBuffer, 0, 0, null);
        
        renderForeground(g, ticks);
    }
}
