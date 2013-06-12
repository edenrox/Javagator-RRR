/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

/**
 *
 * @author ian
 */
public class CooldownTimer {
    protected long lastUse;
    protected long cooldownTime;
    
    public CooldownTimer(long cooldownTime) {
        this.cooldownTime = cooldownTime;
    }
    
    /**
     * Use cool down timer if it is cool
     * @param currentTime the current time
     * @return true if used, false if not
     */
    public boolean useIfCool(long currentTime) {
        if (isCool(currentTime)) {
            lastUse = currentTime;
            return true;
        }
        return false;
    }
    
    /**
     * Check if the cool down timer is cool
     * @param currentTime the current time
     * @return true if cool
     */
    public boolean isCool(long currentTime) {
        if (currentTime - lastUse > cooldownTime) {
            return true;
        }
        return false;
    }
    
    public boolean isHot(long currentTime) {
        return (!isCool(currentTime));
    }
}
