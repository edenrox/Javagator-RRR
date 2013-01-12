/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

/**
 *
 * @author ian
 */
public class Vector {
    public float X;
    public float Y;
    
    public float getMagnitude() {
        return (float) Math.sqrt(X * X + Y * Y);
    }
}
