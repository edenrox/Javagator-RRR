/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

import com.hopkins.rocknrollracing.utils.ArrayUtils;

/**
 *
 * @author ihopkins
 */
public class Hero extends HasFaceAndPlanet {
    
    public static final Hero Hawk = new Hero("Cyberhawk", Planet.Serpentis, new CarAttribute[] { CarAttribute.Acceleration, CarAttribute.Jump });
    public static final Hero Ivan = new Hero("Ivanzypher", Planet.Fleagull, new CarAttribute[] { CarAttribute.Jump, CarAttribute.TopSpeed });
    public static final Hero Jake = new Hero("Jake Badlands", Planet.XenoPrime, new CarAttribute[] { CarAttribute.Acceleration, CarAttribute.Cornering });
    public static final Hero Kat = new Hero("Katarina Lyons", Planet.PanterosV, new CarAttribute[] { CarAttribute.Jump, CarAttribute.Cornering });
    public static final Hero Olaf = new Hero("Olaf", Planet.Valhalla, new CarAttribute[] { CarAttribute.Acceleration, CarAttribute.TopSpeed, CarAttribute.Cornering });
    public static final Hero Snake = new Hero("Snake Sanders", Planet.Terra, new CarAttribute[] { CarAttribute.Acceleration, CarAttribute.TopSpeed });
    public static final Hero Tarquinn = new Hero("Tarquinn", Planet.Aurora, new CarAttribute[] { CarAttribute.TopSpeed, CarAttribute.Cornering });
    
    public static final Hero All[] = new Hero[] { Hawk, Ivan, Jake, Kat, Olaf, Snake, Tarquinn };
    
    protected CarAttribute[] bonuses;
    
    public CarAttribute[] getBonuses() {
        return bonuses;
    }
    
    public boolean hasBonus(CarAttribute bType) {
        for(CarAttribute bonus : bonuses) {
            if (bonus == bType) {
                return true;
            }
        }
        return false;
    }
    
    public Hero(String name, Planet planet, CarAttribute[] bonuses) {
        super(name, planet);
        this.bonuses = bonuses;
    }
    
    public String getBonusesString() {
        StringBuilder sb = new StringBuilder();
        for(CarAttribute bonus : bonuses) {
            sb.append("+1 " );
            sb.append(bonus.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
