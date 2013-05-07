/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.track;

/**
 *
 * @author ian
 */
public class TileLayer {
    public static final TilePiece[] PieceLookUp = TilePiece.values();
    
    protected int[] tiles;
    protected int width;
    protected int height;
    
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    
    public int getIndex(int x, int y) {
        return y * width + x;
    }
    
    protected int getValue(int x, int y) {
        int index = getIndex(x, y);
        if (index > tiles.length) {
            return 0;
        } else {
            return tiles[index];
        }
    }
    
    // the higher 3 bytes are the tile index
    public TilePiece getTile(int x, int y) {
        int value = getValue(x, y);
        
        return PieceLookUp[value >> 8];
    }
    
    // The first bit is whether the tile is Horiztonally flipped
    public boolean isHFlip(int x, int y) {
        int value = getValue(x, y);
        return ((value & 0x1) > 0);
    }
    // The rest of the first byte is the frame offset
    public int getFrame(int x, int y) {
        int value = getValue(x, y);
        return ((value & 0xFE) >> 1);
    }
    
    protected int calculateValue(TilePiece p, int frame, boolean isHFlip) {
        int value = p.ordinal() << 8;
        value += (frame & 0x8F) << 1;
        if (isHFlip) {
            value += 1;
        }
        return value;
    }
    
    public void setTile(int x, int y, TilePiece p, int frame, boolean isHFlip) {
        int index = getIndex(x, y);
        tiles[index] = calculateValue(p, frame, isHFlip);
    }
    
    public TileLayer(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new int[width * height];
    }
    
    public void reset(TilePiece p, int frame, boolean isHFlip) {
        int value = calculateValue(p, frame, isHFlip);
        for(int i = 0; i < tiles.length; i++) {
            tiles[i] = value;
        }
    }
}
