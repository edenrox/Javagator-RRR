/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.utils.MathUtils;

/**
 *
 * @author ian
 */
public class RaceItem {
    protected Vector3D position;
    protected Vector3D velocity;
    protected int angle;
    protected RaceItemType type;
    protected Object object;
    
    public Vector3D getPosition() {
        return position;
    }
    
    public Vector3D getVelocity() {
        return velocity;
    }
    
    public int getAngle() {
        return angle;
    }
    public void setAngle(int value) {
        angle = value;
    }
    
    public RaceItemType getType() {
        return type;
    }
    
    public Object getObject() {
        return object;
    }
    
    public RaceItem(RaceItemType type, Object obj) {
        this.position = new Vector3D();
        this.velocity = new Vector3D();
        this.angle = 0;
        this.type = type;
        this.object = obj;
    }
    
    public void incAngle(int incAmount) {
        if (incAmount < 0) {
            incAmount += MathUtils.MAX_ANGLE;
        }
        this.angle = (this.angle + incAmount) % MathUtils.MAX_ANGLE;
    }
}
