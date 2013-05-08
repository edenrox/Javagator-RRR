/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.test;

import com.hopkins.rocknrollracing.state.Planet;
import com.hopkins.rocknrollracing.state.track.TileLayer;
import com.hopkins.rocknrollracing.state.track.TilePiece;
import com.hopkins.rocknrollracing.state.track.TiledPiece;
import com.hopkins.rocknrollracing.state.track.TiledTrack;
import com.hopkins.rocknrollracing.state.track.Track;
import com.hopkins.rocknrollracing.state.track.TrackPiece;
import com.hopkins.rocknrollracing.state.track.TrackPieceTiler;
import com.hopkins.rocknrollracing.state.track.TrackPieceType;
import com.hopkins.rocknrollracing.views.elements.TrackTileRenderer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author ian
 */
public class TrackPieceRendererTest {
    
    public static Track loadTrack(int number) throws Exception {
        Track rv = new Track();
        
        // Open the track file
        String filename = String.format("tracks/%d.json", number);
        InputStream is = TrackPieceRendererTest.class.getClassLoader().getResourceAsStream(filename);
        InputStreamReader isr = new InputStreamReader(is);
        // Read the track data
        JSONObject jsonTrack = (JSONObject) JSONValue.parse(isr);
        // Parse the track data
        rv.fromJSON(jsonTrack);
        // return the track
        return rv;
    }
    
    public static void main(String[] args) throws Exception {
        BufferedImage bi = new BufferedImage(15 * 32 * 10, 25 * 8 * 10, BufferedImage.TYPE_INT_ARGB);
        
        
        TiledTrack tt = new TiledTrack();
        
        TileLayer bg = tt.getBG();
        TileLayer fg = tt.getFG();
        
        
        
        
        Track t = loadTrack(2);
        
        TrackPieceTiler tiler = new TrackPieceTiler(t, fg, bg);
        
        tiler.renderTrack();
        
        
        TrackTileRenderer ttr = new TrackTileRenderer();
        ttr.load(Planet.ChemVI);
        
        
        Graphics g = bi.createGraphics();
        ttr.renderSection(g, 0, 0, bg, 0, 0, bg.getWidth(), bg.getHeight());
        ttr.renderSection(g, 0, 0, fg, 0, 0, fg.getWidth(), fg.getHeight());
        
        ImageIO.write(bi, "PNG", new File("track-piece.png"));
    }
    
}
