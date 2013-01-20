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
public class Vector3D {
    public static final float Epsilon = 0.01f;
    
    public float X;
    public float Y;
    public float Z;
    
    public float getMagnitude() {
        return (float) Math.sqrt(X * X + Y * Y + Z * Z);
    }
    
    public void scale(float scale) {
        X = X * scale;
        Y = Y * scale;
    }
    
    public void setMagnitude(float quantity) {
        float magnitude = getMagnitude();
        float factor = quantity / magnitude;
        if (magnitude < Epsilon) {
            factor = 0;
        }
        X = X * factor;
        Y = Y * factor;
    }
    
    public void add(Vector3D toAdd) {
        X += toAdd.X;
        Y += toAdd.Y;
        Z += toAdd.Z;
    }
    
    public void add(float quantity, int angle) {
        float dx = (float) (quantity * MathUtils.cos(angle));
        float dy = (float) (-quantity * MathUtils.sin(angle));
        
        X += dx;
        Y += dy;
    }
    
    public void copy(Vector3D that) {
        this.X = that.X;
        this.Y = that.Y;
        this.Z = that.Z;
    }
    
    public void set(float x, float y, float z) {
        this.X = x;
        this.Y = y;
        this.Z = z;
    }
    
    public int getAngle() {
        int angle = (int) Math.round(Math.atan2(-this.Y, this.X) * 180 / Math.PI);
        if (angle < 0) {
            angle = 360 + angle;
        }
        return angle;
    }
}
