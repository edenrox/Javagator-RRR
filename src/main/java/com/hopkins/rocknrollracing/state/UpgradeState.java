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
    
    public UpgradeState() {
        Levels = new int[] {
            0, 0, 0, 0,
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
    
    public void setLevels(UpgradeType[] types, int level) {
        for(UpgradeType type : types) {
            setLevel(type, level);
        }
    }
}
