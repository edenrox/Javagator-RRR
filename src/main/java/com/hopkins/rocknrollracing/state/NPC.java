/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ihopkins
 */
public class NPC extends HasFace {
    
    public static final NPC Gordo = new NPC("Gordo");
    public static final NPC Braddock = new NPC("Captain Braddock");
    public static final NPC Eddie = new NPC("Fast Eddie");
    
    public static final NPC All[] = new NPC[] { Gordo, Braddock, Eddie };
    
    
    public NPC(String name) {
        super(name);
    }
    
}
