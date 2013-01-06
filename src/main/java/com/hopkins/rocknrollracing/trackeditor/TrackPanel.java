/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.trackeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author ian
 */
public class TrackPanel extends JPanel  {
    public static final int TILE_WIDTH = 8;
    public static final int TILE_HEIGHT = 8;
    public static final int SCALE = 2;
    
    protected PieceRenderer pieceRenderer;
    protected Point selectedTile;
    protected Track track;
    protected TileClickListener tcListener;
    
    public TrackPanel(Track trk, PieceRenderer renderer) {
        tcListener = null;
        track = trk;
        selectedTile = new Point(0, 0);
        pieceRenderer = renderer;
        setPreferredSize(new Dimension(Track.WIDTH * TILE_WIDTH * SCALE, Track.HEIGHT * TILE_HEIGHT * SCALE));
        setDoubleBuffered(true);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                onClick(evt);
            }
        });
    }
    
    public void addTileClickListener(TileClickListener listener) {
        tcListener = listener;
    }
    
    public Point getSelectedTile() {
        return selectedTile;
    }
    
    private int inRange(int min, int max, int value) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
    
    public void onClick(MouseEvent event) {

        // select the new tile
        selectedTile.x = inRange(0, Track.WIDTH - 1, event.getX() / (TILE_WIDTH * SCALE));
        selectedTile.y = inRange(0, Track.HEIGHT - 1, event.getY() / (TILE_HEIGHT * SCALE));
        
        if (tcListener != null) {
            tcListener.tileClicked(new TileClickEvent(selectedTile, this));
        }
        
        this.repaint();
    }
   
    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        
        for(int y = 0; y < Track.HEIGHT; y++) {
            for (int x = 0; x < Track.WIDTH; x++) {
                PieceType type = track.getPiece(x, y).getType();
                pieceRenderer.render(g, x * TILE_WIDTH * SCALE, y * TILE_HEIGHT * SCALE, SCALE, type);
            }
        }
        
        g.setColor(Color.red);
        g.drawRect(selectedTile.x * TILE_WIDTH * SCALE, 
                selectedTile.y * TILE_HEIGHT * SCALE,
                TILE_WIDTH * SCALE - 1, 
                TILE_HEIGHT * SCALE - 1);
    }
    
    public static interface TileClickListener {
        public void tileClicked(TileClickEvent event);
    }
    
    public static class TileClickEvent {
        protected Point tile;
        protected TrackPanel source;
        
        public Point getTile() {
            return tile;
        }
        
        public TrackPanel getSource() {
            return source;
        }
        
        public TileClickEvent(Point point, TrackPanel panel) {
            tile = point;
            source = panel;
        }
    }
}
