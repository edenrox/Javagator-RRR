/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.trackeditor;

import com.hopkins.rocknrollracing.state.Planet;
import com.hopkins.rocknrollracing.state.track.TiledTrack;
import com.hopkins.rocknrollracing.state.track.TrackPiece;
import com.hopkins.rocknrollracing.state.track.TrackPieceTiler;
import com.hopkins.rocknrollracing.state.track.TrackPieceType;
import com.hopkins.rocknrollracing.views.elements.TrackTileRenderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
/**
 *
 * @author ian
 */
public class PreviewPanel extends JPanel {
    public static final Color BACKGROUND_COLOR = new Color(112, 80, 8);
    
    
    protected TrackTileRenderer renderer;
    protected TrackPiece piece;
    protected TiledTrack tiledTrack;
    protected TrackPieceTiler trackTiler;
    

    public void setPiece(TrackPiece value) {
        piece = value;
        tiledTrack.reset();
        trackTiler.renderPiece(piece, 0, 0);
        repaint();
    }
    public TrackPiece getPiece() {
        return piece;
    }
    
    
    
    public PreviewPanel() {
        setPreferredSize(new Dimension(13 * 32, 30 * 8));
        setDoubleBuffered(true);
        piece = new TrackPiece();
        piece.setType(TrackPieceType.Empty);
        
        tiledTrack = new TiledTrack(40, 40);
        trackTiler = new TrackPieceTiler(null, tiledTrack);
        renderer = new TrackTileRenderer();
        try {
            renderer.load();
            renderer.load(Planet.ChemVI);
        } catch (Exception ex) {
            System.err.println("Error loading renderer: " + ex.toString());
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        
        renderer.renderSection(g, 0, 0, tiledTrack.getBG(), 0, 0, 20, 30); //bg
        renderer.renderSection(g, 0, 0, tiledTrack.getFG(), 0, 0, 20, 30); //fg
    }
    
    
}
