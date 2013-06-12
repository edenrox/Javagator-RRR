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
public class PowerUp extends NamedModel {
    public static final PowerUp Money = new PowerUp("Money");
    public static final PowerUp Armor = new PowerUp("Armor");
    
    public static final PowerUp[] All = new PowerUp[] { Money, Armor };
    
    public PowerUp(String name) {
        super(name);
    }
    
    public int ordinal() {
        return ArrayUtils.indexOfObject(All, this);
    }
    
}
