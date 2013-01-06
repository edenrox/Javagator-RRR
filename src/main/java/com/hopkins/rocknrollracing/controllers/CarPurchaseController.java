/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.CarColor;
import com.hopkins.rocknrollracing.state.CarModel;
import com.hopkins.rocknrollracing.state.ControllerButton;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.utils.MathUtils;
import com.hopkins.rocknrollracing.views.BuyView;

/**
 *
 * @author ian
 */
public class CarPurchaseController extends AppController {
    
    @Inject
    protected BuyView theView;

    @Override
    protected void loadController() {
        setView(theView);
        theView.playerState = gameState.Player1;
    }
    
    @Override
    public void update(ControllerState input, long ticks) {
        
        if (input.isPressed(ControllerButton.Right)) {
            theView.modelIndex++;
        } else if (input.isPressed(ControllerButton.Left)) {
            theView.modelIndex--;
        } else if (input.isPressed(ControllerButton.Down)) {
            theView.menuIndex++;
        } else if (input.isPressed(ControllerButton.Up)) {
            theView.menuIndex--;
        } else if (input.isAnyButtonPressed()) {
            switch(theView.menuIndex) {
                case 0:
                    dispatchTo("BetweenRace");
                    break;
                case 1:
                    doPurchase();
                    break;
                default:
                    theView.colorIndex = theView.menuIndex - 2;
                    break;
            }
        }
        
        theView.modelIndex = MathUtils.forceInRange(theView.modelIndex, 0, 2);
        theView.menuIndex = MathUtils.forceInRange(theView.menuIndex, 0, 6);
    }
    
    protected void doPurchase() {
        CarModel model = theView.getSelected();
        if (gameState.Player1.canAfford(model)) {
            gameState.Player1.buyCar(model, CarColor.All[theView.colorIndex]);
            dispatchTo("BetweenRace");
        } else {
            // display a message about not being able to afford this car
        }
    }
    
}
