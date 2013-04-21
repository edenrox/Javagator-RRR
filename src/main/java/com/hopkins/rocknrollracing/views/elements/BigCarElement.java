/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.CarColor;
import com.hopkins.rocknrollracing.state.CarModel;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import com.hopkins.rocknrollracing.utils.StringUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.apache.log4j.Logger;

/**
 *
 * @author ihopkins
 */
public class BigCarElement extends AppElement {
    
    public static final Logger log = Logger.getLogger(BigCarElement.class);
    
    public static final int[] BlueColors = new int[] {
        0x000028, 0x000030, 0x000048, 0x000068, 
        0x000080, 0x000098, 0x0000E0, 
        0x2828F8, 0x3838F8, 0x5050F8, 0x8080F8,
    };
    public static final int[] GreenColors = new int[] {
        0x001800, 0x002800, 0x003800, 0x004000, 
        0x005800, 0x007000, 0x009000, //0x00A000, 
        0x10B810, 0x18B818, 0x30E830, 0x70F070
    };
    public static final int[] RedColors = new int[] {
        0x180000, 0x280000, 0x300000, 0x480000,
        0x680000, 0x700000, 0x900000, //0xA80000, 
        0xF81818, 0xF83030, 0xF85858, 0xF87878, 
    };
    
    public static final int[] YellowColors = new int[] {
        0x381000, 0x702800, 0x903800, 0xA85000, 
        0xD06000, 0xC88800, 0xC88800,
        0xF89800, 0xE8A068, 0xE8C878, 0xE0F090,
    };
    public static final int[] BlackColors = new int[] {
        0x000000, 0x080808, 0x101010, 0x101010,
        0x181818, 0x181818, 0x282828,
        0x303030, 0x505050, 0x606060, 0x303030,
    };
    
    public static final int[][] AllColors = new int[][] {
        BlackColors, BlueColors, RedColors, GreenColors, YellowColors
    };

    
    public static final String SPRITE_PATH = "images/cars/%s_bg.png";
    
    protected BufferedImage bigImage;
    
    

    @Override
    public void load() throws Exception {
        bigImage = null;
    }

    protected BufferedImage getBigImage(CarModel model, CarColor color) {
        if (bigImage == null) {
            
            String path = StringUtils.nameToPath(model.getName());
            try {
                // Load the source image
                BufferedImage src = loadSprite(SPRITE_PATH, path);
                
                // build the color lookup table
                int[] originColors = ColorSwapper.makeColorsOpaque(BlueColors);
                int[] destinationColors = ColorSwapper.makeColorsOpaque(AllColors[color.ordinal()]);
                
                // palette swap the source image
                bigImage = ColorSwapper.paletteSwap(src, ColorSwapper.buildColorLookup(originColors, destinationColors));
            } catch (ImageUtils.ImageLoadException ex) {
                log.error("Error loading big image: " + model.getName(), ex);
                bigImage = AppElement.EMPTY;
            }
        }
        return bigImage;
    }
    
    
    
    public void render(Graphics g, int x, int y, CarModel model, CarColor color) {
        
        
        g.drawImage(getBigImage(model, color), x, y, null);
    }
  
    
}
