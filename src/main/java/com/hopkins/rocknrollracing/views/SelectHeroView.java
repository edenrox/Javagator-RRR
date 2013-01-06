/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.state.HasFace;
import com.hopkins.rocknrollracing.state.Hero;
import com.hopkins.rocknrollracing.state.Planet;

/**
 *
 * @author ian
 */
public class SelectHeroView extends SelectView {
    
    public static final String TEXT_FORMAT =
            "      Player 1\n\n" +
            "%s\n\n" +
            "%s\n\n" +
            "%s";
            
    
    public Hero Hero;

    @Override
    protected String getBottomText() {
        return "<  Select Hero  >";
    }

    @Override
    protected HasFace getFace() {
        return Hero;
    }
    
    
    protected Planet getPlanet() {
        return Hero.getPlanet();
    }

    @Override
    protected String getHoverText() {
        return String.format(TEXT_FORMAT,
                Hero.getName(),
                Hero.getPlanet().getName(),
                Hero.getBonusesString()
                );
    }
    
    
    
}
