/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.state.CarColor;
import com.hopkins.rocknrollracing.state.CarModel;

/**
 *
 * @author ian
 */
public class RaceCar extends BaseEntity {
    
    public CarColor Color;
    public CarModel Model;
    
    public RaceCarAttributes Attributes;
    public RaceCarIntent Intent;
    public RaceCarCharges Charges;
    public RaceCarTimers Timers;
    public RaceCarPlace Place;
    
    public RaceCar() {
        Attributes = new RaceCarAttributes();
        Intent = new RaceCarIntent();
        Charges = new RaceCarCharges();
        Timers = new RaceCarTimers();
        Place = new RaceCarPlace();
    }
    
    public boolean isSmoking(long currentTime) {
        return (Charges.Armor <= 4) || (isSkidding(currentTime));
    }
    
    public boolean isSkidding(long currentTime) {
        return Timers.Squeal.isHot(currentTime);
    }
    
    public void onLapComplete() {
        Charges.Boost = Attributes.MaxCharges.Boost;
        Charges.Weapon = Attributes.MaxCharges.Weapon;
        Charges.Drop = Attributes.MaxCharges.Drop;
    }
    
    public void onArmorPowerUp() {
        Charges.Armor = Attributes.MaxCharges.Armor;
    }
    
    
    
}
