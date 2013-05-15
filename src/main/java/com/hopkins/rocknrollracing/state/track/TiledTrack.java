/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.track;

/**
 *
 * @author ian
 */
public class TiledTrack {
    public static final int TRACK_TILES_WIDE = 120;
    public static final int TRACK_TILES_HIGH = 120;
    
    protected TileLayer bg;
    protected TileLayer fg;
    
    public TileLayer getBG() {
        return bg;
    }
    public TileLayer getFG() {
        return fg;
    }
    
    public TiledTrack(int width, int height) {
        bg = new TileLayer(width, height);
        fg = new TileLayer(width, height);
        
        reset();
    }
    
    public final  void reset() {
        // reset both layers
        bg.reset(TilePiece.Background, 0, false);
        fg.reset(TilePiece.Empty, 0, false);
    }
    
}
