/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

import com.hopkins.rocknrollracing.utils.ArrayUtils;

/**
 *
 * @author ian
 */
public class CarModel extends NamedModel {
    
    public static final CarModel DirtDevil = new CarModel("Dirt Devil", Weapon.Plasma, Boost.Jump, Drop.Oil, 18000);
    public static final CarModel Marauder = new CarModel("Marauder", Weapon.Plasma, Boost.Jump, Drop.Oil, 18000);
    public static final CarModel AirBlade = new CarModel("Air Blade", Weapon.Missile, Boost.Nitro, Drop.Mine, 70000);
    public static final CarModel BattleTrak = new CarModel("Battle Trak", Weapon.Missile, Boost.Nitro, Drop.Scatterpack, 110000);
    public static final CarModel Havoc = new CarModel("Havoc", Weapon.Sundog, Boost.Nitro, Drop.Scatterpack, 130000);
    
    public static final CarModel[] All = new CarModel[] {
        DirtDevil, Marauder, AirBlade, BattleTrak, Havoc
    };
    
    protected Weapon weapon;
    protected Boost boost;
    protected Drop drop;
    protected int price;
    
    public Weapon getWeapon() {
        return weapon;
    }
    
    public Boost getBoost() {
        return boost;
    }
    
    public Drop getDrop() {
        return drop;
    }
    
    public int getPrice() {
        return price;
    }
        
    public CarModel(String name, Weapon weapon, Boost boost, Drop drop, int price) {
        super(name);
        this.weapon = weapon;
        this.boost = boost;
        this.drop = drop;
        this.price = price;
    }
    
    public int ordinal() {
        return ArrayUtils.indexOfObject(All, this);
    }
}
