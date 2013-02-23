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
public class EffectsElement extends AppElement {

    public static final String SPRITE_PATH = "images/cars/effects/%s.png";
    public static final int LARGE_SPRITE = 48;
    public static final int SMALL_SPRITE = 16;
    
    protected BufferedImage smoke, explosion, nitro, parts;
    
    
    @Override
    public void load() throws Exception {
        smoke = ImageUtils.loadSprite(String.format(SPRITE_PATH, "smoke"));
        explosion = ImageUtils.loadSprite(String.format(SPRITE_PATH, "explosion"));
        nitro = ImageUtils.loadSprite(String.format(SPRITE_PATH, "nitro"));
        parts = ImageUtils.loadSprite(String.format(SPRITE_PATH, "parts"));
    }
    
    public void renderSmoke(Graphics g, int x, int y, int frame) {
        renderSmallSprite(g, x, y, frame, smoke, SMALL_SPRITE);
    }
    
    public void renderNitro(Graphics g, int x, int y, int frame) {
        renderSmallSprite(g, x, y, frame, nitro, SMALL_SPRITE);
    }
    
    public void renderPart(Graphics g, int x, int y, int frame) {
        renderSmallSprite(g, x, y, frame, parts, SMALL_SPRITE);
    }
    
    public void renderExplosion(Graphics g, int x, int y, int frame) {
        renderSmallSprite(g, x, y, frame, explosion, LARGE_SPRITE);
    }
    
    protected void renderSmallSprite(Graphics g, int x, int y, int frame, BufferedImage image, int size) {
        SpriteRenderer.render(g, image, x, y, size, size, frame, false, false);
    }
    
    
    
}
