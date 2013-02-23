/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

/**
 *
 * @author ian
 */
public class Coord {
    public int X;
    public int Y;
    
    public Coord() {
        set(0, 0);
    }
    
    public Coord(int x, int y) {
        set(x, y);
    }
    
    public final void set(int x, int y) {
        X = x;
        Y = y;
    }
    
    public void copy(Coord that) {
        this.X = that.X;
        this.Y = that.Y;
    }
    
    @Override
    public String toString() {
        return String.format("[%d, %d]", X, Y);
    }
}
