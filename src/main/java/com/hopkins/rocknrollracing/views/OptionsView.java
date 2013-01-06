/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.CarColor;
import com.hopkins.rocknrollracing.state.CarModel;
import com.hopkins.rocknrollracing.state.CarState;
import com.hopkins.rocknrollracing.state.GameState;
import com.hopkins.rocknrollracing.state.SoundState;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import com.hopkins.rocknrollracing.views.elements.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class OptionsView extends ViewWithBackground {
    
    public static final String SPRITE_PATH = "images/options/cursor.png";
    
    public static final String MENU_FORMAT = 
            "Options \n" +
            "        \n" +
            "        \n" +
            "Music %s\n" +
            "        \n" +
            "Sound %s\n" +
            "        \n" +
            "Larry %s\n" +
            "        \n" +
            "Exit";
            
    
    @Inject
    protected PanelElement panelElement;
    
    @Inject
    protected FontBasicElement fontElement;
    
    @Inject
    protected CarElement carElement;
    
    @Inject
    protected CrystalElement crystalElement;
    
    protected BufferedImage cursorImage;
    
    public int cursor;
    public SoundState soundState;

    @Override
    protected void loadView() throws Exception {
        cursorImage = ImageUtils.loadSprite(SPRITE_PATH);
    }

    @Override
    public void renderBackground(Graphics g) {
        
        // Red background
        panelElement.renderRedPanel(g, 0, 0, Screen.WIDTH, Screen.HEIGHT, 2);
        
        // Left Crystals
        crystalElement.render(g, 8, 11);
        crystalElement.render(g, 8, 75);
        
        // Center gray
        panelElement.renderGrayPanel(g, 69, 13, 118, 111);
        
        // Right Crystals
        crystalElement.render(g, 192, 11);
        crystalElement.render(g, 192, 75);
        
        // Bottom gray
        panelElement.renderGrayPanel(g, 21, 141, 214, 63);
        
        // Cars
        CarState cs = new CarState();
        cs.trackZ = 0;
        cs.z = 0;
        
        cs.angle = 21;
        cs.model = CarModel.BattleTrak;
        cs.color = CarColor.Blue;
        cs.x = 12;
        cs.y = 18;
        carElement.render(g, cs);
        
        cs.model = CarModel.Havoc;
        cs.color = CarColor.Red;
        cs.x = 12;
        cs.y = 82;
        carElement.render(g, cs);
        
        cs.angle = 3;
        cs.model = CarModel.Marauder;
        cs.color = CarColor.Green;
        cs.x = 198;
        cs.y = 18;
        carElement.render(g, cs);
        
        cs.model = CarModel.AirBlade;
        cs.color = CarColor.Yellow;
        cs.x = 198;
        cs.y = 82;
        carElement.render(g, cs);
        
    }

    @Override
    protected void renderForeground(Graphics g, long ticks) {
        
        // Cursor
        if (ticks % 30 < 15) {
            g.drawImage(cursorImage, 78, 48 + cursor * 16, null);
        }
        
        // Text
        String text = String.format(MENU_FORMAT,
                booleanToString(soundState.MusicEnabled),
                booleanToString(soundState.EffectsEnabled),
                booleanToString(soundState.LarryEnabled));
        fontElement.renderText(g, 96, 24, text);
    }
    
    protected String booleanToString(boolean value) {
        if (value) {
            return "On";
        } else {
            return "Off";
        }
    }
    
    
    
    
    
}
