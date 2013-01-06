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
public class FontIntroElement extends AppElement {
    public static final String SPRITE_PATH = "images/font/intro/%s.png";
    public static final char MINIMUM = 'A';
    public static final char MAXIMUM = 'Z';
    public static final int HEIGHT = 16;
    public static final int SPACE_WIDTH = 14;
    
    BufferedImage characters[];

    @Override
    public void load() throws Exception {
        char chars[] = new char[29];
        characters = new BufferedImage[chars.length];
        int index = 0;
        for(char c = MINIMUM; c <= MAXIMUM; c++) {
            chars[index++] = c;
        }
        chars[index++] = '.';
        chars[index++] = '1';
        chars[index++] = '2';
        
        for(int i = 0; i < chars.length; i++) {
            String file = "" + chars[i];
            if (file.equals(".")) {
                file = "dot";
            }
            characters[i] = ImageUtils.loadSprite(String.format(
                    SPRITE_PATH,
                    file));
        }
    }
    
    public void renderText(Graphics g, int x, int y, String text) {
        String lines[] = text.split("\n");
        int cy = y;
        int cx = x;
        for(String line : lines) {
            cx = x;
            for(char c : line.toCharArray()) {
                renderChar(g, cx, cy, c);
                cx += getCharWidth(c);
            }
            cy += HEIGHT;
        }
    }
    
    protected int getCharWidth(char letter) {
        letter = Character.toUpperCase(letter);
        if (letter == ' ') {
            return SPACE_WIDTH;
        }
        if (inRange(letter)) {
            return characters[indexOf(letter)].getWidth();
        }
        return 0;
    }
    
    public void renderChar(Graphics g, int x, int y, char letter) {
        letter = Character.toUpperCase(letter);
        if (inRange(letter)) {
            int index = indexOf(letter);
            BufferedImage source = characters[index];
            if (source.getHeight() > HEIGHT) {
                y += (HEIGHT - source.getHeight()) / 2;
            }
            g.drawImage(source, x, y, null);
        }
    }
    
    protected boolean inRange(char c) {
        switch (c) {
            case '.':
            case '1':
            case '2':
                return true;
            default:
                return ((c >= MINIMUM) && (c <= MAXIMUM));
        }
    }
    
    protected int indexOf(char c) {
        switch (c) {
            case '.':
                return 26;
            case '1':
                return 27;
            case '2':
                return 28;
            default:
                return (int) (c - MINIMUM);
        }
    }
    
    
}
