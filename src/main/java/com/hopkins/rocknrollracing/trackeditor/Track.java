/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.trackeditor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ian
 */
public class Track {
    public static final float VERSION = 1.0f;
    public static final float EPSILON = 0.001f;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    
    protected Piece[] pieces;
    protected String notes;
    
    public String getNotes() {
        return notes;
    }
    public void setNotes(String value) {
        notes = value;
    }
    
    public Track() {
        // initialize the pieces
        pieces = new Piece[WIDTH * HEIGHT];
        clear();
    }
    
    public final void clear() {
        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = new Piece();
        }
        notes = "";
    }
    
    public Piece getPiece(int x, int y) {
        if ((x < 0) || (y < 0) || (x >= WIDTH) || (y >= HEIGHT)) {
            throw new IndexOutOfBoundsException(String.format(
                    "Index passed in was out of bounds: {x: %d, y: %d, w: %d, h: %d}",
                    x, y, WIDTH, HEIGHT));
        }
        return pieces[y * WIDTH + x];
    }
    
    public void validate() {
        int numStart = 0;
        
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                // 
                
                Piece p = getPiece(x, y);
                PieceType type = p.getType();
                if ((type == PieceType.StartRight) ||
                        (type == PieceType.StartUp)) {
                    numStart++;
                }
            }
        }
        
        if (numStart != 1) {
            throw new TrackValidationException(String.format(
                    "Incorrect number of start pieces: %d",
                    numStart));
        }
        
        
    }
    
    public static class TrackValidationException extends RuntimeException {
        public TrackValidationException(String message) {
            super(message);
        }
    }
    
    protected JSONArray getPiecesJSON() {
        JSONArray arr = new JSONArray();
        for (int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {
                arr.add(getPiece(x, y).toJSON());
            }
        }
        return arr;
    }
    
    protected String getCreatedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(Calendar.getInstance().getTime());
    }
    
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        
        obj.put("created", getCreatedDate());
        obj.put("version", VERSION);
        obj.put("width", WIDTH);
        obj.put("height", HEIGHT);
        obj.put("notes", notes);
        obj.put("pieces", getPiecesJSON());
        
        return obj;
    }
    
    protected void setPiecesJSON(JSONArray arr) {
        for(int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int index = y * WIDTH + x;
                getPiece(x, y).fromJSON((JSONObject) arr.get(index));
            }
        }
    }
    
    public void fromJSON(JSONObject obj) {
        clear();
        
        float version = ((Double) obj.get("version")).floatValue();
        if (Math.abs(version - VERSION) > EPSILON) {
            throw new TrackLoadException(String.format(
                    "Track file version (%d) does not match the application (%d)",
                    version, VERSION));
        }
        
        // load the data
        notes = (String) obj.get("notes");
        setPiecesJSON((JSONArray) obj.get("pieces"));
    }
    
    public static class TrackLoadException extends RuntimeException {
        public TrackLoadException(String message) {
            super(message);
        }
    }
    
}
