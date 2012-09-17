/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.view;

import java.util.HashMap;

/**
 *
 * @author ian
 */
public class ColorMap {
    protected HashMap<Integer, Integer> data;
    
    public ColorMap() {
        data = new HashMap<Integer, Integer>();
    }
    
    public void add(int from, int to) {
        data.put(0xff000000 | from, 0xff000000 | to);
    }
    
    public boolean has(int index) {
        return data.containsKey(index);
    }
    
    public int get(int index) {
        return data.get(index);
    }
    
    public void add(int[] from, int[] to) {
        for(int i = 0; i < from.length; i++) {
            add(from[i], to[i]);
        }
    }
}
