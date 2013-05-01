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
public class CarEffectElement extends AppElement {
    
    public static final String SPRITE_PATH = "car/effects/%s.png";
    
    protected BufferedImage[] sprites;
    protected int[] frames;

    @Override
    public void load() throws Exception {
        int NumSprites = CarEffectType.values().length;
        sprites = new BufferedImage[NumSprites];
        frames = new int[NumSprites];
        
        // load the sprites and calculate how many frames each has
        for(CarEffectType type : CarEffectType.values()) {
            int index = type.ordinal();
            sprites[index] = loadSprite(SPRITE_PATH, type.toString().toLowerCase());
            frames[index] = sprites[index].getWidth() / sprites[index].getHeight();
        }
    }
    
    /**
     * Render a car effect sprite
     */
    public void renderSprite(Graphics g, CarEffectType type, int frame, int x, int y, boolean hFlip) {
        BufferedImage bi = sprites[type.ordinal()];
        int size = bi.getHeight();
        SpriteRenderer.render(g, bi, x, y, size, size, frame, false, hFlip);
    }
    
    /**
     * Render a car effect over top of a car (for an explosion, the car should be missing)
     */
    public void renderEffect(Graphics g, CarEffectType type, long ticks, int cx, int cy, int angle) {
        
    }
    
    /**
     * Get the number of frames for a given effect type
     */
    public int getFrames(CarEffectType type) {
        return frames[type.ordinal()];
    }
}
