/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

/**
 *
 * @author ian
 */
public abstract class BaseEntity {
    
    public Vector3D Position;
    public Vector3D Velocity;
    public Vector3D Size;
    public Angle3D Angle;
    public BoundingCube Bounds;
    public int Frame;
    
    public BaseEntity() {
        Position = new Vector3D(0,0,0);
        Velocity = new Vector3D(0,0,0);
        Size = new Vector3D(1,1,1);
        Angle = new Angle3D();
        Bounds = new BoundingCube();
        Frame = 0;
    }
    
    public void incrementPosition() {
        Position.add(Velocity);
    }
}
