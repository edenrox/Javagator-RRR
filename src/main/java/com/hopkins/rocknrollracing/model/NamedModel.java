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
public interface NamedModel {
    public String getName();
    public void fromJSON(JSONObject obj);
}
