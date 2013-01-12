/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.UpgradeType;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ihopkins
 */
public class UpgradeElement extends AppElement {
    
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    public static final String SPRITE_PATH = "images/upgrades/%s.png";
    
    protected BufferedImage[] upgradeSprites;
    
    @Override
    public void load() throws Exception {
        UpgradeType all[] = UpgradeType.values();
        
        upgradeSprites = new BufferedImage[all.length];
        for(int i = 0; i < all.length; i++) {
            upgradeSprites[i] = ImageUtils.loadSprite(String.format(SPRITE_PATH, all[i]));
        }
        
        
    }
    
    public void renderUpgradeIcon(Graphics g, int x, int y, UpgradeType type, int offset, boolean isHighlighted) {
        
        int spriteIndex = type.ordinal();       
        Color c = MenuColors.Red;
        if (isHighlighted) {
            c = MenuColors.Yellow;
        }
        g.setColor(c);
        g.drawRect(x, y, WIDTH - 1, HEIGHT - 1);
        SpriteRenderer.render(g, upgradeSprites[spriteIndex], x, y, WIDTH, HEIGHT, offset, false, false);
    }
    
}
