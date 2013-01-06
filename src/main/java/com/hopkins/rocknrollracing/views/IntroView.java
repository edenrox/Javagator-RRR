/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import com.hopkins.rocknrollracing.views.elements.FadeElement;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class IntroView extends AppView {
    public static final String SPRITE_PATH = "images/intro/%s.png";
    
    public static final int DURATION = 900;
    
    @Inject
    protected FadeElement fadeElement;
    
    protected BufferedImage images[];


    @Override
    protected void loadView() throws Exception {
        images = new BufferedImage[] {
            ImageUtils.loadSprite(String.format(
                SPRITE_PATH, "interplay")),
            ImageUtils.loadSprite(String.format(
                SPRITE_PATH, "blizzard")),
            ImageUtils.loadSprite(String.format(
                SPRITE_PATH, "title"))
        };
    }
    
    @Override
    public void render(Graphics g, long ticks) {
        clear(g, Color.black);
        
        int index = Math.min(2, (int) ticks / 300);
        int mod300 = (int) ticks % 300;
        int fade = 0;
        if (mod300 < 50) {
            fade = (int) ((FadeElement.FADE_LEVELS - 1) * (double) (50 - mod300) / 50);
        } else if ((mod300 > 250) && (index < 2)) {
            fade = (int) ((FadeElement.FADE_LEVELS - 1) * (double) (mod300 - 250) / 50);
        }
        
        g.drawImage(images[index], 0, 0, null);
        
        fadeElement.renderFade(g, fade);
    }
    
    
}
