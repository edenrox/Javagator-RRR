/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.utils;

/**
 *
 * @author ihopkins
 */
public class MathUtils {
    
    public static final int MAX_ANGLE = 24;
    
    public static final float[] COS;
    public static final float[] SIN;
    
    static {
        COS = new float[MAX_ANGLE];
        SIN = new float[MAX_ANGLE];
        for (int i = 0; i < MAX_ANGLE; i++) {
            COS[i] = (float) Math.cos(i / 12.0 * Math.PI);
            SIN[i] = (float) Math.sin(i / 12.0 * Math.PI);
        }
    }
    
    public static int forceInRange(int item, int min, int max) {
        if (item < min) {
            return min;
        } else if (item > max) {
            return max;
        } else {
            return item;
        }
    }
    
    public static int wrapInRange(int item, int min, int max) {
        if (item < min) {
            return max;
        } else if (item > max) {
            return min;
        }
        return item;
    }
    
    public static float cos(int angle) {
        return COS[angle];
    }
    
    public static float sin(int angle) {
        return SIN[angle];
    }
}
