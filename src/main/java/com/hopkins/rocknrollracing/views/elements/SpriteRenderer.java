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
public class SpriteRenderer {

    public static void render(Graphics g, BufferedImage img, int x, int y, int w, int h, int frame, boolean vFlip, boolean hFlip) {
        int sx1, sx2, sy1, sy2;
        int dx1, dx2, dy1, dy2;
        int swap;
        
        sx1 = frame * w;
        sx2 = sx1 + w;
        sy1 = 0;
        sy2 = sy1 + h;
        
        if (vFlip) {
            swap = sy1;
            sy1 = sy2;
            sy2 = swap;
        }
        if (hFlip) {
            swap = sx1;
            sx1 = sx2;
            sx2 = swap;
        }
        
        dx1 = x;
        dx2 = dx1 + w;
        dy1 = y;
        dy2 = dy1 + h;
        
        g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
    }
    
    
}
