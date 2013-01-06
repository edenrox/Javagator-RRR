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
        barImage = loadSprite("bar");
        weaponImage = loadSprite("weapon");
        boostImage = loadSprite("boost");
        dropImage = loadSprite("drop");
        
        panelElement = new PanelElement();
        panelElement.load();
    }
    
    protected BufferedImage loadSprite(String name) throws Exception {
        return ImageUtils.loadSprite(String.format(SPRITE_PATH, name));
    }
    
    public void render(Graphics g, int x, int y, PlayerState ps) {
        // Grey background
        panelElement.renderGrayPanel(g, x, y, 64, 64);
        
        // Icons
        renderIcon(g, x+6, y+3, weaponImage, ps.Model.getWeapon().ordinal());
        
        renderIcon(g, x+6, y+23, dropImage, ps.Model.getDrop().ordinal());
        
        renderIcon(g, x+6, y+43, boostImage, ps.Model.getBoost().ordinal());
        
        renderCharges(g, x+28, y+6, ps.Upgrades.getLevel(UpgradeType.Weapon) + 1);
        
        renderCharges(g, x+28, y+26, ps.Upgrades.getLevel(UpgradeType.Drop) + 1);
        
        renderCharges(g, x+28, y+46, ps.Upgrades.getLevel(UpgradeType.Boost) + 1);
        
    }
    
    protected void renderIcon(Graphics g, int x, int y, BufferedImage image, int offset) {
        int dx1, dx2, dy1, dy2, sx1, sx2, sy1, sy2;
        
        panelElement.renderBlackInlayGrey(g, x, y, 18, 18);
        
        dx1 = x + 2;
        dx2 = dx1 + ICON_WIDTH;
        dy1 = y + 2;
        dy2 = dy1 + ICON_HEIGHT;
        
        sx1 = ICON_WIDTH * offset;
        sx2 = sx1 + ICON_WIDTH;
        sy1 = 0;
        sy2 = sy1 + ICON_HEIGHT;

        g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
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
