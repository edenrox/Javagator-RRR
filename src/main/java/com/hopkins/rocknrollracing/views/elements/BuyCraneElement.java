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
public class BuyCraneElement extends AppElement {

    public static String SPRITE_PATH = "images/buy/crane.png";
    
    protected BufferedImage craneImage;
    
    @Override
    public void load() throws Exception {
        craneImage = ImageUtils.loadSprite(SPRITE_PATH);
    }
    
    public void render(Graphics g, int x, int y) {
        g.drawImage(craneImage, x, y, null);
    }
}
