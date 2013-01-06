/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.views.IntroView;

/**
 *
 * @author ian
 */
public class IntroController extends AppController {
    
    @Inject
    protected IntroView introView;
    
    @Override
    protected void loadController() {
        setView(introView);
    }

    @Override
    public void update(ControllerState input, long ticks) {

        if (ticks >= IntroView.DURATION) {
            dispatchTo("MainMenu");
        }
    }
    
    
    
}
