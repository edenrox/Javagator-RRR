/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.state.CarAttribute;
import com.hopkins.rocknrollracing.state.Hero;
import com.hopkins.rocknrollracing.state.UpgradeState;
import com.hopkins.rocknrollracing.state.UpgradeType;

/**
 *
 * @author ian
 */
public class RaceCarAttributes {
    public float TopSpeed;
    public float Acceleration;
    public float Cornering;
    public float Suspension;
    public float Jump;
    public RaceCarCharges MaxCharges;
    
    public RaceCarAttributes() {
        TopSpeed = 0;
        Acceleration = 0;
        Cornering = 0;
        Suspension = 0;
        Jump = 0;
        MaxCharges = new RaceCarCharges();
    }
    
    public void calculate(UpgradeState us, Hero hero) {
        MaxCharges.Armor = 8 + us.getLevel(UpgradeType.Armor) * 2;
        MaxCharges.Boost = us.getCharges(UpgradeType.Boost);
        MaxCharges.Drop = us.getCharges(UpgradeType.Drop);
        MaxCharges.Weapon = us.getCharges(UpgradeType.Weapon);
        
        Acceleration += us.getLevel(UpgradeType.Engine);
        TopSpeed += us.getLevel(UpgradeType.Engine);
        Cornering += us.getLevel(UpgradeType.Tires);
        Suspension += us.getLevel(UpgradeType.Shocks);
        Jump += us.getLevel(UpgradeType.Shocks);
        
        if (hero != null) {
            if (hero.hasBonus(CarAttribute.Acceleration)) {
                Acceleration += 1;
            }
            if (hero.hasBonus(CarAttribute.Cornering)) {
                Cornering += 1;
            }
            if (hero.hasBonus(CarAttribute.Jump)) {
                Jump += 1;
            }
            if (hero.hasBonus(CarAttribute.TopSpeed)) {
                TopSpeed += 1;
            }
        }
    }
}
