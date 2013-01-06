/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.trackeditor;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author ian
 */
public class Piece {
    public static final Logger log = Logger.getLogger(Piece.class);
    
    protected PieceType type;
    protected PieceFeatureType feature;
    protected int[] height;
    
    public PieceType getType() {
        return type;
    }
    public void setType(PieceType value) {
        type = value;
        if ((value != PieceType.StraightUp) && (value != PieceType.StraightRight)) {
            feature = PieceFeatureType.None;
        }
    }
    
    public Piece() {
        type = PieceType.Empty;
        feature = PieceFeatureType.None;
        height = new int[7];
    }
    
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("type", type.toString());
        if (feature != PieceFeatureType.None) {
            obj.put("feature", feature.toString());
        }
        return obj;
    }
    
    public void fromJSON(JSONObject obj) {
        try {
            type = PieceType.valueOf((String) obj.get("type"));
        } catch (IllegalArgumentException ex) {
            type = PieceType.Empty;
            log.error(ex);
        }
        if (obj.containsKey("feature")) {
            feature = PieceFeatureType.valueOf((String) obj.get("feature"));
        }
    }
}
