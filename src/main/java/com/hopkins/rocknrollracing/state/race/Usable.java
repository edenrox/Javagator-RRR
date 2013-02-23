/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

/**
 *
 * @author ian
 */
public class Usable {
    protected int available;
    protected int max;
    protected int cooldown;
    
    public int getAvailable() {
        return available;
    }
    public int getMax() {
        return max;
    }
    public boolean isCoolingDown() {
        return (cooldown > 0);
    }
    
    public Usable(int max) {
        this.available = max;
        this.max = max;
        this.cooldown = 0;
    }
    
    public boolean canUse() {
        return (!isCoolingDown()) && (available > 0);
    }
    
    public void tick() {
        if (isCoolingDown()) {
            cooldown--;
        }
    }
    
    public boolean use(int cooldown) {
        if (canUse()) {
            this.cooldown = cooldown;
            this.available--;
            return true;
        } else {
            return false;
        }
    }
    
    public void refill() {
        available = max;
    }
}
