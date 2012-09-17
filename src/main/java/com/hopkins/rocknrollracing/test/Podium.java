/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.test;

import com.hopkins.rocknrollracing.Application;
import com.hopkins.rocknrollracing.sound.Larry;

/**
 *
 * @author ian
 */
public class Podium {
    
    public static void main(String[] args) throws Exception {
        
        
        Application app = new Application();
        
        // Load the models
        app.models.load();
        
        Larry larry = new Larry(app);
        larry.playPodium("Jake", 1, null);
        larry.playPodium("Grinder", 2, null);
        larry.playPodium("Hawk", 3, null);
        larry.playPodium("Rip", 4, null);
        
        
    }
}
