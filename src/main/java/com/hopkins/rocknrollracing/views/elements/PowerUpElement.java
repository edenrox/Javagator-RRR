/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class PowerUpElement extends AppElement {
    public static final String SPRITE_PATH = "images/powerups/%s.png";
    
    protected BufferedImage gold, health;

    @Override
    public void load() throws Exception {
        gold = loadSprite("gold");
        health = loadSprite("health");
    }
    
    protected BufferedImage loadSprite(String name) throws Exception {
        return ImageUtils.loadSprite(String.format(SPRITE_PATH, name));
    }
    
    
    
}
