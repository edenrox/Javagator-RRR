/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.Division;
import com.hopkins.rocknrollracing.state.GameState;
import com.hopkins.rocknrollracing.state.NPC;
import com.hopkins.rocknrollracing.views.elements.*;
import java.awt.Graphics;

/**
 *
 * @author ian
 */
public class AdvancePlanetView extends ViewWithBackground {
    
    public static final String RETURN_TEXT = "Return to Menu";
    
    public static final String CHARACTER_NAME = "Captain Braddock:";
    
    public static final String CANT_ADVANCE = 
            "'You ain't earned\n" +
            "the right yet,\n" +
            "rookie.'";
    
    public static final String ADVANCE_PLANET = 
            "'Sit down, strap\n" +
            "in, and shut up.'";
    
    public static final String ADVANCE_TO_A = 
            "So you think \n" +
            "you're ready to\n" +
            "race division A?";
    
    public static final String POINTS_FORMAT = 
            "Score Needed\n" +
            " To Advance \n\n" +
            "    %d\n\n" +
            "     Score\n" +
            "P1:   %4d\n";
    
    @Inject
    protected PanelElement panel;
    
    @Inject
    protected FontBasicElement font;
    
    @Inject
    protected FaceElement face;
    
    @Inject
    protected AdvancePlanetMenuElement menu;
    
    @Inject
    protected ShipLaunchElement shipLaunch;
    
    public GameState gameState;
    public int cursor;

    @Override
    protected void loadView() throws Exception {
        // noop
    }

    @Override
    protected void renderBackground(Graphics g) {
        gameState.Player1.Points = 3200;
        // Background
        panel.renderRedPanel(g, 0, 0, Screen.WIDTH, Screen.HEIGHT, 2);
        
        // Braddock Text Area
        panel.renderGrayPanel(g, 101, 13, 142, 63);
        
        // Points Text Area
        panel.renderGrayPanel(g, 13, 133, 102, 71);
        
        // Black space for ship
        panel.renderBlackInlayRed(g, 126, 94, 140, 140, 2);
        
        // Points Text
        String text = String.format(POINTS_FORMAT,
                gameState.Rival.getPointsRequired(),
                gameState.Player1.Points
                );
        font.renderText(g, 16, 136, text);
        
        // Braddock's Face
        face.render(g, 24, 16, NPC.Braddock);
        
        // Render the ship
        shipLaunch.renderShip(g, 148, 117, -1);
        
        // Render the cradle
        shipLaunch.renderCradle(g, 141, 149);
    }

    @Override
    protected void renderForeground(Graphics g, long ticks) {
        for(int i = 0; i < 2; i++) { 
            boolean isHighlighted = ((cursor == i) && (ticks % 30 > 15));
            menu.renderItem(g, 24 + i * 40, 96, i, isHighlighted);
        }
        
        String text = RETURN_TEXT;
        if (cursor == 1) {
            if (gameState.Player1.Points < gameState.Rival.getPointsRequired()) {
                text = CANT_ADVANCE;
            } else if (gameState.Division == Division.B) {
                text = ADVANCE_TO_A;
            } else {
                text = ADVANCE_PLANET;
            }
            text = CHARACTER_NAME + "\n\n" + text;
        }
        font.renderText(g, 104, 24, text);
        
    }
    
    
    
    
    
    
    
}
