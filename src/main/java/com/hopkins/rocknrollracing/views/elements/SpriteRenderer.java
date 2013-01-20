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
        render(g, img, x, y, w, h, frame, vFlip, hFlip, 1.0f);
    }
    public static void render(Graphics g, BufferedImage img, int x, int y, int w, int h, int frame, boolean vFlip, boolean hFlip, float scale) {
        int sx1, sx2, sy1, sy2;
        int dx1, dx2, dy1, dy2;
        int dw, dh;
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
        
        dw = w;
        dh = h;
        if (Math.abs(scale - 1.0f) > 0.001) {
            dw = (int) Math.round(w * scale);
            dh = (int) Math.round(h * scale);
        }
        
        dx1 = x;
        dx2 = dx1 + dw;
        dy1 = y;
        dy2 = dy1 + dh;
        
        g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
    }
    
    
}
