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
    
    protected abstract String getBottomText();
    protected abstract String getHoverText();
    protected abstract HasFace getFace();
    protected abstract Planet getPlanet();

    @Override
    protected void renderForeground(Graphics g, long ticks) {
        int angle = (int) (((double) ticks / 15) % 24 * 15);
        if (ticks % 60 == 0) {
//            System.out.println(angle);
        }
        // Render the face
        face.render(g, 13, 118, getFace());
        
        
        g.clipRect(14, 6, 226, 98);
        // Render the planet
        planet.renderLarge(g, 80, 7, angle, getPlanet());
        g.setClip(null);
        
        // Render the text
        font.renderText(g, 88, 112, getHoverText());
    }

    @Override
    protected void loadView() throws Exception {
        // noop
    }
    
    
    
    
    
}
