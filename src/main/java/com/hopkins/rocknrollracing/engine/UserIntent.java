/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.engine;

import com.hopkins.rocknrollracing.state.ControllerButton;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.race.RaceCarIntent;

/**
 *
 * @author ian
 */
public class UserIntent {
    
    
    public static void calculateIntent(ControllerState cs, RaceCarIntent rci) {
        rci.reset();
        if (cs.isDown(ControllerButton.Gas)) {
            rci.Accelerate = true;
        }
        if (cs.isDown(ControllerButton.Left)) {
            rci.TurnLeft = true;
        } else if (cs.isDown(ControllerButton.Right)) {
            rci.TurnRight = true;
        }
        
        if (cs.isPressed(ControllerButton.Weapon)) {
            rci.Weapon = true;
        }
        if (cs.isPressed(ControllerButton.Drop)) {
            rci.Drop = true;
        }
        if (cs.isPressed(ControllerButton.Boost)) {
            rci.Boost = true;
        }
    }
    
}
