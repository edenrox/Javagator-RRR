/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.trackeditor;

import com.hopkins.rocknrollracing.state.track.TrackPieceType;
import com.hopkins.rocknrollracing.views.elements.TrackPieceElement;
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
    
    
    protected TrackPieceElement renderer;
    protected TrackPieceType type;
    

    public void setType(TrackPieceType value) {
        type = value;
        repaint();
    }
    public TrackPieceType getType() {
        return type;
    }
    
    
    
    public PreviewPanel() {
        setPreferredSize(new Dimension(TrackPieceElement.WIDTH, TrackPieceElement.HEIGHT));
        setDoubleBuffered(true);
        type = TrackPieceType.Empty;
        renderer = new TrackPieceElement();
        try {
            renderer.load();
        } catch (Exception ex) {
            System.err.println("Error loading renderer: " + ex.toString());
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        renderer.renderPiece(g, getWidth() / 2, 8, type);
    }
    
    
}
