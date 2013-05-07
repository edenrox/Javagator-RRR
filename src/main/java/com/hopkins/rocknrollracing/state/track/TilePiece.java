/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.track;

/**
 *
 * @author ian
 */
public enum TilePiece {
    Empty,
    FlatRoad, FlatEdgeTop, FlatEdgeBottom,
    FlatFinish,
    ShallowUpRoad, ShallowUpEdgeTop, ShallowUpEdgeBottom,
    SteepUpRoad, SteepUpEdgeTop, SteepUpEdgeBottom,
    ShallowDownRoad, ShallowDownEdgeTop, ShallowDownEdgeBottom,
    SteepDownRoad, SteepDownEdgeTop, SteepDownEdgeBottom,
    
    TransitionFlat,
    TransitionShallowUp,
    TransitionShallowDown,
    TransitionSteepUp,
    TransitionSteepDown,

    CornerNorthInside, CornerNorthOutside,
    CornerSouthInside, CornerSouthOutside,
    CornerWestInside, CornerWestOutside,
    
    Puddle,
    
    Background,
    PillarTop,
    PillarMiddle,
    PillarBottom
}
