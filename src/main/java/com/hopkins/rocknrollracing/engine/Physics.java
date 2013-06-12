/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.engine;

import com.hopkins.rocknrollracing.state.Weapon;
import com.hopkins.rocknrollracing.state.race.BaseEntity;
import com.hopkins.rocknrollracing.state.race.Entity;
import com.hopkins.rocknrollracing.state.race.EntityState;
import com.hopkins.rocknrollracing.state.race.RaceCar;
import com.hopkins.rocknrollracing.state.race.Vector3D;
import com.hopkins.rocknrollracing.state.race.World;
import java.util.ArrayList;

/**
 *
 * @author ian
 */
public class Physics {
    
    /**
     * Update the position of entities in the world
     * based on their velocities
     * @param w the world to update
     */
    public void moveEntities(World world) {
        
        // Move the race cars
        for(BaseEntity e : world.RaceCars) {
            e.incrementPosition();
        }
        // Move the entities
        for (BaseEntity e : world.Entities) {
            e.incrementPosition();
        }
        
    }
    
    /**
     * Filter out any entities that are beyond the bounds of the track
     * @param world the world 
     */
    public void filterEntities(World world) {
        // find the entities we wish to remove
        ArrayList<Entity> toRemove = new ArrayList<Entity>(16);
        for(Entity e : world.Entities) {
            if (world.isOutOfBounds(e.Position)) {
                toRemove.add(e);
            }
            if (e.State == EntityState.Dead) {
                toRemove.add(e);
            }
        }
        // remove them
        world.Entities.removeAll(toRemove);
    }
    
    public void detectCollisions(World world) {
        
        for (int i = 0; i < world.RaceCars.size(); i++) {
            RaceCar rci = world.RaceCars.get(i);
            // car vs. wall collisions first
            detectWallCollission(world, rci);
            
            // car vs. floor next
            if (rci.Position.Z <= 0) {
                onBottomCollide(rci);
            }
        }
        
        // car vs. car collisions next
        for (int i = 0; i < world.RaceCars.size(); i++) {
            for (int j = i+1; j < world.RaceCars.size(); j++) {
                RaceCar rci = world.RaceCars.get(i);
                RaceCar rcj = world.RaceCars.get(j);
                
                if (rci.Bounds.doesCollide(rcj.Bounds)) {
                    onCarCollide(rci, rcj);
                }
            }
        }
        
        // car vs. entity collisions next
        for (int i = 0; i < world.RaceCars.size(); i++) {
            for(Entity e : world.Entities) {
                RaceCar rci = world.RaceCars.get(i);
                
                // You can only collide with Live Entities
                if (e.State == EntityState.Live) {
                    
                    // For plasma, you can't hit yourself
                    if (e.Type == Weapon.Plasma) {
                        if (e.CreatedBy == i) {
                            continue;
                        }
                    }
                    
                    // check if the two objects collide
                    e.Bounds.calculate(e);
                    if (rci.Bounds.doesCollide(e.Bounds)) {
                        onEntityCollide(rci, e);
                    }
                }
            }
        }
        
    }
    
    protected void detectWallCollission(World world, RaceCar car) {
        
    }
    
    
    
    
    // when two cars collide, we swap their velocities
    protected void onCarCollide(RaceCar car1, RaceCar car2) {
        Vector3D swap = car1.Velocity;
        car1.Velocity = car2.Velocity;
        car2.Velocity = swap;
    }
    
    // when a car collides with a projectile
    protected void onEntityCollide(RaceCar car, BaseEntity entity) {
        
    }
    
    // when a car collides with a wall
    protected void onWallCollide(RaceCar car, BaseEntity wallSegment) {
        
    }
    
    // when a car hits the bottom of the map (flew off the track)
    protected void onBottomCollide(RaceCar car) {
        
    }
    
}
