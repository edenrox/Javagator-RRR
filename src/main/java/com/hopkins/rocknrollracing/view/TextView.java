/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.view;

import com.hopkins.rocknrollracing.Application;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class TextView {
    
    public static final String SPRITE_PATH = "images/font.png";
    public static final int SPRITE_WIDTH = 8;
    public static final int SPRITE_HEIGHT = 8;
    
    public static final int NUM_CHARS = 58;
    public static final String CHARACTER_MAP = "!\"#$%&'()*+,-./0123456789:,<=>?@abcdefghijklmnopqrstuvwxyz";
    public static final char NEWLINE = '\n';
    
    protected Application app;
    
    public TextView(Application theApp) {
        app = theApp;
    }
    
    public void drawText(Graphics2D g, int sx, int sy, String text) {
        int cx = sx;
        int cy = sy;
        // The SNES font only contains uppercase characters
        text = text.toUpperCase();
        
        for(char item : text.toCharArray()) {
            if (item == NEWLINE) {
                // next line, at the beginning of the line
                cy += SPRITE_HEIGHT;
                cx = sx;
            } else {
                drawCharacter(g, cx, cy, item);
                cx += SPRITE_WIDTH;
            }
        }
    }
    
    public void drawCharacter(Graphics2D g, int sx, int sy, char c) {
        // the position of the character in the sprite
        int pos = (c - '!');
        
        if ((pos < 0) || (pos >= NUM_CHARS)) {
            // character isn't in the sprite map
            return;
        }
        
        // load the sprite
        Image sprite = app.sprites.getSprite(SPRITE_PATH);
        SpriteUtils.drawSprite(g, sprite, pos * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT, sx, sy, false);
    }
    
    
    
}
