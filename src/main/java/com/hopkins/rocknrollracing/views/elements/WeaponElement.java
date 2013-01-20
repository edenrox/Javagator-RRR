/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.Drop;
import com.hopkins.rocknrollracing.state.Weapon;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class WeaponElement extends AppElement {
    
    public static final String SPRITE_PATH = "images/weapons/%s.png";
    public static final int WEAPON_WIDTH = 16;
    public static final int WEAPON_HEIGHT = 16;
    
    protected BufferedImage plasma, missile, sundog;
    protected BufferedImage oil, mine, scatterpack;
    protected SmokeElement smoke;

    @Override
    public void load() throws Exception {
        plasma = loadSprite("plasma");
        missile = loadSprite("missile");
        sundog = loadSprite("sundog");
        oil = loadSprite("oil");
        mine = loadSprite("mine");
        scatterpack = loadSprite("scatterpack");
        smoke = new SmokeElement();
        smoke.load();
    }
    
    protected BufferedImage loadSprite(String name) throws Exception {
        return ImageUtils.loadSprite(String.format(SPRITE_PATH, name));
    }
    
    protected int getFrameFromAngle(int angleInDegrees) {
        switch (angleInDegrees / 15 % 12) {
            case 0:
                return 0;
            case 1:
            case 2:
            case 10:
            case 11:
                return 1;
            case 3:
            case 9:
                return 2;
            case 4:
            case 5:
            case 7:
            case 8:
                return 3;
            case 6:
                return 4;
        }
        return 0;
    }
    
    protected boolean isVFlip(int angleInDegrees) {
        return (angleInDegrees < 180);
    }
    protected boolean isHFlip(int angleInDegrees) {
        return (angleInDegrees > 90) && (angleInDegrees < 270);
    }
    
    public void renderProjectile(Graphics g, int x, int y, int angleInDegrees, Weapon weapon) {
        
        int frame = 0;
        boolean hFlip = false;
        boolean vFlip = false;
        if (weapon != Weapon.Sundog) {
            frame = getFrameFromAngle(angleInDegrees);
            hFlip = isHFlip(angleInDegrees);
            vFlip = isVFlip(angleInDegrees);
        }

        SpriteRenderer.render(g, getImage(weapon), x, y, WEAPON_WIDTH, WEAPON_HEIGHT, frame, vFlip, hFlip);
    }
    
    public void renderDrop(Graphics g, int x, int y, Drop drop) {
        g.drawImage(getImage(drop), x, y, null);
    }
    
    protected BufferedImage getImage(Drop drop) {
        if (drop == Drop.Oil) {
            return oil;
        } else if (drop == Drop.Mine) {
            return mine;
        } else {
            return scatterpack;
        }
    }
    
    protected BufferedImage getImage(Weapon weapon) {
        if (weapon == Weapon.Plasma) {
            return plasma;
        } else if (weapon == Weapon.Missile) {
            return missile;
        } else {
            return sundog;
        }
    }
}
