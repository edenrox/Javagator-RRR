/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

/**
 *
 * @author ian
 */
public class TrigLU {
    public static final int NUM_DEGREES = 360;
    
    public static double[] SIN_TABLE;
    public static double[] COS_TABLE;
    static {
        SIN_TABLE = new double[NUM_DEGREES];
        COS_TABLE = new double[NUM_DEGREES];
        for(int i = 0; i < NUM_DEGREES; i++) {
            SIN_TABLE[i] = Math.sin(i / 180.0 * Math.PI);
            COS_TABLE[i] = Math.cos(i / 180.0 * Math.PI);
        }
    }
    
    public static double sin(int angle) {
        return SIN_TABLE[angle];
    }
    public static double cos(int angle) {
        return COS_TABLE[angle];
    }
}
