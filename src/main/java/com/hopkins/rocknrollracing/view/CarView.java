/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.view;

import com.hopkins.rocknrollracing.Application;
import com.hopkins.rocknrollracing.model.CarModelColor;
import com.hopkins.rocknrollracing.utils.Inflector;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author ian
 */
public class CarView {
    
    public static final String SPRITE_PATH = "images/cars/%s.png";
    public static final int SPRITE_WIDTH = 48;
    public static final int SPRITE_HEIGHT = 48;
    
    public static final int ROTATION_ANGLES = 24;
    
    public static final int[] STATIC_COLORS = new int[] {
        0x000000, 0xFFB059, 0x003973, 0x085242,
        0x215A10, 0x600000, 0x00FF00
    };
    public static final int[] STATIC_COLORS_TO = new int[] {
        0x000000, 0x4A494A, 0x737173, 0x9C9A9C,
        0xEEF3EE, 0xAD393A, 0x525152
    };
    public static final int[] WHEEL_COLORS = new int[] {
        0x005A21, 0x4F3500, 0x314E18
    };
    public static final int[] WHEEL_COLORS_TO = new int[] {
        0x424142, 0x292829, 0x101010
    };
    public static final int[] PAINT_COLORS = new int[] {
        0x001084, 0x08008C, 0x42007B, 0x63005A, 0x6B0010
        };
    
    protected Application app;
    
    public CarView(Application theApp) {
        app = theApp;
    }
    
    public void draw(Graphics2D g, String carName, int sx, int sy, int rotation, int frame, CarModelColor color) {
        // load and color the sprite
        BufferedImage sprite = loadSprite(carName);
        BufferedImage coloredSprite = colorSprite(sprite, color);
        
        // Ensure the rotation is in the right range
        rotation = rotation % ROTATION_ANGLES;
        
        boolean hFlip = isHFlipped(rotation);
        int offset = getSpriteOffset(rotation);
        
        SpriteUtils.drawSprite(g, coloredSprite, 0, offset * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT, sx, sy, hFlip);
    }
    
    protected BufferedImage colorSprite(BufferedImage sprite, CarModelColor color) {
        ColorMap map = new ColorMap();
        map.add(PAINT_COLORS, color.getColors());
        map.add(WHEEL_COLORS, WHEEL_COLORS_TO);
        map.add(STATIC_COLORS, STATIC_COLORS_TO);
        
        return SpriteUtils.colorTransform(sprite, map);
    }
    
    protected BufferedImage loadSprite(String carName) {
        String spritePath = String.format(SPRITE_PATH, Inflector.underscore(carName));
        return app.sprites.getSprite(spritePath);
    }
    
    protected boolean isHFlipped(int rotation) {
        return ((rotation > 0) && (rotation < 12));
    }
    protected int getSpriteOffset(int rotation) {
        if (rotation > 12) {
            return rotation - 12;
        } else {
            return 12 - rotation;
        }
    }
    
    
}
