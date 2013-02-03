/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.test;

import com.hopkins.rocknrollracing.state.track.Track;
import com.hopkins.rocknrollracing.state.track.TrackPieceType;
import com.hopkins.rocknrollracing.views.elements.TrackPieceElement;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author ian
 */
public class TrackSheet {
    
    public static final int NUM_TRACKS = 31;
    public static final int IMAGE_WIDTH = 3000;
    public static final int IMAGE_HEIGHT = 800;
    
    
    public static void main(String[] args) throws Exception {
        BufferedImage bi = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        for (int i = 1; i <= NUM_TRACKS; i++) {
            Track t = loadTrack(i);
            renderTrack(g, t);
            ImageIO.write(bi, "PNG", new File(String.format("track-%d.png", i)));
        }
        g.dispose();
    }
    
    protected static void renderTrack(Graphics g, Track t) throws Exception {
        
        TrackPieceElement element = new TrackPieceElement();
        element.load();
        
        g.setColor(new Color(112, 80, 8));
        g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        
        for(int y = 0; y < Track.HEIGHT; y++) {
            for (int x = 0; x < Track.WIDTH; x++) {
                TrackPieceType type = t.getPiece(x, y).getType();
                Point p = getImagePoint(x, y);
                
                element.renderPiece(g, p.x, p.y, type);
            }
        }
    }
    
    protected static Point getImagePoint(int x, int y) {
        Point rv = new Point();
        
        rv.x = IMAGE_WIDTH / 2 - TrackPieceElement.WIDTH / 2 + x * 192 - y * 192;
        rv.y = 48 * y + 48 * x;
        
        return rv;
    }
    
    protected static Track loadTrack(int i) throws Exception {
        Track t = new Track();
        JSONParser parser = new JSONParser();
        
        InputStream is = t.getClass().getClassLoader().getResourceAsStream(String.format("tracks/%d.json", i));
        JSONObject obj = (JSONObject) parser.parse(new InputStreamReader(is));
        t.fromJSON(obj);
        
        return t;
    }
    
}
