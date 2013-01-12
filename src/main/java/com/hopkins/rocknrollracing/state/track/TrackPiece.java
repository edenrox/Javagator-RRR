/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.track;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author ian
 */
public class TrackPiece {
    public static final Logger log = Logger.getLogger(TrackPiece.class);
    
    protected TrackPieceType type;
    protected int[] height;
    
    public TrackPieceType getType() {
        return type;
    }
    public void setType(TrackPieceType value) {
        type = value;
    }
    
    public TrackPiece() {
        type = TrackPieceType.Empty;
        height = new int[7];
    }
    
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("type", type.toString());
        return obj;
    }
    
    public void fromJSON(JSONObject obj) {
        try {
            type = TrackPieceType.valueOf((String) obj.get("type"));
        } catch (IllegalArgumentException ex) {
            type = TrackPieceType.Empty;
            log.error(ex);
        }
    }
}
