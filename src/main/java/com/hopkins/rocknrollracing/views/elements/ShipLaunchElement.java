/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class ShipLaunchElement extends AppElement {
    
    public static final String SPRITE_PATH = "images/ship/%s.png";

    protected BufferedImage ship, cradle, thrust;
    
    @Override
    public void load() throws Exception {
        ship = loadSprite(SPRITE_PATH,  "ship");
        cradle = loadSprite(SPRITE_PATH, "cradle");
        thrust = loadSprite(SPRITE_PATH, "thrust");
    }
    
    
    public void renderCradle(Graphics g, int x, int y) {
        g.drawImage(cradle, x, y, null);
    }
    
    public void renderShip(Graphics g, int x, int y, int thrustFrame) {
        g.drawImage(ship, x, y, null);
        
        if (thrustFrame >= 0) {
            int tx = x + 11;
            int ty = x + 59;
            renderThrust(g, tx, ty, thrustFrame);
            tx = x + 55;
            thrustFrame = (thrustFrame + 1) % 4;
            renderThrust(g, tx, ty, thrustFrame);
        }
    }
    
    protected void renderThrust(Graphics g, int x, int y, int frame) {
        boolean hFlip = (frame % 2 == 1);
        frame = frame / 2;
        SpriteRenderer.render(g, thrust, x, y, 8, 14, frame, false, hFlip);
    }
    
    
}
