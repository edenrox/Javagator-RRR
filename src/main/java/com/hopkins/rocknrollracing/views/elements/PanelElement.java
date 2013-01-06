/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author ihopkins
 */
public class PanelElement extends AppElement {

    
    public static final Color BOX_RED = new Color(121, 5, 4);
    public static final Color BOX_RED_LIGHT = new Color(165, 10, 7);
    public static final Color BOX_RED_DARK = new Color(75, 3, 2);
    
    public static final Color BOX_GRAY = new Color(81, 81, 81);
    public static final Color BOX_GRAY_LIGHT = new Color(121,121,121);
    public static final Color BOX_GRAY_DARK = new Color(40, 40, 40);
    
    public static final Color BOX_BLUE = new Color(0, 0, 82);
    public static final Color BOX_BLUE_LIGHT = new Color(0, 0, 197);
    public static final Color BOX_BLUE_DARK = new Color(0, 0, 0);
    
    public static final Color BOX_SHADOW = new Color(66, 2, 1);
    
    public static final Color BOX_BLACK = new Color(0,0,0);
    
    @Override
    public void load() throws Exception {
        
    }
    public void renderBluePanel(Graphics g, int x, int y, int width, int height, int depth) {
        
        g.setColor(BOX_BLUE);
        g.fillRect(x, y, width, height);
        
        renderBevel(g, x, y, width, height, depth, BOX_BLUE_LIGHT, BOX_BLUE_DARK);
    }
    
    public void renderRedPanel(Graphics g, int x, int y, int width, int height, int depth) {
        
        g.setColor(BOX_RED);
        g.fillRect(x, y, width, height);
        
        renderBevel(g, x, y, width, height, depth, BOX_RED_LIGHT, BOX_RED_DARK);
    }
    
    protected void renderBevel(Graphics g, int x, int y, int width, int height, int depth, Color highlight, Color lowlight) {
        // Top left highlight
        g.setColor(highlight);
        for(int i = 0; i < depth; i++) {
            g.drawLine(x, y + i, x + width - i - 1, y + i);
            g.drawLine(x + i, y, x + i, y + height - i - 1);
        }
        
        // Bottom right shadow
        g.setColor(lowlight);
        for(int i = 0; i < depth; i++) {
            g.drawLine(x + i + 1, y + height - i, x + width, y + height - i);
            g.drawLine(x + width - i, y + i + 1, x + width - i, y + height);
        }
    }
    
    
    public void renderBlackInlayRed(Graphics g, int x, int y, int width, int height, int depth) {
        
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        
        renderBevel(g, x, y, width, height, depth, BOX_RED_DARK, BOX_RED_LIGHT);
        
    }
    
    public void renderBlackInlayGrey(Graphics g, int x, int y, int width, int height) {
        
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        
        renderBevel(g, x, y, width, height, 1, BOX_GRAY_DARK, BOX_GRAY_LIGHT);
        
    }
    
    public void renderGrayPanel(Graphics g, int x, int y, int width, int height) {
        
        // The Shadow
        g.setColor(BOX_SHADOW);
        g.fillRoundRect(x + 8, y + 8, width, height, 16, 16);
        
        // The Gray
        g.setColor(BOX_GRAY);
        g.fillRoundRect(x, y, width, height, 16, 16);
        
        // The highlight
        
        
    }
    
}
