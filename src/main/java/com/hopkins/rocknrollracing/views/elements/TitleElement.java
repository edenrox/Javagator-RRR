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
 * @author ihopkins
 */
public class TitleElement extends AppElement {
    
    public static final String SPRITE_PATH = "images/intro/rocknrollracing.png";
    
    protected BufferedImage title;

    @Override
    public void load() throws Exception {
        title = ImageUtils.loadSprite(SPRITE_PATH);
    }
    
    public void render(Graphics g, int x, int y) {
        g.drawImage(title, x, y, null);
    }
}
