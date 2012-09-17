/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.view;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

/**
 *
 * @author ian
 */
public class SpriteCache {

    public static final Logger log = Logger.getLogger(SpriteCache.class);
    
    protected HashMap<String, BufferedImage> cache;

    public SpriteCache() {
        cache = new HashMap<String, BufferedImage>();
    }

    public BufferedImage getSprite(String path) {
        if (!cache.containsKey(path)) {
            cache.put(path, loadSprite(path));
        }
        return cache.get(path);
    }

    public BufferedImage loadSprite(String path) {
        try {
            return ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(path));
        } catch (Exception ex) {
            log.error(String.format("Could not find sprite (%s).", path), ex);
            return null;
        }
    }
}
