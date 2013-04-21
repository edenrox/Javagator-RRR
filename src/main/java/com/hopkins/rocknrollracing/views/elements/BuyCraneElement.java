/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class BuyCraneElement extends AppElement {
    
    public static final String SPRITE_PATH = "images/buy/crane_%s.png";
    
    protected BufferedImage arms, bottom, rails, top;
    
    @Override
    public void load() throws Exception {
        arms = loadSprite(SPRITE_PATH, "arms");
        bottom = loadSprite(SPRITE_PATH, "bottom");
        rails = loadSprite(SPRITE_PATH, "rails");
        top = loadSprite(SPRITE_PATH, "top");
    }
    
    public void renderRails(Graphics g, int x, int y) {
        // draw the rails for the crane
        g.drawImage(rails, x, y, null);
    }
    
    public void renderCrane(Graphics g, int x, int y, int cy, ArmPosition armPos) {
        
        // Left Arm of crane
        int aframe = armPos.ordinal();
        int ax = x - 9;
        int ay = y + cy + 32;
        SpriteRenderer.render(g, arms, ax, ay, 22, 20, aframe, false, false);
        
        // Bottom of the crane
        g.drawImage(bottom, x, y + cy - 48, null);
        
        // Top of the crane
        g.drawImage(top, x, y, null);
    }
    
    public void renderArmBehind(Graphics g, int x, int y, int cy, ArmPosition armPos) {
        int aframe = armPos.ordinal() + 3;
        int ax = x + 12;
        int ay = y + cy + 32;
        SpriteRenderer.render(g, arms, ax, ay, 22, 20, aframe, false, false);
    }
            
            
            
    public static enum ArmPosition {
        Closed, Gripping, Open
    }
}
