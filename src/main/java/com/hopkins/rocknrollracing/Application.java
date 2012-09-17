/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing;

import com.hopkins.rocknrollracing.model.NamedModels;
import com.hopkins.rocknrollracing.view.SpriteCache;

/**
 *
 * @author ian
 */
public class Application {
    
    public SpriteCache sprites;
    public NamedModels models;
    
    public Application() {
        sprites = new SpriteCache();
        models = new NamedModels();
    }
}
