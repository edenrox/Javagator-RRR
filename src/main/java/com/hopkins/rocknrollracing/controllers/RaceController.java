/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerButton;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.race.CarRaceItem;
import com.hopkins.rocknrollracing.state.race.RaceItem;
import com.hopkins.rocknrollracing.state.race.RaceState;
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
        if (ticks % 4 == 0) {
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
        int angleStep = 15;
        float accel = 0.7f;
        float max_v = 5.0f;
        float nitro_max_v = 6.0f;
        float drag_coefficient = 0.4f;
        float tire_coefficient = 0.8f;
        float v_mag;
        
        
        // Steer the car
        if (playerCar.isMoving()) {
            if (input.isDown(ControllerButton.Left)) {
                playerCar.incAngle(angleStep);
            }
            if (input.isDown(ControllerButton.Right)) {
                playerCar.incAngle(-angleStep);
            }
        }
        
        // Apply acceleration
        if (input.isDown(ControllerButton.Gas)) {
            playerCar.getVelocity().add(accel, playerCar.getAngle());
        }
        
        // Apply air resistance
        v_mag = playerCar.getVelocity().getMagnitude();
        if (v_mag < 0.2f) {
            playerCar.getVelocity().setMagnitude(0);
        } else {
            playerCar.getVelocity().setMagnitude(v_mag - drag_coefficient);
        }
        
        // Apply tire traction
        /*v_mag = playerCar.getVelocity().getMagnitude();
        int v_angle = playerCar.getVelocity().getAngle();
        int slipAngle = playerCar.getAngle() - v_angle;
        //if (slipAngle > )
        if (Math.abs(slipAngle) < 5) {
            playerCar.getVelocity().X = 0;
            playerCar.getVelocity().Y = 0;
            playerCar.getVelocity().add(v_mag, playerCar.getAngle());
        } else {
            playerCar.getVelocity().X = 0;
            playerCar.getVelocity().Y = 0;
            playerCar.getVelocity().add(v_mag * tire_coefficient, (int) Math.round(playerCar.getAngle() + slipAngle * tire_coefficient));
        }*/
        
        
        // Fire projectiles and drop items
        fireProjectiles(playerCar, input);
        
        if (input.isDown(ControllerButton.Boost)) {
            if (playerCar.Charges[1] > 0) {
                playerCar.getVelocity().setMagnitude(nitro_max_v);
                playerCar.Charges[1]--;
            }
        }
        
        
        
        if (playerCar.getVelocity().getMagnitude() > max_v) {
            playerCar.getVelocity().setMagnitude(max_v);
        }
        
        playerCar.getPosition().add(playerCar.getVelocity());
    }
    
    protected void fireProjectiles(CarRaceItem playerCar, ControllerState input) {
        if (input.isDown(ControllerButton.Fire)) {
            if (playerCar.Charges[0] > 0) {
                raceState.addProjectile(playerCar);
                //playerCar.Charges[0]--;
            }
        }
        if (input.isDown(ControllerButton.Drop)) {
            if (playerCar.Charges[2] > 0) {
                raceState.addDrop(playerCar);
                playerCar.Charges[2]--;
            }
        }
    }
    
    
}
