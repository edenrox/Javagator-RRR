/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.UpgradeType;
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
    
    public static final Color LEVEL_COLOR = new Color(200, 168, 0);
    public static final Color LEVEL_COLOR_MAX = new Color(248, 184, 0);
    
    protected BufferedImage[] upgradeSprites;
    
    @Override
    public void load() throws Exception {
        UpgradeType all[] = UpgradeType.values();
        
        upgradeSprites = new BufferedImage[all.length];
        for(int i = 0; i < all.length; i++) {
            upgradeSprites[i] = loadSprite(SPRITE_PATH, all[i].toString());
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
    
    public void renderLevels(Graphics g, int x, int y, int level) {
        int ox;
        int oy = y + 26;
    
        if (level < 7) {
            g.setColor(LEVEL_COLOR);
        } else {
            g.setColor(LEVEL_COLOR_MAX);
        }
        for (int i = 0; i < level; i++) {
            ox = x + 2 + i * 3;
            g.fillRect(ox, oy, 2, 3);
        }
    }
    
}
