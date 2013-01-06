/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.RaceState;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class HudElement extends AppElement {
    public static String SPRITE_PATH = "images/hud/tiles.png";
    
    BufferedImage tiles;

    @Override
    public void load() throws Exception {
        tiles = ImageUtils.loadSprite(SPRITE_PATH);
    }
    
    
    public void renderHud(Graphics g, RaceState rs) {
        
    }
    
    
    
}
