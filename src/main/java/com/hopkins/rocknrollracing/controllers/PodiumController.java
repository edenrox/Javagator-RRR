/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.HasFace;
import com.hopkins.rocknrollracing.state.PodiumPlace;
import com.hopkins.rocknrollracing.utils.ArrayUtils;
import com.hopkins.rocknrollracing.views.PodiumView;

/**
 *
 * @author ian
 */
public class PodiumController extends AppController {
    
    @Inject
    protected PodiumView theView;
    protected boolean isFirst;

    @Override
    protected void loadController() {
        setView(theView);
        isFirst = true;
    }

    @Override
    public void update(ControllerState input, long ticks) {
        if (isFirst) {
            isFirst = false;
            awardPrizes();
            advanceRaces();
            theView.raceResult = gameState.LastRaceResult;
        }

        if (ticks > PodiumView.DURATION) {
            dispatchTo("BetweenRace");
        }
    }
    
    protected void advanceRaces() {
        gameState.RaceNumber++;
    }
    
    protected void awardPrizes() {
        gameState.Player1.awardPlace(getPlace(0));
        if (gameState.NumPlayers > 1) {
            gameState.Player2.awardPlace(getPlace(1));
        }
    }
    
    protected PodiumPlace getPlace(int player) {
        HasFace face = null;
        if (player == 0) {
            face = gameState.Player1.Hero;
        } else {
            face = gameState.Player2.Hero;
        }
        int index = ArrayUtils.indexOfObject(gameState.LastRaceResult.Positions, face);
        return PodiumPlace.All[index];
    }
}
