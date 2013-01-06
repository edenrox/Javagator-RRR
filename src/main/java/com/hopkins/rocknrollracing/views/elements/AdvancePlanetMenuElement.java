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
public class AdvancePlanetMenuElement extends AppElement {
    
    public static final String EXIT_SPRITE_PATH = "images/advance/exit.png";
    public static final String ADVANCE_SPRITE_PATH = "images/advance/advance.png";
    
    public static final int ITEM_WIDTH = 32;
    public static final int ITEM_HEIGHT = 24;

    protected BufferedImage exit;
    protected BufferedImage advance;
    protected BufferedImage[] lookup;
    
    @Override
    public void load() throws Exception {
        exit = ImageUtils.loadSprite(EXIT_SPRITE_PATH);
        advance = ImageUtils.loadSprite(ADVANCE_SPRITE_PATH);
        lookup = new BufferedImage[] {exit, advance};
    }
    
    public void renderItem(Graphics g, int x, int y, int item, boolean isHighlighted) {
        
        // Shadow
        g.setColor(PanelElement.BOX_SHADOW);
        g.fillRect(x + 8, y + 8, ITEM_WIDTH, ITEM_HEIGHT);
        
        // Highlight
        if (isHighlighted) {
            g.setColor(MenuColors.Yellow);
        } else {
            g.setColor(MenuColors.Gray);
        }
        g.fillRect(x, y, ITEM_WIDTH, ITEM_HEIGHT);

        
        // Button
        g.drawImage(lookup[item], x, y, null);
    }
    
    
}
