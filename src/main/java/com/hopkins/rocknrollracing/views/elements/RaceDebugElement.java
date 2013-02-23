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
        
        sb.append(String.format("Pos: %s\n", playerCar.getPosition().toString()));
        sb.append(String.format("Speed: %.2f\n", playerCar.getVelocity().getMagnitude()));
        sb.append(String.format("Angle: %d WP: %d\n", playerCar.getAngle(), playerCar.LastWayPointIndex));
        
        int x = Screen.WIDTH - 30 * FontBasicElement.WIDTH;
        int y = Screen.HEIGHT - 3 * FontBasicElement.HEIGHT;
        font.renderText(g, x, y, sb.toString());
    }
    
    
}
