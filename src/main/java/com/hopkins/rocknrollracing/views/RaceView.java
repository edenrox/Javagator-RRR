/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.CarState;
import com.hopkins.rocknrollracing.state.Drop;
import com.hopkins.rocknrollracing.state.PowerUp;
import com.hopkins.rocknrollracing.state.Weapon;
import com.hopkins.rocknrollracing.state.race.CarRaceItem;
import com.hopkins.rocknrollracing.state.race.Coord;
import com.hopkins.rocknrollracing.state.race.RaceItem;
import com.hopkins.rocknrollracing.state.race.RaceState;
import com.hopkins.rocknrollracing.state.race.Vector3D;
import com.hopkins.rocknrollracing.state.race.World;
import com.hopkins.rocknrollracing.state.track.TrackPiece;
import com.hopkins.rocknrollracing.state.track.TrackPieceType;
import com.hopkins.rocknrollracing.views.elements.*;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author ian
 */
public class RaceView extends AppView {
    
    public static final Color BACKGROUND_COLOR = new Color(112, 80, 8);
    public static final Color GRID_COLOR = new Color(72, 0, 0);
    public static final boolean DEBUG_ENABLED = false;
    public static final float MAX_CAMERA_ACCEL = 0.02f;
    
    @Inject
    protected HudElement hud;
    
    @Inject
    protected CarElement car;
    
    @Inject
    protected WeaponElement weapon;
    
    @Inject
    protected TrackPieceElement track;
    
    @Inject
    protected PowerUpElement powerUp;
    
    @Inject
    protected RaceDebugElement debug;
    
    @Inject
    protected EffectsElement carEffects;
    
    protected int lastAngle = 0;
    
    public RaceState RaceState;

    @Override
    protected void loadView() throws Exception {
        // noop
    }

    @Override
    public void render(Graphics g, long ticks) {
        
        // Get the camera position
        Vector3D cameraPos = getCameraPosition(RaceState.getCars().get(0));
        
        // Render the track
        renderTrack(g, cameraPos, ticks);
        
        // Draw the projectiles
        renderProjectiles(g, cameraPos, ticks);
        
        // Draw the cars
        renderCars(g, cameraPos, ticks);
        
        
        // render the HUD on top of everything
        hud.renderHud(g, RaceState, ticks);
        
        if (DEBUG_ENABLED) {
            debug.render(g, RaceState.getCars().get(0));
        }
    }
    
    protected Vector3D getCameraPosition(CarRaceItem cri) {
        // Calculate the desired camera position
        Vector3D cameraPos = World.getCameraPosition(cri);
        
        float ratio = Math.min(1, cri.getVelocity().getMagnitude() / cri.getTopSpeed());
        cameraPos.add(1.0f * ratio, cri.getAngle());
        
        // Save the camera position for next time
        return cameraPos;
    }
    
    protected void renderTrack(Graphics g, Vector3D cameraPos, long ticks) {
        Coord c = World.toMapPosition(cameraPos);
        
        // We need to clear the background;
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
        
        // We need to render potentially 9 track pieces
        int y_min = Math.max(0, c.Y - 2);
        int y_max = Math.min(7, c.Y + 2);
        int x_min = Math.max(0, c.X - 2);
        int x_max = Math.min(7, c.X + 2);
        
        // Render each track piece
        for (c.Y = y_min; c.Y <= y_max; c.Y++) {
            for (c.X = x_min; c.X <= x_max; c.X++) {
                TrackPiece piece = RaceState.getTrack().getPiece(c.X, c.Y);
                if (piece.getType() != TrackPieceType.Empty) {
                    Vector3D piecePos = World.fromMapPosition(c);
                    Coord sp = World.toScreenPosition(piecePos, cameraPos);
                    track.renderPiece(g, sp.X, sp.Y, piece.getType());
                    
                    // debug rectangle
                    if (DEBUG_ENABLED) {
                        Coord p1 = World.toScreenPosition(piecePos, cameraPos);
                        piecePos.X += 6;
                        Coord p2 = World.toScreenPosition(piecePos, cameraPos);
                        piecePos.Y += 6;
                        Coord p3 = World.toScreenPosition(piecePos, cameraPos);
                        piecePos.X -= 6;
                        Coord p4 = World.toScreenPosition(piecePos, cameraPos);

                        g.setColor(Color.CYAN);
                        g.drawLine(p1.X, p1.Y, p2.X, p2.Y);
                        g.drawLine(p2.X, p2.Y, p3.X, p3.Y);
                        g.drawLine(p3.X, p3.Y, p4.X, p4.Y);
                        g.drawLine(p4.X, p4.Y, p1.X, p1.Y);
                    }
                }
                
                
            }
        }
    }
    
    protected void renderProjectiles(Graphics g, Vector3D cameraPos, long ticks) {
        
        // render the projectiles and items
        for(RaceItem ri : RaceState.getItems()) {
            
            // Clip race items that aren't within the camer'as clip area
            if (!World.isWithinCameraClip(ri.getPosition(), cameraPos)) {
                //break;
            }
            
            Coord c = World.toScreenPosition(ri.getPosition(), cameraPos);
            switch (ri.getType()) {
                case Projectile:
                    weapon.renderProjectile(g, c.X, c.Y, World.toScreenAngle(ri.getAngle()), (Weapon) ri.getObject());
                    if (ri.getObject().getClass() == Weapon.Missile.getClass()) {
                        // add the smoke effect
                    }
                    break;
                case Drop:
                    weapon.renderDrop(g, c.X, c.Y, (Drop) ri.getObject());
                    break;
                case PowerUp:
                    powerUp.renderPowerUp(g, c.X, c.Y, (PowerUp) ri.getObject());
                    break;
            }
        }
    }
    
    protected void renderCars(Graphics g, Vector3D cameraPos, long ticks) {
        // render the cars
        CarState cs = new CarState();
        for(CarRaceItem cri : RaceState.getCars()) {
            
            // Clip cars that aren't within the camera's clip area
            if (!World.isWithinCameraClip(cri.getPosition(), cameraPos)) {
                //break;
            }
            
            // Figure out the car's position and render the car
            Coord c = World.toScreenPosition(cri.getPosition(), cameraPos);
            cs.x = c.X;
            cs.y = c.Y;
            cs.z = 0;
            cs.trackZ = 0;
            cs.color = cri.getColor();
            cs.model = cri.getModel();
            cs.frame = CarState.getFrameFromAngle(World.toScreenAngle(cri.getAngle()));
            if (cri.isMoving()) {
                cs.wheelPosition = (int) (ticks / 15 % 3);
            } else {
                cs.wheelPosition = 0;
            }
            car.render(g, cs);
            
            if (cri.isNitro()) {
                //carEffects.renderNitro(g, );
            }
            
            if (cri.isSmoking()) {
                Coord cSmokePos;
                int frame;
                Vector3D smokePos = new Vector3D();
                smokePos.copy(cri.getPosition());
                
                // the first smoke position
                frame = (int) (ticks / 15) % 2;
                smokePos.Z += 0.25f;
                smokePos.subtract(cri.getVelocity());
                cSmokePos = World.toScreenPosition(smokePos, cameraPos);
                carEffects.renderSmoke(g, cSmokePos.X, cSmokePos.Y, frame);
                
                // the second smoke position
                smokePos.Z += 0.25f;
                smokePos.subtract(cri.getVelocity());
                cSmokePos = World.toScreenPosition(smokePos, cameraPos);
                carEffects.renderSmoke(g, cSmokePos.X, cSmokePos.Y, frame);
            }
        }
    }

}
