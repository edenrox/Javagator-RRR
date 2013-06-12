/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

/**
 *
 * @author ian
 */
public class RaceCarTimers {
    public CooldownTimer Weapon, Drop, Boost, Turn, Squeal, Damage, Explosion, PuddleSpin;
    
    public RaceCarTimers() {
        Weapon = new CooldownTimer(15);
        Drop = new CooldownTimer(15);
        Boost = new CooldownTimer(15);
        Turn = new CooldownTimer(5);
        Squeal = new CooldownTimer(15);
        Damage = new CooldownTimer(5);
        Explosion = new CooldownTimer(15);
        PuddleSpin = new CooldownTimer(15);
    }
}
