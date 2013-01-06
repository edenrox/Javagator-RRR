/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class BetweenMenuElement extends AppElement {
    public static final String SPRITE_PATH = "images/between/menu.png";
    public static final int NUM_OPTIONS = 5;
            
    public static final Color Yellow = new Color(248, 248, 0);
    public static final Color Gray = new Color(64, 64, 64);
    
    protected BufferedImage menuImage;
    
    @Override
    public void load() throws Exception {
        menuImage = ImageUtils.loadSprite(SPRITE_PATH);
    }
    
    public void render(Graphics g, int x, int y, int selected) {
        
        // Shadow
        g.setColor(PanelElement.BOX_SHADOW);
        g.fillRect(x + 8, y + 8, menuImage.getWidth(), menuImage.getHeight());
        
        // Highlight
        for(int i = 0; i < NUM_OPTIONS; i++) {
            if (selected == i) {
                g.setColor(Yellow);
            } else {
                g.setColor(Gray);
            }
            g.drawRect(x + 32 * i, y+1, 30, 21);
        }
        
        // Images
        g.drawImage(menuImage, x, y, null);
    }
}
