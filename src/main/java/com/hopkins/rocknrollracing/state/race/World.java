/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.state.track.Track;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ian
 */
public class World {
    public static final int NUM_CARS = 4;
    public static final float WIDTH = 48f;
    public static final float HEIGHT = 48f;
    
    public List<RaceCar> RaceCars;
    public List<Entity> Entities;
    public Track Track;
    
    public World(Track theTrack) {
        
        // Initialize the race cars
        RaceCars = new ArrayList<RaceCar>();
        Entities = new ArrayList<Entity>();
        Track = theTrack;
    }
    
    
    public boolean isOutOfBounds(Vector3D pos) {
        if ((pos.X < 0)
            || (pos.Y < 0)
            || (pos.X > WIDTH)
            || (pos.Y > HEIGHT)) {
            return true;
        }
        return false;
    }

    public Vector2D toMapPosition(Vector3D position) {
        Vector2D rv = new Vector2D();
        rv.X = (int) Math.floor(position.X / 6) - 1;
        rv.Y = (int) Math.floor(position.Y / 6) - 1;
        return rv;
    }
    
    public Vector3D fromMapPosition(Vector2D position) {
        Vector3D rv = new Vector3D();
        rv.X = (position.X + 1.5f) * 6;
        rv.Y = (position.Y + 1.5f) * 6;
        rv.Z = 0;
        return rv;
    }
    
    
    
}
