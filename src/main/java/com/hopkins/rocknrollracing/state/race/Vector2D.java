/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

/**
 *
 * @author ian
 */
public class Vector2D {
    public int X;
    public int Y;
    
    public Vector2D() {
        reset(0, 0);
    }
    public Vector2D(int x, int y) {
        reset(x, y);
    }
    public final void reset(int x, int y) {
        X = x;
        Y = y;
    }
    
    @Override
    public boolean equals(Object that) {
        if ((that == null) || (that.getClass() != Vector2D.class)) {
            return false;
        }
        Vector2D vThat = (Vector2D) that;
        return (this.X == vThat.X)
                && (this.Y == vThat.Y);
    }
}
