/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerButton;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.utils.MathUtils;
import com.hopkins.rocknrollracing.views.OptionsView;

/**
 *
 * @author ian
 */
public class OptionsController extends AppController {
    
    @Inject
    protected OptionsView theView;

    @Override
    protected void loadController() {
        setView(theView);
        theView.soundState = this.gameState.Sound;
    }
    
    @Override
    public void update(ControllerState input, long ticks) {
        
        if (input.isPressed(ControllerButton.Down)) {
            theView.cursor++;
        } else if (input.isPressed(ControllerButton.Up)) {
            theView.cursor--;
        } else if (input.isAnyButtonPressed()) {
            doToggle(theView.cursor);
        }
        
        theView.cursor = MathUtils.forceInRange(theView.cursor, 0, 3);
    }
    
    protected void doToggle(int cursor) {
        switch(cursor) {
                case 0:
                    this.gameState.Sound.MusicEnabled = !this.gameState.Sound.MusicEnabled;
                    break;
                case 1:
                    this.gameState.Sound.EffectsEnabled = !this.gameState.Sound.EffectsEnabled;
                    break;
                case 2:
                    this.gameState.Sound.LarryEnabled = ! this.gameState.Sound.LarryEnabled;
                    break;
                case 3:
                    dispatchTo("BetweenRace");
                    break;
            }
    }
    
}
