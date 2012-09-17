/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.model;

import org.json.simple.JSONObject;

/**
 *
 * @author ian
 */
public class SoundModel implements NamedModel {
    protected String name;
    protected SoundType type;
    protected String when;
    
    public String getName() {
        return name;
    }
    public SoundType getType() {
        return type;
    }
    
    public void fromJSON(JSONObject obj) {
        name = (String) obj.get("name");
        
        if (obj.containsKey("when")) {
            type = SoundType.OnEvent;
            when = (String) obj.get("when");
        } else if (obj.containsKey("status")) {
            type = SoundType.Status;
            when = (String) obj.get("status");
        } else {
            type = SoundType.Podium;
            when = (String) obj.get("podium");
        }
    }
    
    public static enum SoundType {
        OnEvent,
        Status,
        Podium
    }
}
