/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.inject.InjectLoadable;
import com.hopkins.rocknrollracing.inject.InjectUtils;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author ian
 */
public abstract class AppView implements InjectLoadable {
    
    @Override
    public final void load() throws Exception {
        loadInject();
        loadView();
    }
    
    protected void loadInject() throws Exception {
        InjectUtils.injectAll(this);
    }
    
    protected abstract void loadView() throws Exception;        
    
    public abstract void render(Graphics g, long ticks);
    
    protected void clear(Graphics g, Color c) {
        g.setColor(c);
        g.fillRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
    }
}
