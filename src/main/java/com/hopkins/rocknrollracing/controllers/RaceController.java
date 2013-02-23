/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerButton;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.race.CarAction;
import com.hopkins.rocknrollracing.state.race.CarRaceItem;
import com.hopkins.rocknrollracing.state.race.RaceItem;
import com.hopkins.rocknrollracing.state.race.RaceState;
import com.hopkins.rocknrollracing.state.race.Vector3D;
import com.hopkins.rocknrollracing.views.RaceView;
import java.util.Iterator;

/**
 *
 * @author ian
 */
public class RaceController extends AppController {
    
    public static final int ANGLE_INCREMENT = 15;
    public static final int CHARGES_COOLDOWN = 60;
    
    
    
    @Inject
    protected RaceView theView;
    
    protected RaceState raceState;
    
    protected PreRaceHelper preRace;

    @Override
    protected void loadController() {
        initializeRaceState();
        theView.RaceState = raceState;
        setView(theView);
    }
    
    protected void initializeRaceState() {
        preRace = new PreRaceHelper();
        
        raceState = preRace.setupRaceState(gameState);
    }

    @Override
    public void update(ControllerState input, long ticks) {
        
        // player inputs
        playerInput(input);
        
        // ai inputs
        aiInputAll();
        
        // update the cars
        carUpdateAll(ticks);
        
        // projectile & drop update
        raceItemUpdate(ticks);
        
        // collission detections & corrections
        
        // places and way points
        waypointsCalculation();
    }
    
    protected void waypointsCalculation() {
        Vector3D delta = new Vector3D();
        for(CarRaceItem cri : raceState.getCars()) {
            delta.copy(cri.getPosition());
            
            delta.subtract(raceState.getNextWaypoint(cri.LastWayPointIndex));
            
            float tolerance = 0.3f;
            if (raceState.isPlayerCar(cri)) {
                tolerance = 6.0f;
            }
            // advance to the next waypoint
            if (delta.getMagnitude() < tolerance) {
                
                cri.onWayPointComplete(raceState);
                
                if (cri.LastWayPointIndex == 0) {
                    cri.onLapComplete();
                }
            }
        }
    }
    
    protected void carUpdateAll(long ticks) {
        for(CarRaceItem cri : raceState.getCars()) {
            carUpdate(cri, ticks);
        }
    }
    
    protected void carUpdate(CarRaceItem cri, long ticks) {
        
        // Update cooldown timers
        cri.updateTimers();
        
        // Handle Acceleration
        if (cri.getAction(CarAction.Accelerate)) {
            cri.getVelocity().add(cri.getMaxAccel(), cri.getAngle());
        } else {
            // Apply rolling friction if the gas isn't down
            float rf = Math.min(cri.getRollingFriction(), cri.getVelocity().getMagnitude());
            cri.getVelocity().add(-rf, cri.getVelocity().getAngle());
        }
        
        // Handle Turning
        if (cri.isMoving()) {
            if (ticks % 10 == 0) {
                if (cri.getAction(CarAction.TurnLeft)) {
                    cri.incAngle(ANGLE_INCREMENT);
                } else if (cri.getAction(CarAction.TurnRight)) {
                    cri.incAngle(-ANGLE_INCREMENT);
                }
            }
        }
        
        // Handle traction
        if (Math.abs(cri.getSlipAngle()) > 0) {
            float vmag = cri.getVelocity().getMagnitude();
            cri.getVelocity().set(0,0,0);
            cri.getVelocity().add(vmag, cri.getAngle());
        }
        
        
        // Handle projectiles, boost, and mines
        if (useCharge(cri, CarAction.Weapon)) {
            raceState.addProjectile(cri);
        }
        if (useCharge(cri, CarAction.Boost)) {
            // noop, the rest is handled by the timer
        }
        if (useCharge(cri, CarAction.Drop)) {
            raceState.addDrop(cri);
        }
        
        float max_speed = cri.getTopSpeed();
        if (cri.isNitro()) {
            cri.getVelocity().add(cri.getMaxAccel(), cri.getAngle());
            max_speed *= 2;
        }
        
        // Limit to max_speed
        if (cri.getVelocity().getMagnitude() > max_speed) {
            cri.getVelocity().setMagnitude(max_speed);
        }
        
        // Update the car's position
        cri.getPosition().add(cri.getVelocity());
    }
    
    protected boolean useCharge(CarRaceItem cri, CarAction type) {
        int index = getChargeIndex(type);
        if ((cri.getAction(type)) 
                && (cri.Charges[index].use(CHARGES_COOLDOWN))) {
            return true;
        }
        return false;
    }
    
    protected int getChargeIndex(CarAction type) {
        switch (type) {
            case Weapon:
                return 0;
            case Boost:
                return 1;
            case Drop:
                return 2;
            default:
                return 3;
        }
    }
    
