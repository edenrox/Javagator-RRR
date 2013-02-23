/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.PowerUp;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class PowerUpElement extends AppElement {
    public static final String SPRITE_PATH = "images/powerups/%s.png";
    
    protected BufferedImage money, armor;

    @Override
    public void load() throws Exception {
        money = loadSprite("money");
        armor = loadSprite("armor");
    }
    
    protected BufferedImage loadSprite(String name) throws Exception {
        return ImageUtils.loadSprite(String.format(SPRITE_PATH, name));
    }
    
    public void renderPowerUp(Graphics g, int x, int y, PowerUp type) {
        g.drawImage(getImage(type), x, y, null);
    }
    
    protected BufferedImage getImage(PowerUp type) {
        if (type == PowerUp.Money) {
            return money;
        } else {
            return armor;
        }
    }
}
