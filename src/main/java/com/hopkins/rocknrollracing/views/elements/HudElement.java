/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.Boost;
import com.hopkins.rocknrollracing.state.Drop;
import com.hopkins.rocknrollracing.state.Weapon;
import com.hopkins.rocknrollracing.state.race.RaceCar;
import com.hopkins.rocknrollracing.state.race.World;
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
    protected HudTrackElement minimap;

    @Override
    public void load() throws Exception {
        charges = loadSprite(SPRITE_PATH, "charges");
        armor = loadSprite(SPRITE_PATH, "armor");
        
        font = new FontBasicElement();
        font.load();
        
        minimap = new HudTrackElement();
        minimap.load();
    }
    
    
    public void renderHud(Graphics g, World theWorld, long ticks) {
        
        RaceCar playerCar = theWorld.RaceCars.get(0);
        
        // Mini Map
        minimap.renderCarPositions(g, 8, 8, theWorld);
        minimap.renderTrack(g, 8, 8, theWorld.Track);
        
        // Status across the top
        renderCharges(g, playerCar);
        renderArmor(g, playerCar.Charges.Armor);
        
        // Status across the bottom
        renderLaps(g, playerCar.Place.LapNumber);
        
        
        /*if (rs.PlayerPaused > 0) {
            renderPaused(g, ticks);
        } else {
            //renderBonus(g, )
        }*/
    }
    
    
    
    protected void renderCharges(Graphics g, RaceCar rc) {
        Weapon weapon = rc.Model.getWeapon();
        Drop drop = rc.Model.getDrop();
        Boost boost = rc.Model.getBoost();
        
        renderCharge(g, 88, 9, weapon.ordinal(), rc.Charges.Weapon);
        renderCharge(g, 121, 9, 3 + boost.ordinal(), rc.Charges.Boost);
        renderCharge(g, 152, 9, 5 + drop.ordinal(), rc.Charges.Drop);
    }
    
    protected void renderCharge(Graphics g, int x, int y, int frame, int quantity) {
        SpriteRenderer.render(g, charges, x, y, CHARGES_WIDTH, CHARGES_HEIGHT, frame, false, false);
        
        font.renderText(g, x+16, y+7, "" + quantity);
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
