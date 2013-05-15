/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.test;

import com.hopkins.rocknrollracing.state.track.Track;
import com.hopkins.rocknrollracing.state.track.TrackIO;
import com.hopkins.rocknrollracing.views.elements.HudTrackElement;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author ian
 */
public class TrackMiniMapSheet {
    
    public static final int MAP_WIDTH = 64;
    public static final int MAP_HEIGHT = 64;
    public static final int NUM_MAPS = 31;
    public static final int MOSAIC_WIDTH = 4;
    public static final int MOSAIC_HEIGHT = (int) Math.ceil((double)NUM_MAPS / MOSAIC_WIDTH);
    public static final int MAP_PADDING = 16;
    
    
    protected static HudTrackElement trackElement;
        
    public static void renderTrack(Graphics g, int x, int y, Track t) {
        trackElement.renderTrack(g, x, y, t);
    }
    
    public static void main(String[] args) throws Exception {
        trackElement = new HudTrackElement();
        trackElement.load();
        
        int width = (MAP_WIDTH + MAP_PADDING) * MOSAIC_WIDTH + MAP_PADDING;
        int height = (MAP_HEIGHT + MAP_PADDING) * MOSAIC_HEIGHT + MAP_PADDING;
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.setFont(new Font("Arial", Font.BOLD, 12));
        for (int i = 0; i < NUM_MAPS; i++) {
            int x = (i % MOSAIC_WIDTH) * (MAP_WIDTH + MAP_PADDING) + MAP_PADDING;
            int y = (i / MOSAIC_WIDTH) * (MAP_HEIGHT + MAP_PADDING)+ MAP_PADDING;
     
            Track track = TrackIO.loadTrack(i+1);
            renderTrack(g, x, y, track);
            
            String trackName = String.format("Track %d", i+1);
            
            g.setColor(Color.BLUE);
            g.drawString(trackName, x, y - 2);
            
            
        }
        ImageIO.write(bi, "PNG", new File("map-sheet.png"));
    }
    
    
}
