/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.track;

/**
 *
 * @author ian
 */
public enum TrackPieceType {
    Empty, 
    CornerDownRight, CornerDownLeft, CornerUpRight, CornerUpLeft,
    StraightRight, StraightUp, StartRight, StartUp, 
    JumpRight, JumpLeft, JumpUp, JumpDown,  
    NotUp, NotDown, NotRight, NotLeft,
    Cross
}
