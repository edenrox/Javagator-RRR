/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.track;

/**
 *
 * @author ian
 */
public class TiledPiece {
    public static final int PIECE_TILES_WIDE = 15;
    public static final int PIECE_TILES_HIGH = 25;
    
    protected TileLayer bg;
    protected TileLayer fg;
    
    public TileLayer getBG() {
        return bg;
    }
    public TileLayer getFG() {
        return fg;
    }
    
    public TiledPiece() {
        bg = new TileLayer(PIECE_TILES_WIDE, PIECE_TILES_HIGH);
        fg = new TileLayer(PIECE_TILES_WIDE, PIECE_TILES_HIGH);
        
        reset();
    }
    
    public final  void reset() {
        // reset both layers
        bg.reset(TilePiece.Background, 0, false);
        fg.reset(TilePiece.Empty, 0, false);
    }
    
    
}
