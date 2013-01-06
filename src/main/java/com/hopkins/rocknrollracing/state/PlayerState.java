/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ian
 */
public class PlayerState {
    public static final int STARTING_MONEY = 200000;
    
    public CarColor Color;
    public CarModel Model;
    public Hero Hero;
    public int Money;
    public int Points;
    public UpgradeState Upgrades;
    
    public PlayerState() {
        Color = CarColor.Blue;
        Model = CarModel.DirtDevil;
        Hero = Hero.Snake;
        Money = STARTING_MONEY;
        Points = 0;
        Upgrades = new UpgradeState();
    }
    
    public boolean canAfford(CarModel m) {
        return (Money >= m.getPrice());
    }
    
    public void buyCar(CarModel m, CarColor color) {
        Color = color;
        Model = m;
        Money -= m.getPrice();
        
        Upgrades = new UpgradeState();
    }
    
    public void awardPlace(PodiumPlace place) {
        Money += place.getPrizeMoney();
        Points += place.getPoints();
    }
    
    public Upgrade getCurrentUpgrade(UpgradeType type) {
        int level = Upgrades.getLevel(type);
        return Upgrade.All[type.ordinal()][level];
    }
    
    public Upgrade getNextUpgrade(UpgradeType type) {
        int level = Upgrades.getLevel(type);
        if (level < 3) {
            return Upgrade.All[type.ordinal()][level+1];
        } else {
            // no upgrade available
            return null;
        }
    }
    
    public boolean isAmmoUpgrade(UpgradeType type) {
        switch (type) {
            case Armor:
            case Engine:
            case Shocks:
            case Tires:
                return false;
            default:
                return true;
        }
    }
    
    public boolean canUpgrade(UpgradeType type) {
        int level = Upgrades.getLevel(type);
        if (isAmmoUpgrade(type)) {
            return (level < 6);
        } else {
            return (level < 3);
        }
    }
    
    public void buyUpgrade(UpgradeType type) {
        Upgrade next = getNextUpgrade(type);
        
        Money -= next.getPrice();
        Upgrades.incLevel(type);
    }
}
