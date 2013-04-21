/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.state.Enemy;
import com.hopkins.rocknrollracing.state.HasFace;
import com.hopkins.rocknrollracing.state.Planet;
import com.hopkins.rocknrollracing.views.elements.FontBasicElement;

/**
 *
 * @author ian
 */
public class SelectPlanetView extends SelectView {
    public static final String TEXT_FORMAT = 
            "%s\n\n" +
            "%s\n\n" +
            "%s";
    
    public Enemy Enemy;

    @Override
    protected String getBottomText() {
        return "< Select Planet >";
    }

    @Override
    protected HasFace getFace() {
        return Enemy;
    }
    

    @Override
    protected Planet getPlanet() {
        return Enemy.getPlanet();
    }
    
    @Override
    protected String getHoverText() {
        Planet planetCurrent = getPlanet();
        return String.format(TEXT_FORMAT,
                planetCurrent.getName(),
                getTemperatureString(planetCurrent.getTemperature()),
                FontBasicElement.wordWrap(planetCurrent.getDescription(), 20));
    }
    
    protected String getTemperatureString(int temperature) {
        if (temperature >= 0) {
            return String.format("%d degrees", temperature);
        } else {
            return String.format("%d below", Math.abs(temperature));
        }
    }
    
    
    
    
    
    
}
