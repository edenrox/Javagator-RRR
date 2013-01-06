/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

import com.hopkins.rocknrollracing.utils.ArrayUtils;

/**
 *
 * @author ihopkins
 */
public class Enemy extends HasFaceAndPlanet {
    
    public static final Enemy Butcher = new Enemy("Butcher Icebone", Planet.Nho, CarModel.Havoc, 14, 3200);
    public static final Enemy Grinder = new Enemy("Grinder X19", Planet.Drakonis, CarModel.AirBlade, 10, 2000);
    public static final Enemy Slash = new Enemy("J.B. Slash", Planet.Inferno, CarModel.Havoc, 14, 3200);
    public static final Enemy Rage = new Enemy("Ragewortt", Planet.Bogmire, CarModel.BattleTrak, 12, 2900);
    public static final Enemy Rip = new Enemy("Rip", null, null, 0, 0);
    public static final Enemy Roadkill = new Enemy("Roadkill Kelly", Planet.NewMojave, CarModel.BattleTrak, 14, 3200);
    public static final Enemy Shred = new Enemy("Shred", null, null, 0, 0);
    public static final Enemy Viper = new Enemy("Viper Mackay", Planet.ChemVI, CarModel.Marauder, 8, 1600);
    
    public static final Enemy All[] = new Enemy[] {Viper, Grinder, Rage, Roadkill, Butcher, Slash};
    
    public static final Enemy CPUPlayers[] = new Enemy[] {Rip, Shred};
    
    protected CarModel model;
    protected int numRaces;
    protected int pointsRequired;
    
    public CarModel getModel() {
        return model;
    }
    
    public int getNumRaces() {
        return numRaces;
    }
    
    public int getPointsRequired() {
        return pointsRequired;
    }
    
    public Enemy(String name, Planet planet, CarModel model, int numRaces, int pointsRequired) {
        super(name, planet);
        this.model = model;
        this.numRaces = numRaces;
        this.pointsRequired = pointsRequired;
    }
    
    public int ordinal() {
        return ArrayUtils.indexOfObject(All, this);
    }
    
}
