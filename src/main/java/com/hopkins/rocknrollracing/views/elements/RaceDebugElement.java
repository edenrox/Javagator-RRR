/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.race.CarRaceItem;
import com.hopkins.rocknrollracing.views.Screen;
import java.awt.Graphics;

/**
 *
 * @author ian
 */
public class RaceDebugElement extends AppElement {
    
    
    
    protected FontBasicElement font;

    @Override
    public void load() throws Exception {
        font = new FontBasicElement();
        font.load();
    }
    
    public void render(Graphics g, CarRaceItem playerCar) {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("X: %4d Y: %4d\n", (int) playerCar.getPosition().X, (int) playerCar.getPosition().Y));
        sb.append(String.format("Speed: %.2f\n", playerCar.getVelocity().getMagnitude()));
        sb.append(String.format("Angle: %d\n", playerCar.getAngle()));
        
        int x = Screen.WIDTH - 15 * FontBasicElement.WIDTH;
        int y = Screen.HEIGHT - 3 * FontBasicElement.HEIGHT;
        font.renderText(g, x, y, sb.toString());
    }
    
    
}
