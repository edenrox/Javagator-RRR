/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.controllers;

import com.hopkins.rocknrollracing.state.CarColor;
import com.hopkins.rocknrollracing.state.CarModel;
import com.hopkins.rocknrollracing.state.Difficulty;
import com.hopkins.rocknrollracing.state.Division;
import com.hopkins.rocknrollracing.state.Drop;
import com.hopkins.rocknrollracing.state.Enemy;
import com.hopkins.rocknrollracing.state.GameState;
import com.hopkins.rocknrollracing.state.HasFace;
import com.hopkins.rocknrollracing.state.PowerUp;
import com.hopkins.rocknrollracing.state.UpgradeState;
import com.hopkins.rocknrollracing.state.UpgradeType;
import com.hopkins.rocknrollracing.state.race.CarRaceItem;
import com.hopkins.rocknrollracing.state.race.Coord;
import com.hopkins.rocknrollracing.state.race.RaceState;
import com.hopkins.rocknrollracing.state.race.Vector3D;
import com.hopkins.rocknrollracing.state.race.World;
import com.hopkins.rocknrollracing.state.track.Track;
import com.hopkins.rocknrollracing.state.track.TrackPieceType;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author ian
 */
public class PreRaceHelper {
    
    public static final String TRACK_PATH = "tracks/%d.json";


    public RaceState setupRaceState(GameState gs) {
        RaceState rs = new RaceState(gs);
        
        rs.getGameState().RaceNumber = 16;
        
        loadTrack(rs);
        calculateWayPoints(rs);
        
        for(Vector3D wp : rs.getWayPoints()) {
            System.out.println(wp.toString());
        }
        
        setupCars(rs);
        addPowerUps(rs);
        addMines(rs);
        
        
        return rs;
    }
    
    protected void loadTrack(RaceState rs) {
        Track track = rs.getTrack();
        
        String trackFile = String.format(TRACK_PATH, rs.getGameState().RaceNumber);
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(trackFile);
        
        track.fromJSON((JSONObject) JSONValue.parse(new InputStreamReader(is)));
    }
    
    protected void setupCars(RaceState rs) {
        Coord start = findStartLine(rs.getTrack());
        GameState gs = rs.getGameState();
        int angle;
        Vector3D inc = new Vector3D();
        Vector3D position = World.fromMapPosition(start);
        UpgradeState rivalUS = gs.getRivalUpgradeState();
        
        if (rs.getTrack().getPiece(start.X, start.Y).getType() == TrackPieceType.StartRight) {
            angle = 0;
            position.Y -= 1.5;
            inc.Y = 1;
        } else {
            angle = 90;
            position.X -= 1.5;
            inc.X = 1;
        }
        
        
        // Player
        gs.Player1.Upgrades.setLevels(UpgradeType.Charges, 4);
        addCar(position, angle, gs.Player1.Model, gs.Player1.Color, rs, gs.Player1.Upgrades, gs.Player1.Hero);
        position.add(inc);
        
        
        // Rival
        addCar(position, angle, gs.Rival.getModel(), CarColor.Purple, rs, rivalUS, gs.Rival);
        position.add(inc);
        
        // Rip
        //addCar(position, angle, gs.Rival.getModel(), CarColor.Gray, rs, rivalUS, Enemy.Rip);
        //position.add(inc);
        
        // Shred
        //addCar(position, angle, gs.Rival.getModel(), CarColor.Orange, rs, rivalUS, Enemy.Shred);
    }
    
    protected CarModel getShredModel(GameState gs) {
        if (gs.Division == Division.A) {
            return gs.Rival.getModel();
        } else {
            return CarModel.All[gs.Rival.getModel().ordinal() - 1];
        }
    }
    
    protected void addCar(Vector3D position, int angle, CarModel model, CarColor color, RaceState rs, UpgradeState us, HasFace character) {
        CarRaceItem cri = new CarRaceItem(model, color, us, character);
        cri.setAngle(angle);
        cri.getPosition().copy(position);
        rs.getCars().add(cri);
        cri.onWayPointComplete(rs);
    }
    
