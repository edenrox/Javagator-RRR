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
import com.hopkins.rocknrollracing.state.HasFace;
import com.hopkins.rocknrollracing.utils.StringUtils;
import com.hopkins.rocknrollracing.views.elements.*;
import java.awt.Graphics;
import java.text.DecimalFormat;

/**
 *
 * @author ihopkins
 */
public class BetweenRaceView extends ViewWithBackground {
    
    public static final String[] MENU_TEXT = new String[] {
        "Start Race",
        "View Equipment",
        "Change Planets",
        "Select New Car",
        "Set Options",
    };
    
    public static final String OVERLAY_FORMAT = 
            " Player 1 \n" +
            "          \n" +
            "Money:    \n" +
            "$%s       \n" +
            "          \n" +
            "          \n" +
            "%s        \n" +
            "Division %s\n" +
            "          \n" +
            "Races Run \n" +
            " %2d of %2d\n" +
            "          \n" +
            "          \n" +
            "     Score\n" +
            "Need:%4d \n" +
            "Have:%4d ";
    
    @Inject
    protected PanelElement panelElement;
    
    @Inject
    protected FaceElement faceElement;
    
    @Inject
    protected CrystalElement crystalElement;
    
    @Inject
    protected CarElement carElement;
    
    @Inject
    protected PlanetElement planetElement;
    
    @Inject
    protected FontBasicElement fontElement;
    
    @Inject
    protected BetweenChargesElement betweenChargesElement;
    
    @Inject
    protected BetweenMenuElement betweenMenuElement;
    
    public int cursor;
    public GameState gameState;
    
    

    @Override
    protected void loadView() throws Exception {
        cursor = 0;
    }

    @Override
    protected void renderBackground(Graphics g) {
        String text = "";
    
        // Screen
        panelElement.renderRedPanel(g, 0, 0, Screen.WIDTH, Screen.HEIGHT, 2);
        
        // Center piece
        panelElement.renderGrayPanel(g, 80, 8, 88, 142);
        
        betweenChargesElement.render(g, 184, 154, gameState.Player1);

        // Bottom left text piece
        panelElement.renderGrayPanel(g, 16, 192, 160, 24);
        
        // Left side
        renderSide(g, gameState.Player1.Hero, 4, 11);
        
        // Right side
        renderSide(g, gameState.Rival, 180, 11);
        
        // Planet
        planetElement.renderSmall(g, 94, 42, gameState.Rival.getPlanet());
        
        
        
        // Overlay text
        text = String.format(OVERLAY_FORMAT, 
                StringUtils.formatNumber(gameState.Player1.Money),
                gameState.Rival.getPlanet().getName(),
                gameState.Division.toString(),
                gameState.RaceNumber,
                gameState.Rival.getNumRaces(),
                gameState.Rival.getPointsRequired(),
                gameState.Player1.Points);
        fontElement.renderText(g, 84, 16, text);
    }
    
    

    @Override
    public void renderForeground(Graphics g, long ticks) {
        String text;
        
        // Selected Menu Item Caption Text
        text = MENU_TEXT[cursor];
        int x = 96 - (text.length() * 4);
        fontElement.renderText(g, x, 200, text);
        
        int pos = cursor;
        if (ticks % 30 < 15) {
            pos = -1;
        }
        betweenMenuElement.render(g, 16, 160, pos);
        
        
        CarState cs = new CarState();
        cs.frame = (int)((ticks % 180 * 24) / 180);
        
        // Hero Side
        cs.color = gameState.Player1.Color;
        cs.model = gameState.Player1.Model;
        cs.x = 12;
        cs.y = 90;
        carElement.render(g, cs);
        
        // Enemy Side
        cs.color = CarColor.Purple;
        cs.model = gameState.Rival.getModel();
        cs.x = 188;
        cs.y = 90;
        carElement.render(g, cs);
    }
    
    protected void renderSide(Graphics g, HasFace face, int x, int y) {
        
        // Hero Face
        faceElement.render(g, x, y, face);
        
        // Hero Crystal and Car
        crystalElement.render(g, x + 3, y + 74);
    }
    
    
    
    
}
