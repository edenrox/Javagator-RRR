/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.CarState;
import com.hopkins.rocknrollracing.state.Drop;
import com.hopkins.rocknrollracing.state.Planet;
import com.hopkins.rocknrollracing.state.PowerUp;
import com.hopkins.rocknrollracing.state.Weapon;
import com.hopkins.rocknrollracing.state.race.Angle3D;
import com.hopkins.rocknrollracing.state.race.Entity;
import com.hopkins.rocknrollracing.state.race.RaceCar;
import com.hopkins.rocknrollracing.state.race.Vector3D;
import com.hopkins.rocknrollracing.state.race.World;
import com.hopkins.rocknrollracing.state.track.TiledTrack;
import com.hopkins.rocknrollracing.state.track.TrackPieceTiler;
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
    public static final float MAX_CAMERA_LEAD_CHANGE = 0.01f;
    public static final float MAX_CAMERA_LEAD = 1.5f;
    
    protected Vector3D prevCameraLead;
    
    @Inject
    protected HudElement hud;
    
    @Inject
    protected CarElement car;
    
    @Inject
    protected WeaponElement weapon;
    
    @Inject
    protected TrackTileRenderer track;
    
    @Inject
    protected PowerUpElement powerUp;
    
    
    @Inject
    protected EffectsElement carEffects;
    
    protected Vector3D cameraPosition;
    protected TiledTrack tiledTrack;
    
    public World theWorld;

    @Override
    protected void loadView() throws Exception {
        
        cameraPosition = new Vector3D();
        
        tiledTrack = null;
        
        track.load(Planet.ChemVI);
    }

    

    @Override
    public void render(Graphics g, long ticks) {
        
        // Position the camera
        positionCamera();
        
        // Render the track
        renderTrack(g);
        
        // Render the drops & powerups
        renderDropsAndPowerUps(g);
        
        // Render the cars and effects
        renderCars(g, ticks);
        
        // Render the weapons
        renderWeapons(g);
        
        // Render the HUD
        hud.renderHud(g, theWorld, ticks);
    }
    
    protected void positionCamera() {
        // for now, we'll just position the camera on the user's car
        cameraPosition.copyFrom(theWorld.RaceCars.get(0).Position);
    }
    
    protected void renderTrack(Graphics g) {
       if (tiledTrack == null) {
           // initialize the tiled track
           initializeTiledTrackForRendering();
       }
       
       // calculate the map positions
       int sx = 0;
       int sy = 0;
       int sw = 0;
       int sh = 0;
       
       // render the background
       track.renderSection(g, Screen.WIDTH, Screen.HEIGHT, tiledTrack.getBG(), sx, sy, sw, sh);
       
       // render the foreground
       track.renderSection(g, Screen.WIDTH, Screen.HEIGHT, tiledTrack.getFG(), sx, sy, sw, sh);
    }
    
    protected void initializeTiledTrackForRendering() {
        tiledTrack = new TiledTrack(TiledTrack.TRACK_TILES_WIDE, TiledTrack.TRACK_TILES_HIGH);
        
        // create a map of the tiles we should render
        TrackPieceTiler tpt = new TrackPieceTiler(theWorld.Track, tiledTrack);
        tpt.renderTrack();
    }
    
    protected void renderDropsAndPowerUps(Graphics g) {
        for(Entity e : theWorld.Entities) {
            int x = 0;
            int y = 0;
            if (e.Type.getClass() == PowerUp.class) {
                powerUp.renderPowerUp(g, x, y, (PowerUp) e.Type);
            } else if (e.Type.getClass() == Drop.class) {
                weapon.renderDrop(g, x, y, (Drop) e.Type);
            }
        }
    }
    
    protected void renderCars(Graphics g, long ticks) {
        CarState cs = new CarState();
        for(RaceCar rc : theWorld.RaceCars) {
            
            cs.x = 0;
            cs.y = 0;
            cs.z = 0;
            cs.color = rc.Color;
            cs.frame = CarState.getFrameFromAngle(rc.Angle.getYaw());
            cs.pitch = rc.Angle.getPitch();
            cs.model = rc.Model;
            cs.wheelPosition = 1;
            cs.trackZ = 0;
                    
            car.render(g, cs);
        }
    }
    
    protected void renderWeapons(Graphics g) {
        for(Entity e : theWorld.Entities) {
            if (e.Type.getClass() == Weapon.class) {
                int x = 0;
                int y = 0;
                weapon.renderProjectile(g, x, y, e.Angle.getYaw(), (Weapon) e.Type);
            }
        }
    }
    
   

}
