/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ian
 */
public class CarState  {
    public static final int NUM_ANGLES = 24;
    
    public int x;
    public int y;
    public int z;
    public int trackZ;
    public int angle;
    public CarColor color;
    public CarModel model;
    public int frame;
    
    public boolean isHFlip() {
        return angle > 12;
    }
}
