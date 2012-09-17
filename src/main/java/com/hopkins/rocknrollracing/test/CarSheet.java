/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.test;

import com.hopkins.rocknrollracing.Application;
import com.hopkins.rocknrollracing.model.CarModelColor;
import com.hopkins.rocknrollracing.view.CarView;
import com.hopkins.rocknrollracing.view.TextView;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 *
 * @author ian
 */
public class CarSheet {
    
    
    public static void main(String[] args) throws Exception {
        Application app = new Application();
        app.models.load();
        
        CarView cv = new CarView(app);
        CarModelColor cmc = app.models.carModelColors.get("Yellow");
        
        TextView tv = new TextView(app);
        Set<String> carNames = app.models.carModels.keys();
        
        int width = CarView.SPRITE_WIDTH * CarView.ROTATION_ANGLES;
        int height = (CarView.SPRITE_HEIGHT + TextView.SPRITE_HEIGHT) * carNames.size();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.gray);
        g.fillRect(0, 0, width, height);
        
        int x = 0, y = 0;
        for(String carName : carNames) {
            x = 0;
            tv.drawText(g, x+2, y+1, carName);
            y += TextView.SPRITE_HEIGHT;
            for(int r = 0; r < CarView.ROTATION_ANGLES; r++) {
                cv.draw(g, carName, x, y, r, 0, cmc);
                x += CarView.SPRITE_WIDTH;
            }
            y += CarView.SPRITE_HEIGHT;
        }
        
        ImageIO.write(img, "PNG", new File("cars.png"));
     
        
    }
}
