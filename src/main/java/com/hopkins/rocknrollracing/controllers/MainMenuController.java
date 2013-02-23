/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerButton;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.Difficulty;
import com.hopkins.rocknrollracing.state.GameMode;
import com.hopkins.rocknrollracing.views.MainMenuView;

/**
 *
 * @author ian
 */
public class MainMenuController extends AppController {
    
    
    
    protected int pos;
    protected int menuIndex;
    
    
    @Inject
    public MainMenuView theView;

    @Override
    protected void loadController() {
        setView(theView);
        
        pos = 0;
        menuIndex = 0;
    }

    @Override
    public void update(ControllerState input, long ticks) {
        
        if (input.isPressed(ControllerButton.Up)) {
            pos--;
        }
        if (input.isPressed(ControllerButton.Down)) {
            pos++;
        }
        if (input.isAnyButtonPressed()) {
            handleMenuChoice(menuIndex, pos);
            menuIndex++;
            pos = 0;
        }
        if (pos < 0) {
            pos = 0;
        }
        if (menuIndex == 1) {
            if (pos > 1) {
                pos = 1;
            }
        } else {
            if (pos > 2) {
                pos = 2;
            }
        }
        
        if (menuIndex > 2) {
            dispatchTo("SelectHero");
        } else {
            theView.setCursorPosition(pos);
            theView.setMenuIndex(menuIndex);
        }
    }
    
    protected void handleMenuChoice(int menuIndex, int choice) {
        switch (menuIndex) {
            case 0:
                if (choice == 2) {
                    this.gameState.Mode = GameMode.Versus;
                } else {
                    this.gameState.Mode = GameMode.Career;
                }
                break;
            case 1:
                this.gameState.NumPlayers = (choice + 1);
                break;
            case 2:
                this.gameState.GameDifficulty = Difficulty.All[choice];
                break;
        }
    }
    
    
    
}
