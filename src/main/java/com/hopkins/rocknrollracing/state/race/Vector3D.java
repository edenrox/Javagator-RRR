/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.state.race.Angle3D;

/**
 *
 * @author ian
 */
public class Vector3D {
    public static final float EPSILON = 0.0001f;
    
    // Instance variables
    
    public float X;
    public float Y;
    public float Z;
    
    // Constructors
    
    public Vector3D() {
        reset(0, 0, 0);
    }
    public Vector3D(float x, float y, float z) {
        reset(x, y, z);
    }
    
    // Copy/clone operators
    
    public void copyFrom(Vector3D toCopyFrom) {
        X = toCopyFrom.X;
        Y = toCopyFrom.Y;
        Z = toCopyFrom.Z;
    } 
    
    @Override
    public Vector3D clone() {
        return new Vector3D(X, Y, Z);
    }
    
    
    
    // Accessors
    
    public float length() {
        return (float) Math.sqrt(X * X + Y * Y + Z * Z);
    }
    
    // Mutators
    
    public void unitFromAngles(Angle3D angles) {
        X = (float) TrigLU.cos(angles.getYaw());
        Y = (float) -TrigLU.sin(angles.getYaw());
        Z = (float) TrigLU.sin(angles.getPitch());
    }
    
    public final void reset(float x, float y, float z) {
        X = x;
        Y = y;
        Z = z;
    }

    public void add(Vector3D toAdd) {
        X += toAdd.X;
        Y += toAdd.Y;
        Z += toAdd.Z;
    }
    
    public void normalize() {
        float length = this.length();
        X = X / length;
        Y = Y / length;
        Z = Z / length;
    }
    
    public void scale(float factor) {
        X = X * factor;
        Y = Y * factor;
        Z = Z * factor;
    }
    
    public void subtract(Vector3D that) {
        X = X - that.X;
        Y = Y - that.Y;
        Z = Z - that.Z;
    }
    
    @Override
    public String toString() {
        return String.format("[%.2f %.2f %.2f]", X, Y, Z);
    }
    
    @Override
    public boolean equals(Object that) {
        if ((that == null) || (that.getClass() != Vector3D.class)) {
            return false;
        }
        Vector3D vThat = (Vector3D) that;
        return (Math.abs(this.X - vThat.X) < EPSILON)
                && (Math.abs(this.Y - vThat.Y) < EPSILON)
                & (Math.abs(this.Z - vThat.Z) < EPSILON);
    }
}
