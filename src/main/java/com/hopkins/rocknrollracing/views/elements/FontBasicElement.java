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
public class FontBasicElement extends AppElement {
    public static final String SPRITE_PATH = "images/font/tiles.png";
    public static final char MINIMUM = '!';
    public static final char MAXIMUM = '_';
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    
    protected BufferedImage source;
    
    @Override
    public void load() throws Exception {
        source = ImageUtils.loadSprite(SPRITE_PATH);
    }
    
    public void renderText(Graphics g, int x, int y, String text) {
        String lines[] = text.split("\n");
        int cy = y;
        int cx = x;
        for(String line : lines) {
            cx = x;
            for(char c : line.toCharArray()) {
                renderChar(g, cx, cy, c);
                cx += WIDTH;
            }
            cy += HEIGHT;
        }
    }
    
    public void renderChar(Graphics g, int x, int y, char letter) {
        letter = Character.toUpperCase(letter);
        if (inRange(letter)) {
            int index = indexOf(letter);
            
            SpriteRenderer.render(g, source, x, y, WIDTH, HEIGHT, index, false, false);
        }
    }
    
    protected int indexOf(char c) {
        return (int) (c - MINIMUM);
    }
    
    protected boolean inRange(char c) {
        return (c >= MINIMUM) && (c <= MAXIMUM);
    }
}
