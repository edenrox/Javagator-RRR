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
    public static final char SPACE_CHAR = ' ';
    public static final String NEWLINE_STRING = "\n";
    
    protected BufferedImage source;
    
    @Override
    public void load() throws Exception {
        source = ImageUtils.loadSprite(SPRITE_PATH);
    }
    
    public static String wordWrap(String text, int maxWidth) {
        StringBuilder sb = new StringBuilder();
        
        while (text.length() > maxWidth) {
            // Find where we need to wrap the line
            int pos = text.lastIndexOf(SPACE_CHAR, maxWidth);
            if (pos < 0) {
                pos = maxWidth;
            }
            
            // wrap the line
            sb.append(text.substring(0, pos+1));
            sb.append(NEWLINE_STRING);
            text = text.substring(pos+1);
        }
        if (text.length() > 0) {
            sb.append(text);
        }
        
        
        return sb.toString();
    }
    
    public void renderText(Graphics g, int x, int y, String text) {
        String lines[] = text.split(NEWLINE_STRING);
        int cy = y;
        int cx;
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
