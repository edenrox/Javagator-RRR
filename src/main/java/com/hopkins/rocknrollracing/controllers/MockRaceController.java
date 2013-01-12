/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.race.RaceResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ian
 */
public class MockRaceController extends AppController {

    @Override
    public void update(ControllerState input, long ticks) {
        
        // Randomize the order of the players
        List<Integer> characters = Arrays.asList(0,1,2,3);
        Collections.shuffle(characters);
        
        RaceResult rr = new RaceResult();
        for(int i = 0; i < characters.size(); i++) {
            rr.Positions[i] = gameState.getCharacter(characters.get(i));
        }
        gameState.LastRaceResult = rr;
        
        dispatchTo("Podium");
    }

    
}
