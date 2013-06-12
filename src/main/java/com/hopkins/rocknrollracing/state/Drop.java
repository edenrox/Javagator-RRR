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
public class Drop extends NamedModel {
    public static final Drop Oil = new Drop("BF's Slipsauce", 0);
    public static final Drop Mine = new Drop("Bear Claw Mine", 2);
    public static final Drop Scatterpack = new Drop("KO Scatterpack", 2);
    
    public static final Drop[] All = new Drop[] {
        Oil, Mine, Scatterpack
    };
    
    protected int damage;
    
    public Drop(String name, int damage) {
        super(name);
        this.damage = damage;
    }
    
    public float getDamage() {
        return damage;
    }
    
    public int ordinal() {
        return ArrayUtils.indexOfObject(All, this);
    }
}
