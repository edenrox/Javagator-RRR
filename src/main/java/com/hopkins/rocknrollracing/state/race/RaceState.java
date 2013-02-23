/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.state.Drop;
import com.hopkins.rocknrollracing.state.GameState;
import com.hopkins.rocknrollracing.state.PowerUp;
import com.hopkins.rocknrollracing.state.Weapon;
import com.hopkins.rocknrollracing.state.track.Track;
import com.hopkins.rocknrollracing.state.track.TrackPieceType;
import com.hopkins.rocknrollracing.views.elements.CarElement;
import com.hopkins.rocknrollracing.views.elements.WeaponElement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ian
 */
public class RaceState {
    public static final int LAPS_PER_RACE = 4;
    
    protected List<CarRaceItem> cars;
    protected List<RaceItem> items;
    protected List<Vector3D> waypoints;
    protected Track track;
    protected GameState gameState;
    
    public int PlayerPaused;
    public int NumLaps;
    
    public GameState getGameState() {
        return gameState;
    }
    
    public List<CarRaceItem> getCars() {
        return cars;
    }
    
    public List<RaceItem> getItems() {
        return items;
    }
    
    public List<Vector3D> getWayPoints() {
        return waypoints;
    }
    
    
    public Track getTrack() {
        return track;
    }
    
    public RaceState(GameState gs) {
        gameState = gs;
        track = new Track();
        cars = new ArrayList<CarRaceItem>(4);
        items = new ArrayList<RaceItem>(32);
        waypoints = new ArrayList<Vector3D>(32);
        NumLaps = LAPS_PER_RACE;
    }
    
    public void addProjectile(CarRaceItem car) {
        Weapon weapon = car.getModel().getWeapon();
        RaceItem ri = new RaceItem(RaceItemType.Projectile, weapon);
        ri.setAngle(car.getAngle());
        centerOfCar(car, ri);
        
        ri.getVelocity().add(weapon.getSpeed(), ri.getAngle());
        items.add(ri);
    }
    
    public void addPowerUp(PowerUp type, Vector3D position) {
        RaceItem ri = new RaceItem(RaceItemType.PowerUp, type);
        ri.getPosition().copy(position);
        
        items.add(ri);
    }
    
    protected void centerOfCar(CarRaceItem car, RaceItem ri) {
        ri.getPosition().copy(car.getPosition());
        ri.getPosition().X += 1;
        ri.getPosition().Y += 1f;
    }
    
    public void addDrop(CarRaceItem car) {
        Drop drop = car.getModel().getDrop();
        RaceItem ri = new RaceItem(RaceItemType.Drop, drop);
        ri.setAngle(car.getAngle());
        centerOfCar(car, ri);
        
        items.add(ri);
    }
    
    public void addDrop(Drop type, Vector3D pos) {
        RaceItem ri = new RaceItem(RaceItemType.Drop, type);
        ri.getPosition().copy(pos);
        
        items.add(ri);
    }
    
    public Vector3D getNextWaypoint(int currIndex) {
        int nextIndex = (currIndex + 1) % waypoints.size();
        return waypoints.get(nextIndex);
    }
    
    
    protected void initializeStaticTrack() {
        track = new Track();
        
        track.setNotes("Static base track");
        
        for(int y = 1; y < Track.HEIGHT - 1; y++) {
            track.getPiece(0, y).setType(TrackPieceType.StraightUp);
            track.getPiece(7, y).setType(TrackPieceType.StraightUp);
        }
        for (int x = 1; x < Track.WIDTH - 1; x++) {
            track.getPiece(x, 0).setType(TrackPieceType.StraightRight);
            track.getPiece(x, 7).setType(TrackPieceType.StraightRight);
        }
        track.getPiece(0, 0).setType(TrackPieceType.CornerDownRight);
        track.getPiece(0, 7).setType(TrackPieceType.CornerUpRight);
        track.getPiece(7, 0).setType(TrackPieceType.CornerDownLeft);
        track.getPiece(7, 7).setType(TrackPieceType.CornerUpLeft);
        track.getPiece(4, 7).setType(TrackPieceType.StartRight);
    }
    
    public int getNextWaypointIndex(int currentIndex) {
        return (currentIndex + 1) % waypoints.size();
    }
    
    public boolean isPlayerCar(CarRaceItem cri) {
        return (cars.get(0) == cri);
    }
}
