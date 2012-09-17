/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.model;

import java.awt.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ian
 */
public class CarModelColor implements NamedModel {
    protected String name;
    protected int[] colors;
    protected boolean selectable;
    
    public String getName() { return name; }
    public int[] getColors() { return colors; }
    public boolean isSelectable() { return selectable; }
    
    public void fromJSON(JSONObject json) {
        name = (String) json.get("name");
        
        JSONArray array = (JSONArray) json.get("colors");
        colors = new int[array.size()];
        for (int i = 0; i < array.size(); i++) {
            colors[i] = Integer.parseInt((String) array.get(i), 16);
        }
        
        selectable = (Boolean) json.get("selectable");
    }
}
