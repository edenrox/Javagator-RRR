/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ian
 */
public class Difficulty extends NamedModel {
    
    public static final Difficulty Rookie = new Difficulty("Rookie");
    public static final Difficulty Veteran = new Difficulty("Veteran");
    public static final Difficulty Warrior = new Difficulty("Warrior");
    
    public static final Difficulty[] All = new Difficulty[] {
        Rookie, Veteran, Warrior
    };
    
    public Difficulty(String name) {
        super(name);
    }
}
