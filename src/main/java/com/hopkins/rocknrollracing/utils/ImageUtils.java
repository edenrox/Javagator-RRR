/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.utils;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author ian
 */
public class ImageUtils {
    
    public static boolean spriteExists(String path) {
        return (ImageUtils.class.getClassLoader().getResource(path) != null);
    }
    
    public static BufferedImage loadSprite(String path) throws ImageLoadException {
        try {
            InputStream is = ImageUtils.class.getClassLoader().getResourceAsStream(path);
            BufferedImage image = ImageIO.read(is);
            is.close();
            return image;
        } catch (Exception ex) {
            throw new ImageLoadException(String.format(
                    "Error loading sprite: %s",
                    path), ex);
        }
    }
    
    public static class ImageLoadException extends Exception {
        public ImageLoadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
}
