/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerButton;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.Division;
import com.hopkins.rocknrollracing.utils.MathUtils;
import com.hopkins.rocknrollracing.views.AdvancePlanetView;

/**
 *
 * @author ian
 */
public class AdvancePlanetController extends AppController {
    
    @Inject
    protected AdvancePlanetView theView;

    @Override
    protected void loadController() {
        setView(theView);
        theView.gameState = gameState;
        theView.cursor = 0;
    }

    @Override
    public void update(ControllerState input, long ticks) {
        if (input.isPressed(ControllerButton.Left)) {
            theView.cursor--;
        } else if (input.isPressed(ControllerButton.Right)) {
            theView.cursor++;
        } else if (input.isAnyButtonPressed()) {
            doAction(theView.cursor);
        }
        theView.cursor = MathUtils.forceInRange(theView.cursor, 0, 1);
    }
    
    protected void doAction(int index) {
        if (index == 0) {
            dispatchTo("BetweenRace");
        } else {
            if (gameState.canAdvancePlanet()) {
                gameState.advancePlanet();
            }
        }
    }

}
