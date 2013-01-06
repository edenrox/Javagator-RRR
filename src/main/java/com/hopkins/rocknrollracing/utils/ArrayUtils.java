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
    public static final int NOT_FOUND = -1;
    
    public static <T> T[] map(T[] array, MapFunction<T> func) {
        for(int i = 0; i < array.length; i++) {
            array[i] = func.execute(array[i]);
        }
        return array;
    }
    
    
    public static int indexOf(int[] haystack, int needle) {
        for(int i = 0; i < haystack.length; i++) {
            if (needle == haystack[i]) {
                return i;
            }
        }
        return NOT_FOUND;
    }
    public static int indexOfObject(Object[] haystack, Object needle) {
        for(int i = 0; i < haystack.length; i++) {
            if (needle == haystack[i]) {
                return i;
            }
        }
        return NOT_FOUND;
    }
    public static int indexOf(String[] haystack, String needle) {
        for(int i = 0; i < haystack.length; i++) {
            if (needle.equals(haystack[i])) {
                return i;
            }
        }
        return NOT_FOUND;
    }
    
    public static interface MapFunction<T> {
        public T execute(T item);
    }
}
