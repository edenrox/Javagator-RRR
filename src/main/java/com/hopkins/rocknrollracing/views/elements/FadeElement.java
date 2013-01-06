/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.views.Screen;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author ian
 */
public class FadeElement extends AppElement {
    public static final int FADE_LEVELS = 16;
    
    protected Color fadeColors[];

    @Override
    public void load() throws Exception {
        fadeColors = new Color[FADE_LEVELS];
        
        fadeColors[0] = new Color(0,0,0,0);
        fadeColors[FADE_LEVELS-1] = Color.black;
        for(int i = 1; i < FADE_LEVELS-1; i++) {
            fadeColors[i] = new Color(0, 0, 0, (int) (((double) i / FADE_LEVELS) * 256));
        }
    }
    
    public void renderFade(Graphics g, int intensity) {
        g.setColor(fadeColors[intensity]);
        g.fillRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
    }
    
}
