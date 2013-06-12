/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

/**
 *
 * @author ian
 */
public class BoundingCube {
    public Vector3D Minimum;
    public Vector3D Maximum;
    
    public BoundingCube() {
        Minimum = new Vector3D();
        Maximum = new Vector3D();
    }
    
    @Override
    public BoundingCube clone() {
        BoundingCube rv = new BoundingCube();
        rv.Minimum.copyFrom(this.Minimum);
        rv.Maximum.copyFrom(this.Maximum);
        return rv;
    }
    
    public void move(Vector3D delta) {
        Minimum.add(delta);
        Minimum.add(delta);
    }
    
    /**
     * Recalculate the bounding box for an entity
     * @param e the entity to recalculate for
     */
    public void calculate(BaseEntity e) {
        Vector3D halfSize = e.Size.clone();
        halfSize.scale(0.5f);
        
        // Minumum = Origin - Size / 2;
        Minimum.copyFrom(e.Position);
        Minimum.subtract(halfSize);
        
        // Maximum = Origin + Size / 2;
        Maximum.copyFrom(e.Position);
        Maximum.add(halfSize);
    }
    
    /**
     * Does this bounding box collide with another
     * Note: must be axis aligned
     * @param that the bounding box to check against
     * @return true if they collide
     */
    public boolean doesCollide(BoundingCube that) {
        if ( (this.Minimum.X < that.Maximum.X) 
            && (this.Maximum.X > that.Minimum.X)
            && (this.Minimum.Y < that.Maximum.Y)
            && (this.Maximum.Y > that.Minimum.Y)
            && (this.Minimum.Z < that.Maximum.Z)
            && (this.Maximum.Z > that.Minimum.Z))
        {
            return true;
        }
        return false;
    }
}
