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
            0, 0, 0
        };
    }
    
    public int getLevel(UpgradeType type) {
        return Levels[type.ordinal()];
    }
    public void incLevel(UpgradeType type) {
        Levels[type.ordinal()]++;
    }
}
