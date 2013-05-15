/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.track;

import com.hopkins.rocknrollracing.test.TrackPieceRendererTest;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author ian
 */
public class TrackIO {
    
    public static final String TRACK_PATH = "tracks/%d.json";
    
    public static Track loadTrack(int trackNumber) throws IOException {
        String path = String.format(TRACK_PATH, trackNumber);
        try {
            InputStream is = Track.class.getClassLoader().getResourceAsStream(path);
            return loadTrack(is);
        } catch (Exception ex) {
            throw new IOException("Error reading track file: " + path + " (resource)", ex);
        }
    }
    
    public static Track loadTrack(String filename) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(filename);
            return loadTrack(fis);
        } catch (Exception ex) {
            throw new IOException("Error reading track file: " + filename, ex);
        }
    }
    
    public static Track loadTrack(InputStream is) throws Exception {
        Track rv = new Track();
        
        // Read and parse the track data
        InputStreamReader isr = new InputStreamReader(is);
        JSONObject jsonTrack = (JSONObject) JSONValue.parse(isr);
        rv.fromJSON(jsonTrack);
        
        return rv;
    }
    
    public static void saveTrack(String filename, Track toSave) throws IOException {
        JSONObject jso = toSave.toJSON();
        try {
            FileWriter fw = new FileWriter(filename);
            fw.append(jso.toString());
            fw.close();
        } catch (IOException ex) {
            throw new IOException("Error saving track to file: " + filename, ex);
        }
    }
    
    
    
}
