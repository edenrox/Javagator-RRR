/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerButton;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.Enemy;
import com.hopkins.rocknrollracing.utils.MathUtils;
import com.hopkins.rocknrollracing.views.SelectPlanetView;

/**
 *
 * @author ian
 */
public class SelectPlanetController extends AppController {

    @Inject
    protected SelectPlanetView theView;
    
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
            gameState.Rival = getEnemy();
            dispatchTo("BetweenRace");
        }
        
        // Wrap around
        cursor = MathUtils.wrapInRange(cursor, 0, Enemy.All.length - 1);

        theView.Enemy = getEnemy();
    }
    
    protected Enemy getEnemy() {
        return Enemy.All[cursor];
    }
    
    
}
