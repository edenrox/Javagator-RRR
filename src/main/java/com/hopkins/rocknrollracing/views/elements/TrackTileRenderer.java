/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.Planet;
import com.hopkins.rocknrollracing.state.track.TileLayer;
import com.hopkins.rocknrollracing.state.track.TilePiece;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import com.hopkins.rocknrollracing.utils.StringUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class TrackTileRenderer extends AppElement {
    public static final String SPRITE_PATH = "images/track/%s/%s.png";
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 8;
    
    protected BufferedImage[] tileSprites;

    @Override
    public void load() throws Exception {
        // noop
    }
    
    public void load(Planet planet) throws Exception {
        
        // Allocate space for the tile pieces
        TilePiece[] tilePieces = TilePiece.values();
        tileSprites = new BufferedImage[tilePieces.length];
        
        // Load the tile pieces
        for(int i = 0; i < tilePieces.length; i++) {
            String path = String.format(SPRITE_PATH,
                    StringUtils.nameToPath(planet.getName()), tilePieces[i].toString());
            tileSprites[i] = ImageUtils.loadSprite(path);
        }
    }
    
    public void renderSection(Graphics g, int dx, int dy, TileLayer tiles, int sx, int sy, int sw, int sh) {
        
        for(int y = sy; y < sy + sh; y++) {
            for(int x = sx; x < sx + sw; x++) {
                int px = dx + x * TILE_WIDTH;
                int py = dy + y * TILE_HEIGHT;
                TilePiece p = tiles.getTile(x, y);
                boolean hFlip = tiles.isHFlip(x, y);
                int frame = tiles.getFrame(x, y);
                renderTile(g, px, py, p, frame, hFlip);
            }
        }
        
    }
    
    public void renderTile(Graphics g, int x, int y, TilePiece p, int frame, boolean hFlip) {
        if (p == TilePiece.Empty) {
            // short circuit (we don't need to draw the empty piece)
            return;
        }
        
        BufferedImage sprite = tileSprites[p.ordinal()];
        int denom = sprite.getHeight() / TILE_HEIGHT;
        
        // Destination
        int dx1 = x;
        int dy1 = y;
        int dx2 = dx1 + TILE_WIDTH;
        int dy2 = dy1 + TILE_HEIGHT;
        
        // Source
        int sx1 = frame / denom * TILE_WIDTH;
        int sy1 = (frame % denom) * TILE_HEIGHT;        
        int sx2 = sx1 + TILE_WIDTH;
        int sy2 = sy1 + TILE_HEIGHT;
        
        if (hFlip) {
            int swap = sx2;
            sx2 = sx1;
            sx1 = swap;
        }
        
        g.drawImage(sprite, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
    }
    
    
}