    protected void addPowerUps(RaceState rs) {
        int numPowerups = getNumPowerups(rs.getGameState().GameDifficulty);
        Vector3D pos;
        Track track = rs.getTrack();
        
        for (int i = 0; i < numPowerups; i++) {
            pos = findRandomTrackPosition(track);
            rs.addPowerUp(PowerUp.Money, pos);
            
            pos = findRandomTrackPosition(track);
            rs.addPowerUp(PowerUp.Armor, pos);
        }
    }
    
    protected void addMines(RaceState rs) {
        int numMines = getNumMines(rs.getGameState().GameDifficulty);
        Vector3D pos;
        Track track = rs.getTrack();
        Drop mineType = rs.getGameState().Rival.getModel().getDrop();
        
        for (int i = 0; i < numMines; i++) {
            pos = findRandomTrackPosition(track);
            rs.addDrop(mineType, pos);
        }
    }
    
    protected Vector3D findRandomTrackPosition(Track t) {
        Vector3D rv = new Vector3D();
        
        rv.X = (float) Math.random() * 60;
        rv.Y = (float) Math.random() * 60;
        rv.Z = 0;
        
        return rv;
    }
    
    protected int getNumPowerups(Difficulty difficulty) {
        if (difficulty == Difficulty.Rookie) {
            return 8;
        } else if (difficulty == Difficulty.Veteran) {
            return 4;
        } else /*if (difficulty == Difficulty.Warrior)*/ {
            return 2;
        }
    }
    
    protected int getNumMines(Difficulty difficulty) {
        if (difficulty == Difficulty.Rookie) {
            return 2;
        } else if (difficulty == Difficulty.Veteran) {
            return 4;
        } else /*if (difficulty == Difficulty.Warrior)*/ {
            return 8;
        }
    }
    
    protected void calculateWayPoints(RaceState rs) {
        Coord curr, next = null;

        // Initialize
        Track track = rs.getTrack();
        Direction dir = Direction.Unknown;
        next = findStartLine(track);
        
        Vector3D middleOffset = new Vector3D(0.5f, 1.5f, 0f);
        
        while (true) {
            // add a way point
            curr = next;
            dir = getNextDirection(track, curr, dir);
            Vector3D waypoint = World.fromMapPosition(curr);
            waypoint.add(middleOffset);
            rs.getWayPoints().add(waypoint);
            
            // Find the next way point
            next = findNextWaypoint(rs.getTrack(), curr, dir);
            
            // Exit once complete
            if (track.getPiece(next.X, next.Y).getType().isStart()) {
                break;
            }
        }
        
    }
    
    protected Direction getNextDirection(Track track, Coord curr, Direction dir) {
        TrackPieceType type = track.getPiece(curr.X, curr.Y).getType();
        switch (type) {
            case StartUp:
                return Direction.Up;
            case StartRight:
                return Direction.Right;
            case CornerDownLeft:
                if (dir == Direction.Up) {
                    return Direction.Left;
                } else {
                    return Direction.Down;
                }
            case CornerDownRight:
                if (dir == Direction.Up) {
                    return Direction.Right;
                } else {
                    return Direction.Down;
                }
            case CornerUpRight:
                if (dir == Direction.Down) {
                    return Direction.Right;
                } else {
                    return Direction.Up;
                }
            case CornerUpLeft:
                if (dir == Direction.Down) {
                    return Direction.Left;
                } else {
                    return Direction.Up;
                }
            default: 
                return Direction.Unknown;
        }
    }
    
    protected Coord findNextWaypoint(Track track, Coord start, Direction dir) {
        Coord rv = new Coord();
        rv.copy(start);
        
        while (true) {
            switch (dir) {
                case Up:
                    rv.Y--;
                    break;
                case Down:
                    rv.Y++;
                    break;
                case Left:
                    rv.X--;
                    break;
                case Right:
                    rv.X++;
                    break;
            }
            
            TrackPieceType type = track.getPiece(rv.X, rv.Y).getType();
            if ((type.isCorner()) || (type.isStart())) {
                break;
            }
        }
        return rv;
    }
    
    protected Coord findStartLine(Track track) {
        for(int y = 0; y < Track.HEIGHT; y++) {
            for (int x = 0; x < Track.WIDTH; x++) {
                if (track.getPiece(x, y).getType().isStart()) {
                    return new Coord(x, y);
                }
            }
        }
        throw new RuntimeException("Track has no starting line");
    }
    
    public enum Direction {
        Unknown,
        Up, Right, Down, Left
    }
}
