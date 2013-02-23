/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.utils.MathUtils;
import com.hopkins.rocknrollracing.views.Screen;

/**
 *
 * @author ian
 */
public class World {
    
    public static final float WIDTH = 60.0f;
    public static final float HEIGHT = 60.0f;
    
    public static final float CAR_WIDTH = 1.0f;
    public static final float CAR_HEIGHT = 1.0f;
    
    public static Vector3D getCameraPosition(CarRaceItem cri) {
        Vector3D v = new Vector3D();
        
        // The camera will be position at the car + velocity * 2
        v.copy(cri.getPosition());

        v.X += 1;
        //v.Y += 1;
        
        return v;
    }
    
    public static Coord toMapPosition(Vector3D worldPos) {
        Coord c = new Coord();
        c.X = ((int)worldPos.X / 6) - 1;
        c.Y = ((int)worldPos.Y / 6) - 1;
        
        c.X = MathUtils.forceInRange(c.X, 0, 7);
        c.Y = MathUtils.forceInRange(c.Y, 0, 7);
        return c;
    }
    
    public static Vector3D fromMapPosition(Coord coord) {
        Vector3D rv = new Vector3D();
        rv.X = (coord.X + 1) * 6;
        rv.Y = (coord.Y + 1) * 6;
        
        return rv;
    }
    
    public static Coord toScreenPosition(Vector3D worldPos, Vector3D cameraPos) {
        
        // Calculate the delta between the camera and the world position
        Vector3D dv = new Vector3D();
        dv.X = worldPos.X - cameraPos.X;
        dv.Y = worldPos.Y - cameraPos.Y;
        dv.Z = worldPos.Z - cameraPos.Z;
        
        // Convert from 3D top-down to a 2D perspective view
        Coord v = new Coord();
        v.X = Screen.WIDTH / 2;
        v.X += (int) Math.round(32 * dv.X - 32 * dv.Y);
        
        v.Y = Screen.HEIGHT / 2;
        v.Y += (int) Math.round(8 * dv.X + 8 * dv.Y);
        v.Y += (int) Math.round(8 * dv.Z);
        
        return v;
    }
    
    public static boolean isOutOfBounds(Vector3D pos) {
        if ((pos.X < 0) || (pos.X > WIDTH)) {
            return true;
        }
        if ((pos.Y < 0) || (pos.Y > HEIGHT)) {
            return true;
        }
        return false;
    }
    
    public static boolean isWithinCameraClip(Vector3D pos, Vector3D cameraPos) {
        if (Math.abs(pos.X - cameraPos.X) > 12.0f) {
            return false;
        }
        if (Math.abs(pos.Y - cameraPos.Y) > 12.0f) {
            return false;
        }
        return true;
    }
    
    public static int toScreenAngle(int angleInDegrees) {
        return (angleInDegrees + 315) % 360;
    }
}
