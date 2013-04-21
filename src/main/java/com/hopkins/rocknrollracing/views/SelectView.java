/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.HasFace;
import com.hopkins.rocknrollracing.state.Planet;
import com.hopkins.rocknrollracing.views.elements.*;
import java.awt.Graphics;

/**
 *
 * @author ian
 */
public abstract class SelectView extends ViewWithBackground {
    
    public static final int PLANET_X_MIN = -62;
    public static final int PLANET_X_MAX = 248;
    public static final int PLANET_X_CENTER = 78;
    public static final int PLANET_VELOCITY = 2;
    
    @Inject
    protected PanelElement panel;
    
    @Inject
    protected FaceElement face;
    
    @Inject
    protected FontSelectElement fontSelect;
    
    @Inject
    protected FontBasicElement font;
    
    @Inject
    protected PlanetElement planet;
    
    protected int planetX = PLANET_X_CENTER;
    protected int planetVelocity = 0;
    protected Planet lastPlanet;
    
    @Override
    protected void renderBackground(Graphics g) {
        
        // background
        panel.renderRedPanel(g, 0, 0, Screen.WIDTH, Screen.HEIGHT, 2);
        
        // planet inset
        panel.renderBlackInlayRed(g, 13, 5, 228, 100, 1);
        
        // Stars
        for(int i = 0; i < 8; i++) {
            int sx = (int) (14 + Math.random() * 224);
            int sy = (int) (6 + Math.random() * 96);
            planet.renderStar(g, sx, sy, i % 4);
        }
        
        // Gray text area
        panel.renderGrayPanel(g, 85, 109, 166, 79);
        
        // Bottom text
        fontSelect.renderText(g, 9, 196, getBottomText());
    }
    
    public int Direction;
    
    protected abstract String getBottomText();
    protected abstract String getHoverText();
    protected abstract HasFace getFace();
    protected abstract Planet getPlanet();

    @Override
    protected void renderForeground(Graphics g, long ticks) {
        
        // Calculate the angle of the planet
        int angle = 360 - (int) (((double) ticks / 60) % 24 * 15);
        
        // Render the face
        face.render(g, 13, 118, getFace());
        
        // Calculate the planet position
        Planet currentPlanet = getPlanet();
        if (lastPlanet == null) {
            lastPlanet = currentPlanet;
        }
        if ((currentPlanet != lastPlanet) && (planetVelocity == 0)) {
            if (Direction > 0) {
                planetVelocity = -PLANET_VELOCITY;
            } else {
                planetVelocity = PLANET_VELOCITY;
            }
        }
        planetX += planetVelocity;
        if (planetX < PLANET_X_MIN) {
            lastPlanet = currentPlanet;
            planetX = PLANET_X_MAX;
        }
        if (planetX > PLANET_X_MAX) {
            lastPlanet = currentPlanet;
            planetX = PLANET_X_MIN;
        }
        if ((planetX == PLANET_X_CENTER) && (lastPlanet == currentPlanet)) {
            planetVelocity = 0;
        }
        
        
        
        // Render the planet (clipped to the viewport)
        g.clipRect(14, 6, 226, 98);
        planet.renderLarge(g, planetX, 7, angle, lastPlanet);
        g.setClip(null);
        
        // Render the text
        font.renderText(g, 88, 112, getHoverText());
    }

    @Override
    protected void loadView() throws Exception {
        // noop
    }
    
    
    
    
    
}
