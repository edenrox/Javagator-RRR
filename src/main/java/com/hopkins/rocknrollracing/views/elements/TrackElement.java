/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.utils.ImageUtils;
import com.hopkins.rocknrollracing.views.Screen;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class TrackElement extends AppElement {
    public static final String SPRITE_PATH = "images/track/%s/%s.png";
    
    public static final int PIECE_WIDTH = 32;
    public static final int PIECE_HEIGHT = 8;
    
    protected BufferedImage road, edgeTop, edgeBottom;
    
    
    

    @Override
    public void load() throws Exception {
        String planet = "chem_vi";
        road = loadSprite(planet, "road");
        edgeTop = loadSprite(planet, "edgeTop");
        edgeBottom = loadSprite(planet, "edgeBottom");
               
    }
    
    protected BufferedImage loadSprite(String planet, String name) throws Exception {
        return ImageUtils.loadSprite(String.format(SPRITE_PATH, planet, name));
    }
    
    
    public void renderAll(Graphics g) {
        int frame = 0;
        for (int y = 0; y < Screen.HEIGHT; y += PIECE_HEIGHT) {
            for (int x = 0; x < Screen.WIDTH; x += PIECE_WIDTH) {
                SpriteRenderer.render(g, road, x, y, PIECE_WIDTH, PIECE_HEIGHT, frame, false, false);
                frame = (frame + 1) % 2;
            }
            frame = (frame + 1) % 2;
        }
    }
    
}
