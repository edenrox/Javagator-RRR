/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.engine;

import com.hopkins.rocknrollracing.state.race.BoundingCube;
import com.hopkins.rocknrollracing.state.race.RaceCar;
import com.hopkins.rocknrollracing.state.race.RaceCarIntent;
import com.hopkins.rocknrollracing.state.race.Vector2D;
import com.hopkins.rocknrollracing.state.race.Vector3D;
import com.hopkins.rocknrollracing.state.race.World;

/**
 *
 * @author ian
 */
public class EnemyIntent {
    
    public static final int LOOK_AHEAD = 5;
    
    public static void calculateIntent(World world, int carIndex, RaceCarIntent rci) {
        rci.reset();
        
        // Enemies always accelerate
        rci.Accelerate = true;

        // Enemies drive towards the next checkpoint
        int dir = getNextDirection(world, carIndex);
        rci.TurnLeft = (dir == -1);
        rci.TurnRight = (dir == 1);
        
        // if a car is infront of us, we should fire at it
        if (isAnotherCarInFront(world, carIndex)) {
            rci.Weapon = true;
        }
        
        // we will use the mines and boost randomly (this is kind of dumb, but i'm ok with that)
        double rand = Math.random();
        if (rand < 0.001) {
            rci.Drop = true;
        } else if (rand < 0.002) {
            rci.Boost = true;
        }
    }
    
    protected static int getNextDirection(World world, int carIndex) {
        
        // find the car state
        RaceCar car = world.RaceCars.get(carIndex);
        
        // figure out the current map position
        Vector2D curPos = world.toMapPosition(car.Position);
        
        // figure out the next way point
        //Vector2D checkPoint = world.RaceTrack.getNextCP(car.Place.LastCheckPointIndex);
        
        // we know the delta to get to the next checkpoint
        //int deltaX = curPos.X - checkPoint.X;
        //int deltaY = curPos.Y - checkPoint.Y;
        
        return 0; /*TODO: calculate the direction */
    }
    
    protected static boolean isAnotherCarInFront(World world, int carIndex) {
        
        // find the car state
        RaceCar car = world.RaceCars.get(carIndex);
        
        // clone the bounding cube of the car
        BoundingCube bc = car.Bounds.clone();
        
        // we want to move the cube forward by the size of the car on each iteration
        Vector3D delta = new Vector3D();
        delta.unitFromAngles(car.Angle);
        delta.scale(car.Size.Y);
        
        // iterate of the look ahead increments and each car that isn't this one
        for(int i = 1; i < LOOK_AHEAD; i++) {
            bc.move(delta);
            for (int j = 0; j < world.RaceCars.size(); j++) {
                if (j != carIndex) {
                    if (bc.doesCollide(world.RaceCars.get(j).Bounds)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
}
