/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.engine;

import com.hopkins.rocknrollracing.state.CarColor;
import com.hopkins.rocknrollracing.state.Division;
import com.hopkins.rocknrollracing.state.Drop;
import com.hopkins.rocknrollracing.state.Enemy;
import com.hopkins.rocknrollracing.state.GameState;
import com.hopkins.rocknrollracing.state.PowerUp;
import com.hopkins.rocknrollracing.state.Weapon;
import com.hopkins.rocknrollracing.state.race.Angle3D;
import com.hopkins.rocknrollracing.state.race.Direction;
import com.hopkins.rocknrollracing.state.race.Entity;
import com.hopkins.rocknrollracing.state.race.EntityState;
import com.hopkins.rocknrollracing.state.race.RaceCar;
import com.hopkins.rocknrollracing.state.race.Vector2D;
import com.hopkins.rocknrollracing.state.race.Vector3D;
import com.hopkins.rocknrollracing.state.race.World;
import com.hopkins.rocknrollracing.state.track.Track;

/**
 *
 * @author ian
 */
public class Spawn {
    
    public static void raceCarsAtStartLine(World w, GameState gs) {
        
        // Figure out where the start line is, and which way we should be pointing
        Vector2D startLine = w.Track.findStartLine();
        Vector3D origin = w.fromMapPosition(startLine);
        Direction startDirection = Direction.Up;
        
        // Figure out which way to offset the cars
        Vector3D offset = startOffsetFromDirection(startDirection);
        Vector3D initialOffset = offset.clone();
        initialOffset.scale(1.5f);
        
        // calculate the position of the first car
        Vector3D carPos = new Vector3D();
        carPos.copyFrom(origin);
        carPos.subtract(initialOffset);
        
        // place the cars one by one
        for(int i = 0; i < World.NUM_CARS; i++) {

            // setup the car's physics
            RaceCar rc = new RaceCar();
            rc.Angle.setYaw(Angle3D.getYawFromDirection(startDirection));
            rc.Position.copyFrom(carPos);
            rc.Size.reset(0.8f, 0.8f, 0.8f);
            rc.Velocity.reset(0,0,0);
            
            // configure the car's state
            configureCar(rc, gs, i);
            
            // power up the car
            rc.onArmorPowerUp();
            rc.onLapComplete();
            
            // increment the position for the next car
            carPos.add(offset);
            
            w.RaceCars.add(rc);
        }
        
    }
    
    protected static Vector3D startOffsetFromDirection(Direction dir) {
        switch (dir) {
            default:
            case Right:
                return new Vector3D(0, 1, 0);
            case Up:
                return new Vector3D(1, 0, 0);
            case Left:
                return new Vector3D(0, -1, 0);
            case Down:
                return new Vector3D(-1, 0, 0);
        }
    }
    
    
    
    protected static void configureCar(RaceCar rc, GameState gs, int carIndex) {
        switch (carIndex) {
            case 0:
                // The player
                rc.Attributes.calculate(gs.Player1.Upgrades, gs.Player1.Hero);
                rc.Color = gs.Player1.Color;
                rc.Model = gs.Player1.Model;
                break;
            case 1:
                // The rival
                rc.Attributes.calculate(gs.getRivalUpgradeState(), null);
                rc.Color = CarColor.Purple;
                rc.Model = gs.Rival.getModel();
                break;
            case 2:
                // Rip
                rc.Attributes.calculate(gs.getRivalUpgradeState(), null);
                rc.Color = CarColor.Gray;
                rc.Model = gs.Rival.getModel();
                break;
            case 3:
            default: 
                // Shred
                rc.Attributes.calculate(gs.getRivalUpgradeState(), null);
                rc.Color = CarColor.Orange;
                int rivalOrdinal = gs.Rival.ordinal();
                if ((gs.Division == Division.A) || (rivalOrdinal == 0)) {
                    rc.Model = gs.Rival.getModel();
                } else {
                    rc.Model = Enemy.All[rivalOrdinal - 1].getModel();
                }
                break;
        }
    }
    
    
    public static void randomPowerUpsOnTrack(World theWorld, int numPowerUps) {
        int numDone = 0;
        
        while (numDone < numPowerUps) {
            Vector3D pos = randomPosition();
            PowerUp type = (Math.random() > 0.5) ? PowerUp.Money : PowerUp.Armor;
            if (theWorld.Track.isRoadWay(pos)) {
                pos.Z = theWorld.Track.getHeightAt(pos);
                
                // instantiate the power up entity
                Entity e = new Entity();
                e.Position.copyFrom(pos);
                e.CreatedBy = Entity.CREATED_BY_SYSTEM;
                e.Type = type;
                e.State = EntityState.Live;
                e.Size = new Vector3D(0.5f, 0.5f, 0.5f);
                
                // add the power up to the world
                theWorld.Entities.add(e);
                
                // increment the number we have done
                numDone++;
            }
        }
        
    }
    
    protected static Vector3D randomPosition() {
        Vector3D rv = new Vector3D();
        rv.X = (float) (Math.random() * Track.WIDTH);
        rv.Y = (float) (Math.random() * Track.HEIGHT);
        
        return rv;
    }
    
    public static void raceCarAfterDeath(World w, int carIndex) {
        RaceCar car = w.RaceCars.get(carIndex);
        
        // find the last check point
        Vector2D lastCheckpoint = null;
        car.Position.copyFrom(w.fromMapPosition(lastCheckpoint));
        
        // reset the velocity
        car.Velocity.reset(0, 0, 0);
        
        // reset the armor
        car.onArmorPowerUp();
    }
    
    public static void weaponFromRaceCar(World w, int carIndex) {
        RaceCar car = w.RaceCars.get(carIndex);
        Weapon weaponType = car.Model.getWeapon();
        
        Entity e = new Entity();
        e.Position.copyFrom(car.Position);
        e.Type = weaponType;
        e.Velocity.unitFromAngles(car.Angle);
        e.Velocity.scale(weaponType.getSpeed());
        e.Angle.copyFrom(car.Angle);
        e.Size.reset(1, 1, 1);
        e.CreatedBy = carIndex;
        e.State = EntityState.Live;
        
        w.Entities.add(e);
    }
    
    public static void dropFromRaceCar(World w, int carIndex) {
        RaceCar car = w.RaceCars.get(carIndex);
        Drop dropType = car.Model.getDrop();
        
        Entity e = new Entity();
        e.Position.copyFrom(car.Position);
        e.Type = dropType;
        e.Position.Z = w.Track.getHeightAt(e.Position);
        e.Size.reset(0.5f, 0.5f, 0.5f);
        e.CreatedBy = carIndex;
        e.State = EntityState.Live;
        
        w.Entities.add(e);
    }
    
}
