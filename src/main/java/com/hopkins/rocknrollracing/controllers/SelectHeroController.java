/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerButton;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.Hero;
import com.hopkins.rocknrollracing.utils.MathUtils;
import com.hopkins.rocknrollracing.views.SelectHeroView;

/**
 *
 * @author ian
 */
public class SelectHeroController extends AppController {
    
    @Inject
    protected SelectHeroView theView;
    
    protected int cursor;

    @Override
    protected void loadController() {
        setView(theView);
        cursor = 0;
    }

    @Override
    public void update(ControllerState input, long ticks) {
        if (input.isPressed(ControllerButton.Left)) {
            cursor--;
        } else if (input.isPressed(ControllerButton.Right)) {
            cursor++;
        } else if (input.isAnyButtonPressed()) {
            gameState.Player1.Hero = getHero();
            dispatchTo("CarPurchase");
        }
        
        // Wrap around
        cursor = MathUtils.wrapInRange(cursor, 0, Hero.All.length - 1);

        theView.Hero = getHero();
    }
    
    protected Hero getHero() {
        return Hero.All[cursor];
    }
    
    
    
    
    
}
