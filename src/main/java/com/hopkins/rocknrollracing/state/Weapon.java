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
public class Weapon {
    public static final Weapon Plasma = new Weapon("VK Plasma Rifle", 1.0f);
    public static final Weapon Missile = new Weapon("Rogue Missile", 2.0f);
    public static final Weapon Sundog = new Weapon("Sundog Beam", 1.0f);
    
    public static final Weapon[] All = new Weapon[] {
        Plasma, Missile, Sundog
    };
    
    protected String name;
    protected float damage;
    
    public Weapon(String name, float damage) {
        this.name = name;
        this.damage = damage;
    }
    
    public int ordinal() {
        return ArrayUtils.indexOfObject(All, this);
    }
}
