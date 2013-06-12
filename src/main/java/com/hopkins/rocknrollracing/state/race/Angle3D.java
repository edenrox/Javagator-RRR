/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.utils.ArrayUtils;
import com.hopkins.rocknrollracing.utils.MathUtils;

/**
 *
 * @author ian
 */
public class Angle3D {
    
    public static final int[] PITCHES = new int[] {-63, -45, 0, 45, 63};
    public static final int YAW_INCREMENT = 5;
    
    protected int pitchIndex;
    protected int yaw;
    
    public Angle3D() {
        setYaw(0);
        setPitch(0);
    }
    
    public Angle3D(int yaw, int pitch) {
        setYaw(yaw);
        setPitch(pitch);
    }
    
    public int getPitch() {
        return PITCHES[pitchIndex];
    }
    public int getYaw() {
        return yaw;
    }
    public int getRoll() {
        return 0;
    }
    
    public void copyFrom(Angle3D that) {
        this.yaw = that.yaw;
        this.setPitch(that.getPitch());
    }
    
    
    public void incPitch() {
        addPitch(1);
    }
    public void decPitch() {
        addPitch(-1);
    }
    protected void addPitch(int amount) {
        MathUtils.forceInRange(pitchIndex, 0, 4);
    }
    public final void setPitch(int amount) {
        int index = ArrayUtils.indexOf(PITCHES, amount);
        if (index > -1) {
            pitchIndex = index;
        }
    }
    
    
    public void incYaw() {
        addYaw(YAW_INCREMENT);
    }
    public void decYaw() {
        addYaw(-YAW_INCREMENT);
    }
    public final void setYaw(int value) {
        yaw = value;
        if (yaw < 0) {
            yaw += 360;
        }
        if (yaw > 360) {
            yaw -= 360;
        }
        if (yaw % YAW_INCREMENT != 0) {
            yaw = (yaw / YAW_INCREMENT) * YAW_INCREMENT;
        }
    }
    protected void addYaw(int amount) {
        yaw += amount;
        if (yaw < 0) {
            yaw += 360;
        }
        if (yaw >= 360) {
            yaw -= 360;
        }
    }
    
    
    public static int getYawFromDirection(Direction dir) {
        switch (dir) {
            default:
            case Right:
                return 0;
            case Up:
                return 90;
            case Left:
                return 180;
            case Down:
                return 270;
        }
    }
}
