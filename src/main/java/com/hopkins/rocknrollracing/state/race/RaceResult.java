/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.state.HasFace;

/**
 *
 * @author ian
 */
public class RaceResult {
    public HasFace[] Positions;
    
    public RaceResult() {
        Positions = new HasFace[4];
    }
}
