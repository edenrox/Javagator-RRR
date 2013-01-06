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
 * @author ihopkins
 */
public class CrystalElement extends AppElement {
    
    public static final String SPRITE_PATH = "images/between/crystal.png";
    
    public BufferedImage glassImage; 

    @Override
    public void load() throws Exception {
        glassImage = ImageUtils.loadSprite(SPRITE_PATH);
    }
    
    
    public void render(Graphics g, int x, int y) {
       
        // Draw the shadow
        g.setColor(PanelElement.BOX_SHADOW);
        g.fillArc(x+8, y+8, 57, 60, 0, 360);
        
        // Draw the crystal
        g.drawImage(glassImage, x, y, null);
        
    }
}
