/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.race.CarRaceItem;
import com.hopkins.rocknrollracing.state.race.Coord;
import com.hopkins.rocknrollracing.state.race.RaceState;
import com.hopkins.rocknrollracing.state.race.World;
import com.hopkins.rocknrollracing.state.track.Track;
import com.hopkins.rocknrollracing.state.track.TrackPieceType;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class HudTrackElement extends AppElement  {
    
    public static final String SPRITE_PATH = "images/hud/%s.png";
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    
    protected BufferedImage track, colors;
    protected float scale;

    public float getScale() {
        return scale;
    }
    public void setScale(float value) {
        scale = value;
    }
    
    
    
    
    @Override
    public void load() throws Exception {
        track = loadSprite(SPRITE_PATH, "track");
        colors = loadSprite(SPRITE_PATH, "colors");
        scale = 1.0f;
    }
    
    public void renderCarPositions(Graphics g, int ox, int oy, RaceState rs) {
        // Render the car positions
        for (int i = rs.getCars().size() - 1; i >= 0; i--) {
            CarRaceItem cri = rs.getCars().get(i);
            int ordinal = cri.getColor().ordinal();
            Coord c = World.toMapPosition(cri.getPosition());
            int px = ox + c.X * WIDTH;
            int py = oy + c.Y * HEIGHT;
            
            SpriteRenderer.render(g, colors, px, py, WIDTH, HEIGHT, ordinal, false, false, scale);
        }
    }
    
    public void renderTrack(Graphics g, int ox, int oy, Track t) {
        // Render the track
        for(int y = 0; y < Track.HEIGHT; y++) {
            for (int x = 0; x < Track.WIDTH; x++) {
                TrackPieceType type = t.getPiece(x, y).getType();
                int px = ox + (int) Math.round(x * WIDTH * scale);
                int py = oy + (int) Math.round(y * HEIGHT * scale);
                renderPiece(g, px, py, type);

            }
        }
    }
    
    public void renderPiece(Graphics g, int x, int y, TrackPieceType type) {
        int ordinal = type.ordinal();
        SpriteRenderer.render(g, track, x, y, WIDTH, HEIGHT, ordinal, false, false, scale);
    }
    
    
    
}
