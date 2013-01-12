/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerButton;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.Weapon;
import com.hopkins.rocknrollracing.state.race.CarRaceItem;
import com.hopkins.rocknrollracing.state.race.RaceItem;
import com.hopkins.rocknrollracing.state.race.RaceState;
import com.hopkins.rocknrollracing.utils.MathUtils;
import com.hopkins.rocknrollracing.views.RaceView;

/**
 *
 * @author ian
 */
public class RaceController extends AppController {
    
    @Inject
    protected RaceView theView;
    
    protected RaceState raceState;

    @Override
    protected void loadController() {
        initializeRaceState();
        theView.RaceState = raceState;
        setView(theView);
    }
    
    protected void initializeRaceState() {
        raceState = new RaceState(gameState);
    }

    @Override
    public void update(ControllerState input, long ticks) {
        
        // player update
        if (ticks % 8 == 0) {
            playerUpdate(input, ticks);
        }
        
        // ai update
        
        // projectile & drop update
        raceItemUpdate(ticks);
        
        // collission detections & corrections
        
        
        
    }
    
    protected void raceItemUpdate(long ticks) {
        for(RaceItem item : raceState.getItems()) {
            item.getPosition().add(item.getVelocity());
        }
    }
    
    protected void playerUpdate(ControllerState input, long ticks) {
        CarRaceItem playerCar = raceState.getCars().get(0);
        float accel = 0.7f;
        float max_v = 5.0f;
        float max_drag = 0.4f;
        
        
        // Apply acceleration
        if (input.isDown(ControllerButton.Gas)) {
            playerCar.getVelocity().add(accel, playerCar.getAngle());
        }
        // Apply drag
        float mag_v = Math.max(playerCar.getVelocity().getMagnitude() - max_drag, 0);
        playerCar.getVelocity().setMagnitude(mag_v);
        if (input.isDown(ControllerButton.Fire)) {
            if (playerCar.Charges[0] > 0) {
                raceState.addProjectile(playerCar);
                playerCar.Charges[0]--;
            }
        }
        if (input.isDown(ControllerButton.Drop)) {
            if (playerCar.Charges[2] > 0) {
                raceState.addDrop(playerCar);
                playerCar.Charges[2]--;
            }
        }
        if (input.isDown(ControllerButton.Boost)) {
            if (playerCar.Charges[1] > 0) {
                //playerCar.getVelocity().setMagnitude(ratio);
                playerCar.Charges[1]--;
            }
        }
        if (input.isDown(ControllerButton.Left)) {
            if (playerCar.isMoving()) {
                playerCar.incAngle(1);
            }
        }
        if (input.isDown(ControllerButton.Right)) {
            if (playerCar.isMoving()) {
                playerCar.incAngle(-1);
            }
        }
        
        if (playerCar.getVelocity().getMagnitude() > max_v) {
            playerCar.getVelocity().setMagnitude(max_v);
        }
        
        playerCar.getPosition().add(playerCar.getVelocity());
    }
    
    
    
    
    
}
