/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

import com.hopkins.rocknrollracing.utils.ArrayUtils;

/**
 *
 * @author ian
 */
public class Drop {
    public static final Drop Oil = new Drop("BF's Slipsauce");
    public static final Drop Mine = new Drop("Bear Claw Mine");
    public static final Drop Scatterpack = new Drop("KO Scatterpack");
    
    public static final Drop[] All = new Drop[] {
        Oil, Mine, Scatterpack
    };
    
    protected String name;
    
    public Drop(String name) {
        this.name = name;
    }
    
    public int ordinal() {
        return ArrayUtils.indexOfObject(All, this);
    }
}
