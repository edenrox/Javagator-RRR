/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author ian
 */
public class SpriteUtils {

    public static boolean drawSprite(Graphics2D g, Image image, int sourceX, int sourceY, int width, int height, int destX, int destY, boolean hFlip) {
        int dx1 = destX;
        int dx2 = destX + width;
        if (hFlip) {
            dx1 = destX + width;
            dx2 = destX;
        }

        return g.drawImage(image,
                dx1, destY, dx2, destY + height,
                sourceX, sourceY, sourceX + width, sourceY + height, null);
    }

    public static BufferedImage colorTransform(BufferedImage image, ColorMap map) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int c = image.getRGB(x, y);
                if (map.has(c)) {
                    c = map.get(c);
                } else if (c != 0) {
                    System.err.println(Integer.toHexString(c));
                }
                dest.setRGB(x, y, c);
            }
        }

        return dest;
    }
    
    
}
