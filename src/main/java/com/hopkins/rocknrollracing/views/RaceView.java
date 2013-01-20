/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.CarState;
import com.hopkins.rocknrollracing.state.Drop;
import com.hopkins.rocknrollracing.state.Weapon;
import com.hopkins.rocknrollracing.state.race.CarRaceItem;
import com.hopkins.rocknrollracing.state.race.RaceItem;
import com.hopkins.rocknrollracing.state.race.RaceState;
import com.hopkins.rocknrollracing.views.elements.*;
import java.awt.Graphics;

/**
 *
 * @author ian
 */
public class RaceView extends AppView {
    
    @Inject
    protected HudElement hud;
    
    @Inject
    protected CarElement car;
    
    @Inject
    protected WeaponElement weapon;
    
    @Inject
    protected TrackPieceElement track;
    
    @Inject
    protected RaceDebugElement debug;
    
    public RaceState RaceState;

    @Override
    protected void loadView() throws Exception {
        // noop
    }

    @Override
    public void render(Graphics g, long ticks) {
        // render the track
        //track.renderAll(g);
        
        // render the projectiles and items
        for(RaceItem ri : RaceState.getItems()) {
            int x = (int) ri.getPosition().X;
            int y = (int) ri.getPosition().Y;
            switch (ri.getType()) {
                case Projectile:
                    weapon.renderProjectile(g, x, y, ri.getAngle(), (Weapon) ri.getObject());
                    break;
                case Drop:
                    weapon.renderDrop(g, x, y, (Drop) ri.getObject());
                    break;
            }
        }
        
        // render the cars
        CarState cs = new CarState();
        for(CarRaceItem cri : RaceState.getCars()) {
            cs.x = (int) cri.getPosition().X;
            cs.y = (int) cri.getPosition().Y;
            cs.z = 0;
            cs.trackZ = 0;
            cs.color = cri.getColor();
            cs.model = cri.getModel();
            cs.frame = CarState.getFrameFromAngle(cri.getAngle());
            if (cri.isMoving()) {
                cs.wheelPosition = (int) (ticks / 15 % 3);
            } else {
                cs.wheelPosition = 0;
            }
            car.render(g, cs);
        }
        
        // render the HUD on top of everything
        hud.renderHud(g, RaceState, ticks);
        
        debug.render(g, RaceState.getCars().get(0));
    }

}
