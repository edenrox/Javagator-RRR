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
import com.hopkins.rocknrollracing.state.GameMode;
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
        theView.modelOffset = getModelOffset();
        theView.playerState = gameState.Player1;
    }
    
    protected int getModelOffset() {
        return Math.max(0, gameState.Rival.getModel().ordinal() - 2);
    }
    
    @Override
    public void update(ControllerState input, long ticks) {
        
        if (!theView.isSelected) {
            if (input.isPressed(ControllerButton.Right)) {
                theView.modelIndex++;
            } else if (input.isPressed(ControllerButton.Left)) {
                theView.modelIndex--;
            } else if (input.isPressed(ControllerButton.Down)) {
                theView.menuIndex++;
            } else if (input.isPressed(ControllerButton.Up)) {
                theView.menuIndex--;
            }
        }
        if (input.isAnyButtonPressed()) {
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
        
        if (theView.isComplete) {
            dispatchTo("BetweenRace");
        }
    }
    
    protected void doPurchase() {
        CarModel model = theView.getSelected();
        if (gameState.Player1.canAfford(model)) {
            gameState.Player1.buyCar(model, CarColor.All[theView.colorIndex]);
            theView.isSelected = true;
        } else {
            // display a message about not being able to afford this car
            theView.setCantAfford();
        }
    }
    
}
