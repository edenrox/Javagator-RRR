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
public class CarModel implements NamedModel {
    protected String name;
    protected int price;
    
    public String getName() { return name; }
    public int getPrice() { return price; }
    
    
    public void fromJSON(JSONObject json) {
        name = (String) json.get("name");
        price = ((Long) json.get("price")).intValue();
    }
}
