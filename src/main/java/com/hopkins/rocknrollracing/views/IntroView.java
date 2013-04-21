/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import com.hopkins.rocknrollracing.views.elements.FadeElement;
import com.hopkins.rocknrollracing.views.elements.TitleElement;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class IntroView extends AppView {
    public static final String SPRITE_PATH = "images/intro/%s.png";
    
    public static final int DURATION = 1050;
    
    @Inject
    protected FadeElement fadeElement;
    
    @Inject
    protected TitleElement titleElement;
    
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
        
        int index = getImageIndex(ticks);
        int fade = getFadeLevel(ticks);
        
        g.drawImage(images[index], 0, 0, null);

        fadeElement.renderFade(g, fade);
        
        
        int titleY = getTitleY(ticks);
        titleElement.render(g, 36, titleY);
    }
    
    protected int getTitleY(long ticks) {
        int Y_MIN = -83;
        int Y_MAX = Screen.HEIGHT - 83;
        int Y_FINAL = 16;
        if (ticks <= 850) {
            return Y_MIN;
        } else if (ticks <= 925) {
            double percent = ((ticks - 850) / 75.0);
            return (int) (Y_MIN + (Y_MAX - Y_MIN) * percent);
        } else if (ticks <= 1000) {
            double percent = ((ticks - 925) / 75.0);
            return (int) (Y_MAX - (Y_MAX - Y_FINAL) * percent);
        } else {
            return Y_FINAL;
        }
    }
    
    protected int getImageIndex(long ticks) {
        return Math.min(2, (int) ticks / 300);
    }
    
    protected int getFadeLevel(long ticks) {
        int mod300 = (int) ticks % 300;
        int fade = 0;
        if ((mod300 < 50) && (ticks < 900)) {
            fade = (int) ((FadeElement.FADE_LEVELS - 1) * (double) (50 - mod300) / 50);
        } else if ((mod300 > 250) && (ticks < 601)) {
            fade = (int) ((FadeElement.FADE_LEVELS - 1) * (double) (mod300 - 250) / 50);
        } else if (ticks > 1000) {
            fade = (int) ((FadeElement.FADE_LEVELS - 1) * (double) (ticks - 1000) / 50);
        }
        return fade;
    }
    
    
}
