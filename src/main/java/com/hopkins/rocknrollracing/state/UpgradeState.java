/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ihopkins
 */
public class UpgradeState {
    public int[] Levels;
    public int[] Charges;
    
    public UpgradeState() {
        Levels = new int[] {
            0, 0, 0, 0, 0, 0, 0
        };
        Charges = new int[] {
            1, 1, 1
        };
    }
    
    public int getLevel(UpgradeType type) {
        return Levels[type.ordinal()];
    }
    public void incLevel(UpgradeType type) {
        Levels[type.ordinal()]++;
    }
    public void setLevel(UpgradeType type, int level) {
        Levels[type.ordinal()] = level;
    }
    
    protected int getChargeIndex(UpgradeType type) {
        return type.ordinal() - UpgradeType.Weapon.ordinal();
    }
    
    public int getCharges(UpgradeType type) {
        return Charges[getChargeIndex(type)];
    }
    public void incCharges(UpgradeType type) {
        Charges[getChargeIndex(type)]++;
    }
    public void setCharges(UpgradeType type, int charges) {
        Charges[getChargeIndex(type)] = charges;
    }

    public void setLevels(UpgradeType[] types, int level) {
        for(UpgradeType type : types) {
            setLevel(type, level);
        }
    }
    public void setCharges(UpgradeType[] types, int level) {
        for(UpgradeType type : types) {
            setCharges(type, level);
        }
    }
}