    protected void playerInput(ControllerState input) {
        ControllerButton buttons[] = new ControllerButton[] {ControllerButton.Gas, ControllerButton.Weapon, ControllerButton.Boost, ControllerButton.Drop};
        CarAction actions[] = new CarAction[] {CarAction.Accelerate, CarAction.Weapon, CarAction.Boost, CarAction.Drop};
        CarRaceItem playerCar = raceState.getCars().get(0);
        playerCar.resetActions();
        
        if (playerCar.isComplete(raceState)) {
            return;
        }
        
        if (input.isDown(ControllerButton.Left)) {
            playerCar.setAction(CarAction.TurnLeft, true);
        } else if (input.isDown(ControllerButton.Right)) {
            playerCar.setAction(CarAction.TurnRight, true);
        }
        
        for(int i = 0; i < buttons.length; i++) {
            if (input.isDown(buttons[i])) {
                playerCar.setAction(actions[i], true);
            }
        }
    }
    
    protected void aiInputAll() {
        // Loop through the AI cars and 
        for (int i = 1; i < raceState.getCars().size(); i++) {
            aiInput(raceState.getCars().get(i));
        }
    }
    
    protected void aiInput(CarRaceItem cri) {
        int random = (int) Math.round(Math.random() * 100);
        
        cri.resetActions();
        
        if (cri.isComplete(raceState)) {
            return;
        }
        
        // we always want to accelerate
        cri.setAction(CarAction.Accelerate, true);
        
        // we will steer to the next waypoint
        Vector3D nextWaypoint = cri.getNextWayPoint();
        
        // calculate the delta between here and the next way point
        Vector3D delta = new Vector3D();
        delta.copy(nextWaypoint);
        delta.subtract(cri.getPosition());
        
        // calculate the angle car needs to go to get to the next way point
        int targetAngle = delta.getAngle();
        targetAngle = ((int) Math.round(targetAngle / 15.0)) * 15;
        
        int currentAngle = cri.getVelocity().getAngle();
        
        // figure out which direction we need to turn
        if (currentAngle == targetAngle) {
            // no turn
        } else if (currentAngle <= 180) {
            if ((targetAngle > currentAngle) && (targetAngle < currentAngle + 180)) {
                cri.setAction(CarAction.TurnLeft, true);
            } else {
                cri.setAction(CarAction.TurnRight, true);
            }
        } else {
            if ((targetAngle < currentAngle) && (targetAngle > currentAngle - 180)) {
                cri.setAction(CarAction.TurnRight, true);
            } else {
                cri.setAction(CarAction.TurnLeft, true);
            }
        }

        
        // Use boost if we are a far enough away from a corner and with some random element
        if ((delta.getMagnitude() > 10) && (random < 10)) {
            cri.setAction(CarAction.Boost, true);
        }
        
        // Fire a weapon if anyone is directly in front of us (up to 4 car lengths ahead)
        if (isOtherInRange(cri)) {
            cri.setAction(CarAction.Weapon, true);
        }
        
        // Drop a mine randomly
        if ((random > 10) && (random < 15)) {
            cri.setAction(CarAction.Drop, true);
        }
        
    }
    
    protected boolean isOtherInRange(CarRaceItem cri) {
        return false;
    }
    
    protected void raceItemUpdate(long ticks) {
        // Iterate through the race items
        Iterator<RaceItem> items = raceState.getItems().iterator();
        while (items.hasNext()) {
            RaceItem item = items.next();
            
            // update the position for the item's velocity
            Vector3D pos = item.getPosition();
            pos.add(item.getVelocity());
        }
    }
    
    protected void playerUpdate(ControllerState input, long ticks) {
        CarRaceItem playerCar = raceState.getCars().get(0);
        int angleStep = 15;
        float accel = 0.4f;
        float max_v = 5.0f;
        float nitro_max_v = 8.0f;
        float rolling_resistance = 0.1f;
        float sliding_resistance = 0.2f;
        float v_mag, v_slide, v_roll;
        int slipAngle, slideAngle;
        
        
        slipAngle = playerCar.getSlipAngle();
        
       
        
        // Apply resistance
        slipAngle = playerCar.getSlipAngle();
        v_mag = playerCar.getVelocity().getMagnitude();
        if (slipAngle == 0) {
            v_slide = 0;
            v_roll = v_mag;
            slideAngle = 0;
        } else if (Math.abs(slipAngle) > 90) {
            v_slide = v_mag;
            v_roll = 0;
            slideAngle = (playerCar.getVelocity().getAngle());
        } else {
            v_slide = (float) Math.abs(v_mag * Math.sin(slipAngle * Math.PI / 180.0));
            v_roll = (float) Math.abs(v_mag * Math.cos(slipAngle * Math.PI / 180.0));
            if (slipAngle > 0) {
                slideAngle = (playerCar.getAngle() + 90) % 360;
            } else {
                slideAngle = (playerCar.getAngle() + 270) % 360;
            }
        }
        v_roll = Math.min(rolling_resistance, v_roll);
        v_slide = Math.min(sliding_resistance, v_slide);
        
        playerCar.getVelocity().add(-v_roll, playerCar.getAngle());
        playerCar.getVelocity().add(-v_slide, slideAngle);
        
        
    }
    
    
    
    
}
