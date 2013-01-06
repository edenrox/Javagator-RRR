/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.animation;

/**
 *
 * @author ihopkins
 */
public class KeyFrame {
    public int frame;
    public int x;
    public int y;
    public Easing easing;
    
    public KeyFrame(int x, int y, int frame, Easing easing) {
        this.x = x;
        this.y = y;
        this.frame = frame;
        this.easing = easing;
    }
}
