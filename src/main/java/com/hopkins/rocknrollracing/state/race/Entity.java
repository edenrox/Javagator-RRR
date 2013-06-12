/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import com.hopkins.rocknrollracing.state.Drop;
import com.hopkins.rocknrollracing.state.Weapon;

/**
 *
 * @author ian
 */
public class Entity extends BaseEntity {
    public static int CREATED_BY_SYSTEM = -1;
    
    public Object Type;
    public EntityState State;
    public int CreatedBy;

    @Override
    public void incrementPosition() {
        
        // For plasma, we have to recalculate the velocity based on our target's location
        if (Type == Weapon.Plasma) {
            
        }
        
        // For a scatter pack, we need to have it split into 5 scatter packs
        if (Type == Drop.Scatterpack) {
            
        }
        
        super.incrementPosition();
    }
    
    
    
    
}
