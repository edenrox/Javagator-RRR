/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerButton;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.GameMode;
import com.hopkins.rocknrollracing.utils.MathUtils;
import com.hopkins.rocknrollracing.views.BetweenRaceView;

/**
 *
 * @author ian
 */
public class BetweenRaceController extends AppController {

    @Inject
    protected BetweenRaceView theView;
    
    @Override
    protected void loadController() {
        setView(theView);
        theView.gameState = this.gameState;
    }
    
    @Override
    public void update(ControllerState input, long ticks) {
        
        if (input.isPressed(ControllerButton.Right)) {
            theView.cursor++;
        } else if (input.isPressed(ControllerButton.Left)) {
            theView.cursor--;
        } else if (input.isAnyButtonPressed()) {
            doDispatch(theView.cursor);
        }
        
        theView.cursor = MathUtils.forceInRange(theView.cursor, 0, 4);
    }
    
    protected void doDispatch(int cursor) {
        switch (cursor) {
            case 0:
                dispatchTo("MockRace");
                break;
            case 1:
                dispatchTo("Upgrade");
                break;
            case 2:
                if (gameState.Mode == GameMode.Versus) {
                    dispatchTo("SelectPlanet");
                } else {
                    dispatchTo("AdvancePlanet");
                }
                break;
            case 3:
                dispatchTo("CarPurchase");
                break;
            case 4:
                dispatchTo("Options");
                break;
        }
    }
    
    
    
}
