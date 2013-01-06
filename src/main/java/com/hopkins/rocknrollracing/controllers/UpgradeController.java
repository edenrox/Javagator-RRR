/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerButton;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.Upgrade;
import com.hopkins.rocknrollracing.state.UpgradePosition;
import com.hopkins.rocknrollracing.state.UpgradeType;
import com.hopkins.rocknrollracing.utils.MathUtils;
import com.hopkins.rocknrollracing.views.UpgradeView;

/**
 *
 * @author ian
 */
public class UpgradeController extends AppController {
    
    @Inject
    protected UpgradeView theView;

    @Override
    protected void loadController() {
        setView(theView);
        theView.playerState = gameState.Player1;
    }

    @Override
    public void update(ControllerState input, long ticks) {
        ControllerButton nextButton = getButtonToMove(theView.cursor, theView.cursor + 1);
        ControllerButton prevButton = getButtonToMove(theView.cursor, theView.cursor - 1);
        if (input.isAnyButtonPressed()) {
            if (theView.cursor == 0) {
                dispatchTo("BetweenRace");
            } else {
                doPurchase(theView.cursor);
            }
        } else if (input.isPressed(nextButton)) {
            theView.cursor++;
        } else if (input.isPressed(prevButton)) {
            theView.cursor--;
        }
        
        theView.cursor = MathUtils.wrapInRange(theView.cursor, 0, getMaxUpgrades());
    }
    
    protected void doPurchase(int cursor) {
        UpgradeType type = getUpgrades()[cursor - 1].getType();
        
        if (gameState.Player1.canUpgrade(type)) {
            Upgrade next = gameState.Player1.getNextUpgrade(type);
            if (next.getPrice() <= gameState.Player1.Money) {
                gameState.Player1.buyUpgrade(type);
            }
        }
    }
    
    protected ControllerButton getButtonToMove(int indexA, int indexB) {
        UpgradePosition posA = getUpgradePosition(indexA);
        UpgradePosition posB = getUpgradePosition(indexB);
        
        int dx = posA.getX() - posB.getX();
        int dy = posA.getY() - posB.getY();
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                return ControllerButton.Left;
            } else {
                return ControllerButton.Right;
            }
        } else {
            if (dy > 0) {
                return ControllerButton.Up;
            } else {
                return ControllerButton.Down;
            }
        }
    }
    
    protected UpgradePosition getUpgradePosition(int i) {
        i = MathUtils.wrapInRange(i, 0, getMaxUpgrades());
        if (i == 0) {
            return UpgradeView.EXIT_POSITION;
        } else {
            return getUpgrades()[i - 1];
        }
    }
    
    protected int getMaxUpgrades() {
        return getUpgrades().length;
    }
    
    protected UpgradePosition[] getUpgrades() {
        int modelIndex = gameState.Player1.Model.ordinal();
        return UpgradePosition.All[modelIndex];
    }
    
}
