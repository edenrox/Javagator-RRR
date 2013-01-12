/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.test;

import com.hopkins.rocknrollracing.state.CarColor;
import com.hopkins.rocknrollracing.state.CarModel;
import com.hopkins.rocknrollracing.state.CarState;
import com.hopkins.rocknrollracing.views.elements.CarElement;
import com.hopkins.rocknrollracing.views.elements.FontBasicElement;
import com.hopkins.rocknrollracing.views.elements.FontIntroElement;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author ian
 */
public class CarSheet {
    
    
    public static void main(String[] args) throws Exception {
        
        
        
        int width = CarElement.WIDTH * CarState.NUM_FRAMES;
        int height = (CarElement.HEIGHT + FontIntroElement.HEIGHT) * CarModel.All.length;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.gray);
        g.fillRect(0, 0, width, height);
        
        
        // Initialize the views
        CarElement carView = new CarElement();
        carView.load();
        FontIntroElement fontView = new FontIntroElement();
        fontView.load();
        
        // We need a CarState
        CarState cs = new CarState();
        
        int x = 0, y = 0;
        for(CarModel model : CarModel.All) {
            x = 0;
            fontView.renderText(g, x+2, y+1, model.getName());
            y += FontIntroElement.HEIGHT;
            for(int r = 0; r < CarState.NUM_FRAMES; r++) {
                cs.x = x;
                cs.y = y;
                cs.frame = r;
                cs.model = model;
                cs.color = CarColor.Blue;
                
                carView.render(g, cs);
                x += CarElement.WIDTH;
            }
            y += CarElement.HEIGHT;
        }
        
        ImageIO.write(img, "PNG", new File("cars.png"));
     
        
    }
}
