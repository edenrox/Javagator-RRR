/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

/**
 *
 * @author ian
 */
public class RaceCarIntent {
    public boolean Accelerate;
    public boolean TurnLeft;
    public boolean TurnRight;
    public boolean Weapon;
    public boolean Boost;
    public boolean Drop;
    
    public void reset() {
        Accelerate = TurnLeft = TurnRight = Weapon = Boost = Drop = false;
    }
}
