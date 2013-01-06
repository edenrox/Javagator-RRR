/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.state.Enemy;
import com.hopkins.rocknrollracing.state.HasFace;
import com.hopkins.rocknrollracing.state.Planet;

/**
 *
 * @author ian
 */
public class SelectPlanetView extends SelectView {
    public static final String TEXT_FORMAT = 
            "%s\n\n" +
            "%d Degrees\n\n" +
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
                94,
                "Bring your gas mask...");
    }
    
    
    
    
    
    
}
