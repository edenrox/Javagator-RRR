/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ian
 */
public abstract class NamedModel {
    protected String name;
    
    public String getName() {
        return name;
    }
    
    public NamedModel(String name) {
        this.name = name;
    }
}
