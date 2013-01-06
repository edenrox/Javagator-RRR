/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.animation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ihopkins
 */
public class ImageAnimation extends Animation {
    
    protected BufferedImage image;
    
    public ImageAnimation(BufferedImage img) {
        image = img;
    }

    @Override
    protected void renderFrame(Graphics g, int x, int y, long frame) {
        g.drawImage(image, x, y, null);
    }
    
    
    
    
}
