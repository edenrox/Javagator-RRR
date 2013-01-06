/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ihopkins
 */
public abstract class HasFace {
    
    protected String name;
    
    public String getName() {
        return name;
    }
    
    public HasFace(String name) {
        this.name = name;
    }
    
}
