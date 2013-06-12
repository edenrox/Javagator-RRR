/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.engine.EnemyIntent;
import com.hopkins.rocknrollracing.engine.IntentHandler;
import com.hopkins.rocknrollracing.engine.Physics;
import com.hopkins.rocknrollracing.engine.Spawn;
import com.hopkins.rocknrollracing.engine.UserIntent;
import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.race.RaceCarIntent;
import com.hopkins.rocknrollracing.state.race.RaceCarPlace;
import com.hopkins.rocknrollracing.state.race.World;
import com.hopkins.rocknrollracing.state.track.Track;
import com.hopkins.rocknrollracing.state.track.TrackIO;
import com.hopkins.rocknrollracing.views.RaceView;

/**
 *
 * @author ian
 */
public class RaceController extends AppController {
    
    @Inject
    protected RaceView theView;
    
    protected World theWorld;
    
    
    protected IntentHandler intentHandler;
    protected RaceCarIntent[] intents;
    protected Physics physics;

    @Override
    protected void loadController() {
        Track track = null;
        try {
             track = TrackIO.loadTrack(1);
        } catch (Exception ex) {
            throw new RuntimeException("Error loading track", ex);
        }
        
        // Instantiate the world
        theWorld = new World(track);
        physics = new Physics();
        
        // Do some initial spawning
        Spawn.raceCarsAtStartLine(theWorld, gameState);
        Spawn.randomPowerUpsOnTrack(theWorld, gameState.getNumPowerUps());
        
        // Setup the view
        theView.theWorld = theWorld;
        setView(theView);
        
        // setup the intent
        intentHandler = new IntentHandler();
        intents = new RaceCarIntent[World.NUM_CARS];
        for(int i = 0; i < intents.length; i++) {
            intents[i] = new RaceCarIntent();
        }
    }

    @Override
    public void update(ControllerState input, long ticks) {
        
        // iterate over the cars
        for(int i = 0; i < theWorld.RaceCars.size(); i++) {
            RaceCarIntent item = intents[i];
            
            // reset the intent
            item.reset();
            
            // calculate the intent
            if (i < 1) {
                UserIntent.calculateIntent(input, item);
            } else {
                EnemyIntent.calculateIntent(theWorld, i, item);
            }
            
            // handle the intent
            intentHandler.handleIntent(item, i, theWorld);
        }
        
        // Physics
        physics.moveEntities(theWorld);
        physics.detectCollisions(theWorld);
        physics.filterEntities(theWorld);
        
        // Calculate positions
        RaceCarPlace.calculate(theWorld.RaceCars);
        
        
    }
    
    
    
    
    
    
    
    
    
    
}
