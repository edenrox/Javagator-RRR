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
    public static final Weapon Plasma = new Weapon("VK Plasma Rifle", 2f, 0.14f);
    public static final Weapon Missile = new Weapon("Rogue Missile", 2f, 0.14f);
    public static final Weapon Sundog = new Weapon("Sundog Beam", 1f, 0.1f);
    
    public static final Weapon[] All = new Weapon[] {
        Plasma, Missile, Sundog
    };
    
    protected String name;
    protected float damage;
    protected float speed;
    
    
    public float getDamage() {
        return damage;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public Weapon(String name, float damage, float speed) {
        this.name = name;
        this.damage = damage;
        this.speed = speed;
    }
    
    public int ordinal() {
        return ArrayUtils.indexOfObject(All, this);
    }
}
