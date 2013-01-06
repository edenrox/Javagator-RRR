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
 * @author ian
 */
public class BuyPlaceElement extends AppElement {

    public static String SPRITE_PATH = "images/buy/place.png";
    
    protected BufferedImage placeImage;
    
    @Override
    public void load() throws Exception {
        placeImage = ImageUtils.loadSprite(SPRITE_PATH);
    }
    
    public void render(Graphics g, int x, int y) {
        g.drawImage(placeImage, x, y, null);
    }
}
