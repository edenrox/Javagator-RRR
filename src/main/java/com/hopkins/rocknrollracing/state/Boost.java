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
public class Boost extends NamedModel {
    
    public static final Boost Jump = new Boost("Locust Jump Jets");
    public static final Boost Nitro = new Boost("Lightning Nitro");
    
    public static final Boost[] All = new Boost[] {
        Jump, Nitro
    };
    
    public Boost(String name) {
        super(name);
    }
    
    public int ordinal() {
        return ArrayUtils.indexOfObject(All, this);
    }
}
