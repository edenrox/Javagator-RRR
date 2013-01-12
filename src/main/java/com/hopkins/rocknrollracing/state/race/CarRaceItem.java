/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.state.CarColor;
import com.hopkins.rocknrollracing.state.CarModel;

/**
 *
 * @author ian
 */
public class CarRaceItem extends RaceItem {
    public int Armor;
    public int SlopeAngle;
    
    public int[] Charges;
    protected CarColor color;
    protected int lap;
    
    public CarColor getColor() {
        return color;
    }
    
    public CarModel getModel() {
        return (CarModel) this.object;
    }
    public int getLap() {
        return lap;
    }
    
    public CarRaceItem(CarModel model, CarColor color) {
        super(RaceItemType.Car, model);
        this.color = color;
        Charges = new int[] {7, 7, 7};
        lap = 1;
    }
    
    public boolean isMoving() {
        return (Math.abs(getVelocity().getMagnitude()) > 0.5f);
    }
    
    public void onLapComplete() {
        lap++;
        Charges[0] = 7;
        Charges[1] = 7;
        Charges[2] = 7;
    }
}
