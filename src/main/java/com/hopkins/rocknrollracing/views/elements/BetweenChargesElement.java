/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.PlayerState;
import com.hopkins.rocknrollracing.state.UpgradeType;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ihopkins
 */
public class BetweenChargesElement extends AppElement {
    
    public static final String SPRITE_PATH = "images/between/%s.png";
    
    public static final int ICON_WIDTH = 16;
    public static final int ICON_HEIGHT = 16;
    public static final int BAR_WIDTH = 4;
    public static final int BAR_HEIGHT = 8;
    
    protected BufferedImage barImage;
    protected BufferedImage weaponImage;
    protected BufferedImage boostImage;
    protected BufferedImage dropImage;
    protected PanelElement panelElement;

    @Override
    public void load() throws Exception {
        barImage = loadSprite(SPRITE_PATH, "bar");
        weaponImage = loadSprite(SPRITE_PATH, "weapon");
        boostImage = loadSprite(SPRITE_PATH, "boost");
        dropImage = loadSprite(SPRITE_PATH, "drop");
        
        panelElement = new PanelElement();
        panelElement.load();
    }
    
    public void render(Graphics g, int x, int y, PlayerState ps) {
        // Grey background
        panelElement.renderGrayPanel(g, x, y, 64, 64);
        
        // Icons
        renderIcon(g, x+6, y+3, weaponImage, ps.Model.getWeapon().ordinal());
        
        renderIcon(g, x+6, y+23, dropImage, ps.Model.getDrop().ordinal());
        
        renderIcon(g, x+6, y+43, boostImage, ps.Model.getBoost().ordinal());
        
        renderCharges(g, x+28, y+6, ps.Upgrades.getCharges(UpgradeType.Weapon));
        
        renderCharges(g, x+28, y+26, ps.Upgrades.getCharges(UpgradeType.Drop));
        
        renderCharges(g, x+28, y+46, ps.Upgrades.getCharges(UpgradeType.Boost));
        
    }
    
    protected void renderIcon(Graphics g, int x, int y, BufferedImage image, int offset) {
        
        panelElement.renderBlackInlayGrey(g, x, y, 18, 18);
        
        SpriteRenderer.render(g, image, x, y, ICON_WIDTH, ICON_HEIGHT, offset, false, false);
    }
    
    protected void renderCharges(Graphics g, int x, int y, int charges) {
        
        panelElement.renderBlackInlayGrey(g, x, y, 32, 11);
        
        for(int i = 0; i < charges; i++) {
            int bx = x + 3 + i * BAR_WIDTH;
            int by = y + 2;
            g.drawImage(barImage, bx, by, null);
        }
    }
    
}
