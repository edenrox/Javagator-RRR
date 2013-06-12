/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.race.RaceCar;
import com.hopkins.rocknrollracing.state.race.Vector2D;
import com.hopkins.rocknrollracing.state.race.World;
import com.hopkins.rocknrollracing.state.track.Track;
import com.hopkins.rocknrollracing.state.track.TrackPieceType;
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
    
    public void renderCarPositions(Graphics g, int ox, int oy, World theWorld) {
        // Render the car positions
        for (int i = theWorld.RaceCars.size() - 1; i >= 0; i--) {
            RaceCar car = theWorld.RaceCars.get(i);
            int ordinal = car.Color.ordinal();
            Vector2D mp = theWorld.toMapPosition(car.Position);
            int px = ox + mp.X * WIDTH;
            int py = oy + mp.Y * HEIGHT;
            
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
