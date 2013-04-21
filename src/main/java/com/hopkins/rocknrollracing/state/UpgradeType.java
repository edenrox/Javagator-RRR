/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ihopkins
 */
public enum UpgradeType {
    Armor, Engine, Shocks, Tires,
    Weapon, Boost, Drop;
    
    public static UpgradeType[] Charges = new UpgradeType[] {Weapon, Boost, Drop};
    public static UpgradeType[] Parts = new UpgradeType[] {Armor, Engine, Shocks, Tires};
    
    public boolean isAmmo() {
        return ((this == Weapon) || (this == Boost) || (this == Drop));
    }
}
