/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ihopkins
 */
public class HasFaceAndPlanet extends HasFace {
    
    protected Planet planet;
    
    public Planet getPlanet() {
        return planet;
    }
    
    public HasFaceAndPlanet(String name, Planet planet) {
        super(name);
        this.planet = planet;
    }
    
}
