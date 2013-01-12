/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.trackeditor;

import com.hopkins.rocknrollracing.state.track.TrackPiece;
import com.hopkins.rocknrollracing.state.track.TrackPieceType;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author ian
 */
public class PieceRenderer {
    public static final int HUD_TILE_WIDTH = 8;
    public static final int HUD_TILE_HEIGHT = 8;
    public static final int TRACK_TILE_OFFSET_HEIGHT = 80;
    
    protected BufferedImage hudTiles;
    
    public PieceRenderer() {
        loadHudTiles();
    }
    
    private void loadHudTiles() {
        try {
            InputStream tilesIS = this.getClass().getClassLoader().getResourceAsStream("images/hud/tiles.png");
            hudTiles = ImageIO.read(tilesIS);
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString());
        }
    } 
    
    public void render(Graphics g, int dx, int dy, float scale, TrackPieceType type) {
        int index = type.ordinal() - 1;
        if (index < 0) {
            return;
        }
        
        int sx = (index % 4) * HUD_TILE_WIDTH;
        int sy = TRACK_TILE_OFFSET_HEIGHT + (index / 4) * HUD_TILE_HEIGHT;
        
        g.drawImage(hudTiles,
                dx, dy, (int) (dx + HUD_TILE_WIDTH * scale), (int) (dy + HUD_TILE_HEIGHT * scale),
                sx, sy, sx + HUD_TILE_WIDTH, sy + HUD_TILE_HEIGHT,
                null);
    }
}
