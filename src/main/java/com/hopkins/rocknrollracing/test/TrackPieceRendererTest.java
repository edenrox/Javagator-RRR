/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.test;

import com.hopkins.rocknrollracing.state.Planet;
import com.hopkins.rocknrollracing.state.track.TileLayer;
import com.hopkins.rocknrollracing.state.track.TiledTrack;
import com.hopkins.rocknrollracing.state.track.Track;
import com.hopkins.rocknrollracing.state.track.TrackIO;
import com.hopkins.rocknrollracing.state.track.TrackPieceTiler;
import com.hopkins.rocknrollracing.views.elements.TrackTileRenderer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author ian
 */
public class TrackPieceRendererTest {
    
    
    public static void main(String[] args) throws Exception {
        BufferedImage bi = new BufferedImage(15 * 32 * 10, 25 * 8 * 10, BufferedImage.TYPE_INT_ARGB);
        
        
        TiledTrack tt = new TiledTrack(TiledTrack.TRACK_TILES_WIDE, TiledTrack.TRACK_TILES_HIGH);
        
        
        Track t = TrackIO.loadTrack(2);
        
        TrackPieceTiler tiler = new TrackPieceTiler(t, tt);
        
        tiler.renderTrack();
        
        
        TrackTileRenderer ttr = new TrackTileRenderer();
        ttr.load(Planet.ChemVI);
        
        
        Graphics g = bi.createGraphics();
        int width = tt.getBG().getWidth();
        int height = tt.getBG().getHeight();
        ttr.renderSection(g, 0, 0, tt.getBG(), 0, 0, width, height);
        ttr.renderSection(g, 0, 0,  tt.getFG(), 0, 0, width, height);
        
        ImageIO.write(bi, "PNG", new File("track-piece.png"));
    }
    
}
