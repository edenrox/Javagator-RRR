/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.state.CarAttribute;
import com.hopkins.rocknrollracing.state.CarColor;
import com.hopkins.rocknrollracing.state.CarModel;
import com.hopkins.rocknrollracing.state.HasFace;
import com.hopkins.rocknrollracing.state.Hero;
import com.hopkins.rocknrollracing.state.UpgradeState;
import com.hopkins.rocknrollracing.state.UpgradeType;

/**
 *
 * @author ian
 */
public class CarRaceItem extends RaceItem {
    public Usable Armor;
    public Usable[] Charges;
    
    
    public int SlopeAngle;    
    
    public int LastWayPointIndex;
    
    protected Vector3D nextWayPoint;
    protected boolean[] actions;
    protected CarColor color;
    protected int lap;
    protected UpgradeState upgradeState;
    protected HasFace character;
    
    public int getLapsRemaining(RaceState rs) {
        return rs.NumLaps - lap - 1;
    }
    
    public boolean isComplete(RaceState rs) {
        return (getLapsRemaining(rs) == 0);
    }
    
    public Vector3D getNextWayPoint() {
        return nextWayPoint;
    }
    
    public HasFace getCharacter() {
        return character;
    }
    
    public boolean getAction(CarAction action) {
        return actions[action.ordinal()];
    }
    
    public void setAction(CarAction action, boolean value) {
        actions[action.ordinal()] = value;
    }
    
    public CarColor getColor() {
        return color;
    }
    
    public CarModel getModel() {
        return (CarModel) this.object;
    }
    public int getLap() {
        return lap;
    }
    
    public CarRaceItem(CarModel model, CarColor color, UpgradeState upgrades, HasFace character) {
        super(RaceItemType.Car, model);
        this.color = color;
        actions = new boolean[CarAction.values().length];
        lap = 0;
        LastWayPointIndex = -1; // We will increment this in the pre-race controller
        
        // Save the upgrade state
        upgradeState = upgrades;
        this.character = character;
        
        // Setup the usable items
        int maxArmor = (upgrades.getLevel(UpgradeType.Armor) + 3) * 2;
        Armor = new Usable(maxArmor);
        Charges = new Usable[] {
            new Usable(upgrades.getLevel(UpgradeType.Weapon)),
            new Usable(upgrades.getLevel(UpgradeType.Boost)),
            new Usable(upgrades.getLevel(UpgradeType.Drop))
        };
    }
    
    public boolean isMoving() {
        return (Math.abs(getVelocity().getMagnitude()) > 0.002f);
    }
    
    public boolean isNitro() {
        return this.Charges[1].isCoolingDown();
    }
    
    public boolean isSmoking() {
        return (Armor.getAvailable() < 2);
    }
    
    public void resetActions() {
        for(int i = 0; i < actions.length; i++) {
            actions[i] = false;
        }
    }
    
    public void updateTimers() {
        for(Usable charge : Charges) {
            charge.tick();
        }
    }
    
    public void onWayPointComplete(RaceState rs) {
        
        // Increment the last way point
        LastWayPointIndex = rs.getNextWaypointIndex(LastWayPointIndex);
        
        // Figure out the next way point
        int nwpi = rs.getNextWaypointIndex(LastWayPointIndex);
        nextWayPoint = rs.getWayPoints().get(nwpi);
        
        // if this is an AI car, then we will randomly offset the way point a bit
        // (so the cars don't all rush to the same location).
        if (!rs.isPlayerCar(this)) {
            int rand = (int) Math.round(Math.random() * 25);
            Vector3D offset = new Vector3D((rand % 5 - 2), (rand / 5 - 2), 0);
            //nextWayPoint.add(offset);
        }
    }
    
    public void onLapComplete() {
        lap++;
        for(Usable charge : Charges) {
            charge.refill();
        }
    }

    public void onArmorPickup() {
        Armor.refill();
    }
    
    public int getSlipAngle() {
        return (getVelocity().getAngle() - getAngle() + 360) % 360;
    }
    
    
    
    protected float getMax(UpgradeType uType, CarAttribute bType) {
        int level = upgradeState.getLevel(uType);
        
        if (character.getClass() == Hero.class) {
            Hero hero = (Hero) character;
            if (hero.hasBonus(bType)) {
                level++;
            }
        }
        level = Math.min(4, level);
        
        return level;
    }
    
    public float getRollingFriction() {
        return 0.002f;
    }
    
    public float getTopSpeed() {
        return 0.05f + (0.002f * getMax(UpgradeType.Engine, CarAttribute.TopSpeed));
    }
    
    public float getMaxAccel() {
        return 0.01f + (0.002f * getMax(UpgradeType.Engine, CarAttribute.Acceleration));
    }
    
    public float getTraction() {
        return 0f;
    }
}
