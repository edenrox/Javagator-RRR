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
public class SmokeElement extends AppElement {
    public static final String SPRITE_PATH = "images/smoke/smoke.png";
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    
    protected BufferedImage smoke;
    
    @Override
    public void load() throws Exception {
        smoke = ImageUtils.loadSprite(SPRITE_PATH);
    }
    
    public void render(Graphics g, int x, int y, int frame) {
        SpriteRenderer.render(g, smoke, x, y, WIDTH, HEIGHT, frame, false, false);
    }
    
    
}
