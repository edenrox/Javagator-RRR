/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class BuyMenuElement extends AppElement {
    
    public static final String SPRITE_PATH = "images/buy/menu.png";
    public static final int NUM_BUTTONS = 7;
    
    protected BufferedImage menuImage;

    @Override
    public void load() throws Exception {
        menuImage = ImageUtils.loadSprite(SPRITE_PATH);
    }
    
    public int getMenuY(int origin_y, int position) {
        int h = 16;
        int space_y = 1;
        int big_space_y = 7;
        
        int dy1 = origin_y + (h+space_y) * position;
        if (position > 1) {
            dy1 += big_space_y;
        }
        return dy1;
    }
    
    public void render(Graphics g, int x, int y) {
        int origin_x = x;
        int origin_y = y;
        int w = 32;
        int h = 16;
        
        g.setColor(Color.BLACK);
        for (int i = 0; i < NUM_BUTTONS; i++) {
            int sx1, sx2, sy1, sy2;
            int dx1, dx2, dy1, dy2;
            
            dx1 = origin_x;
            dy1 = getMenuY(origin_y, i);
            
            dx2 = dx1 + w;
            dy2 = dy1 + h;
            
            sx1 = 0;
            sx2 = sx1 + w;
            sy1 = h * i;
            sy2 = sy1 + h;
            
            g.fillRect(dx1+1, dy1+1, w, h);
            
            g.drawImage(menuImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        }
        
        
        
    }
}
