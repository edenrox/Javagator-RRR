/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.Boost;
import com.hopkins.rocknrollracing.state.Drop;
import com.hopkins.rocknrollracing.state.Weapon;
import com.hopkins.rocknrollracing.state.race.BonusType;
import com.hopkins.rocknrollracing.state.race.CarRaceItem;
import com.hopkins.rocknrollracing.state.race.RaceState;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class HudElement extends AppElement {
    public static final String PAUSE_TEXT = "Give Up?";
    public static final String LAPS_FORMAT = "Laps %d";
    
    public static final String SPRITE_PATH = "images/hud/%s.png";
    
    public static final int CHARGES_WIDTH = 16;
    public static final int CHARGES_HEIGHT = 16;
    public static final int ARMOR_WIDTH = 8;
    public static final int ARMOR_HEIGHT = 8;
    public static final int PIECE_WIDTH = 8;
    public static final int PIECE_HEIGHT = 8;
    
    protected BufferedImage charges, armor, colors;
    protected FontBasicElement font;
    protected HudTrackElement track;

    @Override
    public void load() throws Exception {
        charges = loadSprite("charges");
        armor = loadSprite("armor");
        
        font = new FontBasicElement();
        font.load();
        
        track = new HudTrackElement();
        track.load();
    }
    
    protected BufferedImage loadSprite(String name) throws Exception {
        return ImageUtils.loadSprite(String.format(SPRITE_PATH, name));
    }
    
    
    public void renderHud(Graphics g, RaceState rs, long ticks) {
        
        CarRaceItem playerCar = rs.getCars().get(0);
        
        track.renderCarPositions(g, 8, 8, rs);
        track.renderTrack(g, 8, 8, rs.getTrack());
        renderCharges(g, playerCar);
        renderArmor(g, playerCar.Armor.getAvailable());
        renderLaps(g, playerCar.getLapsRemaining(rs));
        if (rs.PlayerPaused > 0) {
            renderPaused(g, ticks);
        } else {
            //renderBonus(g, )
        }
    }
    
    
    
    protected void renderCharges(Graphics g, CarRaceItem playerCar) {
        Weapon weapon = playerCar.getModel().getWeapon();
        Drop drop = playerCar.getModel().getDrop();
        Boost boost = playerCar.getModel().getBoost();
        
        renderCharge(g, 88, 9, weapon.ordinal(), playerCar.Charges[0].getAvailable());
        renderCharge(g, 121, 9, 3 + boost.ordinal(), playerCar.Charges[1].getAvailable());
        renderCharge(g, 152, 9, 5 + drop.ordinal(), playerCar.Charges[2].getAvailable());
    }
    
    protected void renderCharge(Graphics g, int x, int y, int frame, int quantity) {
        SpriteRenderer.render(g, charges, x, y, CHARGES_WIDTH, CHARGES_HEIGHT, frame, false, false);
        
        font.renderText(g, x+16, y+7, "" + quantity);
    }
    
    protected void renderBonus(Graphics g, BonusType bonus) {
        
    }
    
    protected void renderPaused(Graphics g, long ticks) {
        
        font.renderText(g, 100, 100, PAUSE_TEXT);
        
    }
    
    protected void renderLaps(Graphics g, int lapsRemaining) {
        String text = String.format(LAPS_FORMAT, lapsRemaining);
        font.renderText(g, 192, 8, text);
    }
    
    protected void renderArmor(Graphics g, int armorRemaining) {
        int x = 192;
        int y = 16;
        int offset = 0;
        
        if (armorRemaining < 4) {
            offset = 2;
        }
        
        while (armorRemaining > 1) {
            SpriteRenderer.render(g, armor, x, y, ARMOR_WIDTH, ARMOR_HEIGHT, offset, false, false);
            x += 8;
            armorRemaining -=2;
        }
        if (armorRemaining > 0) {
            SpriteRenderer.render(g, armor, x, y, ARMOR_WIDTH, ARMOR_HEIGHT, offset+1, false, false);
        }
    }
    
    
}
