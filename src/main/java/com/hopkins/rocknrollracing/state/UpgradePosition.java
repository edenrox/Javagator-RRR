/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ian
 */
public class UpgradePosition {
    
    public static final UpgradePosition[] DirtDevil = new UpgradePosition[] {
        new UpgradePosition(60, 89, UpgradeType.Armor),
        new UpgradePosition(185, 89, UpgradeType.Engine),
        new UpgradePosition(203, 124, UpgradeType.Drop),
        new UpgradePosition(206, 168, UpgradeType.Boost),
        new UpgradePosition(161, 173, UpgradeType.Shocks),
        new UpgradePosition(69, 173, UpgradeType.Weapon),
        new UpgradePosition(21, 145, UpgradeType.Tires),
    };
    
    public static final UpgradePosition[] Marauder = new UpgradePosition[] {
        new UpgradePosition(56, 90, UpgradeType.Engine),
        new UpgradePosition(206, 114, UpgradeType.Drop),
        new UpgradePosition(206, 174, UpgradeType.Boost),
        new UpgradePosition(158, 174, UpgradeType.Shocks),
        new UpgradePosition(73, 174, UpgradeType.Weapon),
        new UpgradePosition(18, 174, UpgradeType.Tires),
        new UpgradePosition(18, 132, UpgradeType.Armor),
    };
    
    public static final UpgradePosition[] AirBlade = new UpgradePosition[] {
        new UpgradePosition(82, 91, UpgradeType.Armor),
        new UpgradePosition(171, 92, UpgradeType.Engine),
        new UpgradePosition(195, 135, UpgradeType.Drop),
        new UpgradePosition(206, 174, UpgradeType.Boost),
        new UpgradePosition(145, 175, UpgradeType.Shocks),
        new UpgradePosition(18, 174, UpgradeType.Weapon),
        new UpgradePosition(18, 138, UpgradeType.Tires),
    };
    
    public static final UpgradePosition[] BattleTrak = new UpgradePosition[] {
        new UpgradePosition(80, 89, UpgradeType.Armor),
        new UpgradePosition(124, 111, UpgradeType.Engine),
        new UpgradePosition(206, 89, UpgradeType.Drop),
        new UpgradePosition(206, 130, UpgradeType.Shocks),
        new UpgradePosition(200, 174, UpgradeType.Boost),
        new UpgradePosition(56, 174, UpgradeType.Weapon),
    };
    
    public static final UpgradePosition[] Havoc = new UpgradePosition[] {
        new UpgradePosition(79, 89, UpgradeType.Armor),
        new UpgradePosition(206, 89, UpgradeType.Engine),
        new UpgradePosition(202, 129, UpgradeType.Drop),
        new UpgradePosition(206, 174, UpgradeType.Boost),
        new UpgradePosition(19, 173, UpgradeType.Weapon),
    };
    
    public static final UpgradePosition[][] All = new UpgradePosition[][] {
        DirtDevil,
        Marauder,
        AirBlade,
        BattleTrak,
        Havoc
    };
    
    protected int x;
    protected int y;
    protected UpgradeType type;
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public UpgradeType getType() {
        return type;
    }
    
    public UpgradePosition(int x, int y, UpgradeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
