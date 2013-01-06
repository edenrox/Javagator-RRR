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
                bigImage = ImageUtils.loadSprite(String.format(SPRITE_PATH,
                        path));
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
