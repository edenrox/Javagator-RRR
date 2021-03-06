/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.track;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ian
 */
public class TrackPiece {
    public static final Logger log = Logger.getLogger(TrackPiece.class);
    
    protected TrackPieceType type;
    protected int[] height;
    protected boolean isCheckPoint;
    
    public boolean isCheckPoint() {
        return isCheckPoint;
    }
    
    public void setCheckPoint(boolean value) {
        isCheckPoint = value;
    }
    
    public TrackPieceType getType() {
        return type;
    }
    public void setType(TrackPieceType value) {
        type = value;
    }
    
    public void setHeightAll(int value) {
        for(int i = 0; i < height.length; i++) {
            setHeight(i, value);
        }
    }
    public void setHeight(int index, int value) {
        height[index] = value;
    }
    
    public int getHeight() {
        return getHeight(0);
    }
    
    public int getHeight(int index) {
        return height[index];
    }
    
    public TrackPiece() {
        type = TrackPieceType.Empty;
        height = new int[7];
    }
    
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("type", type.toString());
        if (type.isMultiHeight()) {
            JSONArray heightJSONArray = new JSONArray();
            for(int i = 0; i < height.length; i++) {
                heightJSONArray.add(height[i]);
            }
            obj.put("height", heightJSONArray);
        } else {
            obj.put("height", getHeight());
        }
        if (isCheckPoint) {
            obj.put("checkpoint", "true");
        }
        return obj;
    }
    
    public void fromJSON(JSONObject obj) {
        try {
            type = TrackPieceType.valueOf((String) obj.get("type"));
            Object objHeight = obj.get("height");
            if (objHeight != null) {
                if (type.isMultiHeight()) {
                    if (objHeight.getClass().equals(JSONArray.class)) {
                        JSONArray arr = (JSONArray) obj.get("height");
                        for(int i = 0; i < arr.size(); i++) {
                            setHeight(i, ((Long) arr.get(i)).intValue());
                        }
                    }
                } else {
                    if (objHeight.getClass().equals(Long.class)) {
                        setHeightAll(((Long) obj.get("height")).intValue());
                    }
                }
            }
            isCheckPoint = ("true".equals(obj.get("checkpoint")));
        } catch (IllegalArgumentException ex) {
            type = TrackPieceType.Empty;
            log.error(ex);
        }
    }
    
    public boolean isEmpty() {
        return (type == TrackPieceType.Empty);
    }
}
