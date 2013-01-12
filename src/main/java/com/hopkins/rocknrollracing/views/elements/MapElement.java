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
public class MapElement extends AppElement {
    public static final String SPRITE_PATH = "images/hud/%s.png";
    
    protected BufferedImage track;
    protected BufferedImage position;

    @Override
    public void load() throws Exception {
        track = loadSprite("track");
        position = loadSprite("position");
    }
    
    protected BufferedImage loadSprite(String name) throws Exception {
        return ImageUtils.loadSprite(String.format(SPRITE_PATH, name));
    }
    
    
    
}
