/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.state.CarColor;
import com.hopkins.rocknrollracing.state.Drop;
import com.hopkins.rocknrollracing.state.GameState;
import com.hopkins.rocknrollracing.state.UpgradeType;
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
    protected List<CarRaceItem> cars;
    protected List<RaceItem> items;
    protected Track track;
    protected GameState gameState;
    
    public int PlayerPaused;
    public int NumLaps;
    
    public List<CarRaceItem> getCars() {
        return cars;
    }
    
    public List<RaceItem> getItems() {
        return items;
    }
    
    public Track getTrack() {
        return track;
    }
    
    public RaceState(GameState gs) {
        gameState = gs;
        cars = new ArrayList<CarRaceItem>(4);
        items = new ArrayList<RaceItem>(32);
        NumLaps = 4;
        
        //Player
        cars.add(new CarRaceItem(gameState.Player1.Model, gameState.Player1.Color));
        cars.get(0).Armor = 6 + 2 * gameState.Player1.Upgrades.getLevel(UpgradeType.Armor);
        cars.get(0).getPosition().set(128, 64, 0);
        
        // Rival
        cars.add(new CarRaceItem(gameState.Rival.getModel(), CarColor.Purple));
        cars.get(1).getPosition().set(128, 128, 0);
        
        // Rip
        cars.add(new CarRaceItem(gameState.Rival.getModel(), CarColor.Gray));
        cars.get(2).getPosition().set(64, 64, 0);
        
        // Shred
        cars.add(new CarRaceItem(gameState.Rival.getModel(), CarColor.Orange));
        cars.get(3).getPosition().set(64, 128, 0);
        
        // Load track
        initializeStaticTrack();
        
        // Place Powerups
        
        
        
    }
    
    public void addProjectile(CarRaceItem car) {
        Weapon weapon = car.getModel().getWeapon();
        RaceItem ri = new RaceItem(RaceItemType.Projectile, weapon);
        ri.setAngle(car.getAngle());
        centerOfCar(car, ri);
        
        ri.getVelocity().add(weapon.getSpeed(), ri.getAngle());
        items.add(ri);
    }
    
    protected void centerOfCar(CarRaceItem car, RaceItem ri) {
        ri.getPosition().copy(car.getPosition());
        ri.getPosition().X += CarElement.WIDTH / 2 - WeaponElement.WEAPON_WIDTH / 2;
        ri.getPosition().Y += CarElement.HEIGHT / 2 - WeaponElement.WEAPON_WIDTH / 2;
    }
    
    public void addDrop(CarRaceItem car) {
        Drop drop = car.getModel().getDrop();
        RaceItem ri = new RaceItem(RaceItemType.Drop, drop);
        ri.setAngle(car.getAngle());
        centerOfCar(car, ri);
        
        items.add(ri);
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
}
