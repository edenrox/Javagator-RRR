/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.inject.InjectUtils;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.GameState;
import com.hopkins.rocknrollracing.views.AppView;
import com.hopkins.rocknrollracing.views.NullView;

/**
 *
 * @author ian
 */
public class AppController {
    protected AppView view;
    protected GameState gameState;
    protected String dispatchTo;
    
    public AppView getView() {
        return view;
    }
    public void setView(AppView value) {
        view = value;
    }
    public String getDispatchTo() {
        return dispatchTo;
    }
    
    
    
    public final void load(GameState gs) {
        gameState = gs;
        loadVariables();
        loadInject();
        loadController();
    }
    
    protected final void loadVariables() {
        dispatchTo = null;
        view = new NullView();
    }
    
    protected final void loadInject() {
        InjectUtils.injectAll(this);
    }
    
    protected void loadController() {
        
    }
   
    
    public void update(ControllerState input, long ticks) {
        
    }
    
    public void dispose() {
        
    }
    
    public void dispatchTo(String controllerName) {
        dispatchTo = controllerName;
    }
}
