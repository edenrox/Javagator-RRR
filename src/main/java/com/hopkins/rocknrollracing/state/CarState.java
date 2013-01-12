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
    public static final int NUM_FRAMES = 24;
    
    public int x;
    public int y;
    public int z;
    public int trackZ;
    public int frame;
    public CarColor color;
    public CarModel model;
    public int wheelPosition;
    
    public boolean isHFlip() {
        return frame > 12;
    }
    
    public static int getFrameFromAngle(int angle) {
        return (NUM_FRAMES + 18 - angle) % NUM_FRAMES;
    }
}
