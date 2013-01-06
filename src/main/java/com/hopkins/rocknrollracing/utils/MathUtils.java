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
}
