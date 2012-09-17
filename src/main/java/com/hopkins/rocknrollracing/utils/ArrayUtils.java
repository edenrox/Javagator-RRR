/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.utils;

/**
 *
 * @author ian
 */
public class ArrayUtils {
    
    public static <T> T[] map(T[] array, MapFunction<T> func) {
        for(int i = 0; i < array.length; i++) {
            array[i] = func.execute(array[i]);
        }
        return array;
    }
    
    public static interface MapFunction<T> {
        public T execute(T item);
    }
}
