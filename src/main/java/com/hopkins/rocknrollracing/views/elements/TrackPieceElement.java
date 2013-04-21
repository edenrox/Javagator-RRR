/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.track.TrackPieceType;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

/**
 *
 * @author ian
 */
public class TrackPieceElement extends AppElement {
    public static final String SPRITE_PATH = "images/track/%s.png";
    
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 8;
    
    public static final int WIDTH = 13 * TILE_WIDTH;
    public static final int HEIGHT = 14 * TILE_HEIGHT;
    
    public boolean DebugMode = false;
    
    protected BufferedImage
            road, edgeTop, edgeBottom,
            cornerInsideA, cornerInsideB, cornerInsideC,
            cornerOutsideA, cornerOutsideB, cornerOutsideC;
    
    protected BufferedImage finishLine;

    @Override
    public void load() throws Exception {
        String planet = "chem_vi";
        String pieces[] = new String[] {
            "road", "edgeTop", "edgeBottom",
            "cornerInsideA", "cornerInsideB", "cornerInsideC",
            "cornerOutsideA", "cornerOutsideB", "cornerOutsideC"
            };
        Class<?> clazz = this.getClass();
        for(String piece : pieces) {
            BufferedImage bi = loadSprite(SPRITE_PATH, String.format("%s/%s", planet, piece));
            Field f = clazz.getDeclaredField(piece);
            f.setAccessible(true);
            f.set(this, bi);
        }
        
        finishLine = loadSprite(SPRITE_PATH, "finishLine");
    }
    
    
    public void renderPiece(Graphics g, int x, int y, TrackPieceType type) {
        x = x - WIDTH / 2;
        switch (type) {
            case NotDown:
                renderRoad(g, x, y, true, true, false, true);
                renderInsideCorner(g, x, y, CornerType.South);
                renderInsideCorner(g, x, y, CornerType.West);
                renderBottomEdge(g, x, y, false);
                break;
            case NotRight:
                renderRoad(g, x, y, true, false, true, true);
                renderInsideCorner(g, x, y, CornerType.South);
                renderInsideCorner(g, x, y, CornerType.East);
                renderBottomEdge(g, x, y, true);
                break;
            case NotLeft:
                renderRoad(g, x, y, true, true, true, false);
                renderTopEdge(g, x, y, true);
                renderInsideCorner(g, x, y, CornerType.West);
                renderInsideCorner(g, x, y, CornerType.North);
                break;
            case NotUp:
                renderRoad(g, x, y, false, true, true, true);
                renderTopEdge(g, x, y, false);
                renderInsideCorner(g, x, y, CornerType.North);
                renderInsideCorner(g, x, y, CornerType.East);
                break;
            case Cross:
                renderRoad(g, x, y, true, true, true, true);
                renderInsideCorner(g, x, y, CornerType.North);
                renderInsideCorner(g, x, y, CornerType.East);
                renderInsideCorner(g, x, y, CornerType.South);
                renderInsideCorner(g, x, y, CornerType.West);
                renderRoadColumn(g, x + 6 * TILE_WIDTH, y + 6 * TILE_HEIGHT, 2, 0);
                break;
            case CornerDownLeft:
                renderRoad(g, x, y, false, false, true, true);
                renderInsideCorner(g, x, y, CornerType.East);
                renderOutsideCorner(g, x, y, CornerType.East);
                break;
            case CornerDownRight:
                renderRoad(g, x, y, false, true, true, false);
                renderInsideCorner(g, x, y, CornerType.North);
                renderOutsideCorner(g, x, y, CornerType.North);
                break;
            case CornerUpLeft:
                renderRoad(g, x, y, true, false, false, true);
                renderInsideCorner(g, x, y, CornerType.South);
                renderOutsideCorner(g, x, y, CornerType.South);
                break;
            case CornerUpRight:
                renderRoad(g, x, y, true, true, false, false);
                renderInsideCorner(g, x, y, CornerType.West);
                renderOutsideCorner(g, x, y, CornerType.West);
                break;
            case StartRight:
                renderTopEdge(g, x, y, false);
                renderRoad(g, x, y, false, true, false, true);
                renderBottomEdge(g, x, y, false);
                renderFinishLine(g, x, y, false);
                break;
            case StraightRight:
                renderTopEdge(g, x, y, false);
                renderRoad(g, x, y, false, true, false, true);
                renderBottomEdge(g, x, y, false);
                break;
            case StartUp:
                renderTopEdge(g, x, y, true);
                renderRoad(g, x, y, true, false, true, false);
                renderBottomEdge(g, x, y, true);
                renderFinishLine(g, x, y, true);
                break;
            case StraightUp:
                renderTopEdge(g, x, y, true);
                renderRoad(g, x, y, true, false, true, false);
                renderBottomEdge(g, x, y, true);
                break;
            case Empty:
            default:
                // noop
                break;
        }
        if (DebugMode) {
            renderDebugOutline(g, x, y, (type == TrackPieceType.Empty));
        }
    }
    
    protected void renderDebugOutline(Graphics g, int x, int y, boolean isEmpty) {
        Color c = new Color(0, 255, 255, 127);
        if (isEmpty) {
            c = new Color(127, 127, 127, 127);
        }
        
        g.setColor(c);
        
        g.drawRect(x, y, WIDTH, HEIGHT);
    }
    
    protected void renderRoad(Graphics g, int x, int y, boolean exitNE, boolean exitSE, boolean exitSW, boolean exitNW) {
        /*int h[] = new int [] {1,3,5,7,5,3,1};
        
        // center tiles
        /*for(int i = 0; i < 7; i++) {
            int px = x + (3 + i) * TILE_WIDTH;
            
            int oy = y + (3 + Math.abs(i - 3)) * TILE_HEIGHT;
            int frame = 0;
            for (int j = 0; j < h[i]; j++) {
                int py = oy + j * TILE_HEIGHT;
                SpriteRenderer.render(g, road, px, py, TILE_WIDTH, TILE_HEIGHT, frame, false, false);
                frame = (frame + 1) % 2;
            }
        }
        renderRoadColumn(g, x + 6 * TILE_WIDTH, y + 3 * TILE_HEIGHT, 7, 1);
        
        if (exitNE) {
            renderRoadColumn(g, x + 10 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 9 * TILE_WIDTH, y + 4 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 8 * TILE_WIDTH, y + 3 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 7 * TILE_WIDTH, y + 2 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 6 * TILE_WIDTH, y + 2 * TILE_HEIGHT, 1, 0);
        }
        if (exitNW) {
            renderRoadColumn(g, x + 2 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 3 * TILE_WIDTH, y + 4 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 4 * TILE_WIDTH, y + 3 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 5 * TILE_WIDTH, y + 2 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 6 * TILE_WIDTH, y + 2 * TILE_HEIGHT, 1, 0);
        }
        if (exitSE) {
            renderRoadColumn(g, x + 7 * TILE_WIDTH, y + 10 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 8 * TILE_WIDTH, y + 9 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 9 * TILE_WIDTH, y + 8 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 10 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 11 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 1, 0);
        }
        if (exitSW) {
            renderRoadColumn(g, x + 5 * TILE_WIDTH, y + 10 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 4 * TILE_WIDTH, y + 9 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 3 * TILE_WIDTH, y + 8 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 2 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 2, 1);
            renderRoadColumn(g, x + 1 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 1, 0);
        }*/
    }
    
    protected void renderRoadColumn(Graphics g, int x, int y, int columnHeight, int frame) {
        for (int i = 0; i < columnHeight; i++) {
            SpriteRenderer.render(g, road, x, y + i * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, frame, false, false);
            frame = (frame + 1) % 2;
        }
    }
    protected void renderRoadRow(Graphics g, int x, int y, int rowWidth, int frame) {
        for (int i = 0; i < rowWidth; i++) {
            SpriteRenderer.render(g, road, x  + i * TILE_WIDTH, y, TILE_WIDTH, TILE_HEIGHT, frame, false, false);
            frame = (frame + 1) % 2;
        }
    }
    
    protected void renderBottomEdge(Graphics g, int x, int y, boolean flip) {
        int ox[] = new int[] { 1, 2, 3, 4, 5, 6};
        int oy[] = new int[] { 6, 7, 8, 9,10,11};
        
        if (flip) {
            ox = new int[] { 6, 7, 8, 9,10,11};
            oy = new int[] {11,10, 9, 8, 7, 6};
        }

        for(int i = 0; i < 6; i++) {
            int px = x + ox[i] * TILE_WIDTH;
            int py = y + oy[i] * TILE_HEIGHT;
            SpriteRenderer.render(g, edgeBottom, px, py, TILE_WIDTH, TILE_HEIGHT * 3, 0, false, flip);
        }
        renderRoadRow(g, x + 2 * TILE_WIDTH, y + 6 * TILE_HEIGHT, 9, 0);
        if (flip) {
            renderRoadRow(g, x + 1 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 9, 0);
            renderRoadRow(g, x + 2 * TILE_WIDTH, y + 8 * TILE_HEIGHT, 7, 0);
            renderRoadRow(g, x + 3 * TILE_WIDTH, y + 9 * TILE_HEIGHT, 5, 0);
            renderRoadRow(g, x + 4 * TILE_WIDTH, y + 10 * TILE_HEIGHT, 3, 0);
            renderRoadRow(g, x + 5 * TILE_WIDTH, y + 11 * TILE_HEIGHT, 1, 0);
            
        } else {
            renderRoadRow(g, x + 3 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 9, 0);
            renderRoadRow(g, x + 4 * TILE_WIDTH, y + 8 * TILE_HEIGHT, 7, 0);
            renderRoadRow(g, x + 5 * TILE_WIDTH, y + 9 * TILE_HEIGHT, 5, 0);
            renderRoadRow(g, x + 6 * TILE_WIDTH, y + 10 * TILE_HEIGHT, 3, 0);
            renderRoadRow(g, x + 7 * TILE_WIDTH, y + 11 * TILE_HEIGHT, 1, 0);
        }
    }
    
    protected void renderTopEdge(Graphics g, int x, int y, boolean flip) {
        int ox[] = new int[] { 7, 8, 9,10,11,12};
        int oy[] = new int[] { 1, 2, 3, 4, 5, 6};
        
        if (flip) {
            ox = new int[] { 0, 1, 2, 3, 4, 5};
            oy = new int[] { 6, 5, 4, 3, 2, 1};
        }

        for(int i = 0; i < 6; i++) {
            int px = x + ox[i] * TILE_WIDTH;
            int py = y + oy[i] * TILE_HEIGHT;
            SpriteRenderer.render(g, edgeTop, px, py, TILE_WIDTH, TILE_HEIGHT * 2, 0, false, flip);
        }
        
        renderRoadRow(g, x + 2 * TILE_WIDTH, y + 6 * TILE_HEIGHT, 9, 0);
        if (flip) {
            renderRoadRow(g, x + 1 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 9, 0);
            
            renderRoadRow(g, x + 3 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 8, 0);
            renderRoadRow(g, x + 4 * TILE_WIDTH, y + 4 * TILE_HEIGHT, 6, 0);
            renderRoadRow(g, x + 5 * TILE_WIDTH, y + 3 * TILE_HEIGHT, 4, 0);
            renderRoadRow(g, x + 6 * TILE_WIDTH, y + 2 * TILE_HEIGHT, 2, 0);
        } else {
            renderRoadRow(g, x + 3 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 9, 0);
            renderRoadRow(g, x + 2 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 8, 1);
            renderRoadRow(g, x + 3 * TILE_WIDTH, y + 4 * TILE_HEIGHT, 6, 1);
            renderRoadRow(g, x + 4 * TILE_WIDTH, y + 3 * TILE_HEIGHT, 4, 1);
            renderRoadRow(g, x + 5 * TILE_WIDTH, y + 2 * TILE_HEIGHT, 2, 1);
        }
    }
    
    protected void renderFinishLine(Graphics g, int x, int y, boolean flip) {
        int px;
        int py = y + 4 * TILE_HEIGHT;
        int frame = 0;
        
        if (flip) {
            px = x + 3 * TILE_WIDTH;
            py += TILE_HEIGHT;
        } else {
            px = x + 7 * TILE_WIDTH;
        }
        
        // top
        SpriteRenderer.render(g, finishLine, px, py, TILE_WIDTH, TILE_HEIGHT, frame, false, true);
        SpriteRenderer.render(g, finishLine, px + TILE_WIDTH, py, TILE_WIDTH, TILE_HEIGHT, frame, false, false);
        // center
        for (int i = 0; i < 3; i++) {
            py += TILE_HEIGHT;
            if (!flip) {
                px -= TILE_WIDTH;
            }
            frame = (flip) ? 1 : 0;
            SpriteRenderer.render(g, finishLine, px, py, TILE_WIDTH, TILE_HEIGHT, frame, false, true);
            SpriteRenderer.render(g, finishLine, px + TILE_WIDTH, py, TILE_WIDTH, TILE_HEIGHT, 2, false, false);
            frame = (flip) ? 0 : 1;
            SpriteRenderer.render(g, finishLine, px + TILE_WIDTH * 2, py, TILE_WIDTH, TILE_HEIGHT, frame, false, false);
            if (flip) {
                px += TILE_WIDTH;
            }
        }
        // bottom
        py += TILE_HEIGHT;
        frame = 1;
        SpriteRenderer.render(g, finishLine, px, py, TILE_WIDTH, TILE_HEIGHT, frame, false, true);
        SpriteRenderer.render(g, finishLine, px + TILE_WIDTH, py, TILE_WIDTH, TILE_HEIGHT, frame, false, false);
        
        
    }
    
    protected void renderInsideCorner(Graphics g, int x, int y, CornerType type) {
        switch (type) {
            case North:
                g.drawImage(cornerInsideB, x + 192, y + HEIGHT - 24, null);
                
                renderRoadRow(g, x + 5 * TILE_WIDTH, y + 11 * TILE_HEIGHT, 3, 0);
                renderRoadRow(g, x + 4 * TILE_WIDTH, y + 10 * TILE_HEIGHT, 5, 0);
                renderRoadRow(g, x + 3 * TILE_WIDTH, y + 9 * TILE_HEIGHT, 7, 0);
                renderRoadRow(g, x + 2 * TILE_WIDTH, y + 8 * TILE_HEIGHT, 9, 0);
                
                break;
            case East:
                g.drawImage(cornerInsideA, x + TILE_WIDTH, y + 40, -32, 24, null);
                renderRoadColumn(g, x + 5 * TILE_WIDTH, y + 2 * TILE_HEIGHT, 10, 1);
                renderRoadColumn(g, x + 4 * TILE_WIDTH, y + 3 * TILE_HEIGHT, 8, 1);
                renderRoadColumn(g, x + 3 * TILE_WIDTH, y + 4 * TILE_HEIGHT, 6, 1);
                renderRoadColumn(g, x + 2 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 4, 1);
                renderRoadColumn(g, x + 1 * TILE_WIDTH, y + 6 * TILE_HEIGHT, 2, 1);
                break;
            case South:
                g.drawImage(cornerInsideC, x + 192, y, null);
                renderRoadRow(g, x + 5 * TILE_WIDTH, y + 2 * TILE_HEIGHT, 3, 1);
                renderRoadRow(g, x + 4 * TILE_WIDTH, y + 3 * TILE_HEIGHT, 5, 1);
                renderRoadRow(g, x + 3 * TILE_WIDTH, y + 4 * TILE_HEIGHT, 7, 1);
                renderRoadRow(g, x + 2 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 9, 1);
                
                break;
            case West:
                g.drawImage(cornerInsideA, x + WIDTH - TILE_WIDTH, y + 40, null);
                
                renderRoadColumn(g, x + 7 * TILE_WIDTH, y + 2 * TILE_HEIGHT, 10, 1);
                renderRoadColumn(g, x + 8 * TILE_WIDTH, y + 3 * TILE_HEIGHT, 8, 1);
                renderRoadColumn(g, x + 9 * TILE_WIDTH, y + 4 * TILE_HEIGHT, 6, 1);
                renderRoadColumn(g, x + 10 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 4, 1);
                renderRoadColumn(g, x + 11 * TILE_WIDTH, y + 6 * TILE_HEIGHT, 2, 1);
                break;
        }
    }
    
    protected void renderOutsideCorner(Graphics g, int x, int y, CornerType type) {
        switch (type) {
            case North:
                g.drawImage(cornerOutsideB, x + TILE_WIDTH, y + 24, null);
                SpriteRenderer.render(g, edgeTop, x, y + 48, TILE_WIDTH, 16, 0, false, true);
                SpriteRenderer.render(g, edgeTop, x + WIDTH - TILE_WIDTH, y + 48, TILE_WIDTH, 16, 1, false, false);
                renderRoadRow(g, x + 4 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 5, 1);
                renderRoadRow(g, x + 3 * TILE_WIDTH, y + 6 * TILE_HEIGHT, 7, 1);
                renderRoadRow(g, x + 1 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 11, 0);
                /*renderRoadColumn(g, x + 1 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 1, 0);
                renderRoadColumn(g, x + 2 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 2, 1);
                renderRoadColumn(g, x + 3 * TILE_WIDTH, y + 6 * TILE_HEIGHT, 4, 1);
                renderRoadColumn(g, x + 4 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 6, 1);
                renderRoadColumn(g, x + 5 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 7, 0);
                renderRoadColumn(g, x + 6 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 5, 1);
                renderRoadColumn(g, x + 7 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 7, 0);
                renderRoadColumn(g, x + 8 * TILE_WIDTH, y + 5 * TILE_HEIGHT, 6, 1);
                renderRoadColumn(g, x + 9 * TILE_WIDTH, y + 6 * TILE_HEIGHT, 4, 1);
                renderRoadColumn(g, x + 10 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 2, 1);
                renderRoadColumn(g, x + 11 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 1, 0);
                */
                break;
            case East:
                g.drawImage(cornerOutsideA, x + WIDTH - TILE_WIDTH * 4, y, -96, 112, null);
                renderRoadColumn(g, x + 6 * TILE_WIDTH, y + 3 * TILE_HEIGHT, 7, 1);
                
                break;
            case South:
                g.drawImage(cornerOutsideC, x + TILE_WIDTH, y + HEIGHT - 64, null);
                renderRoadRow(g, x + 3 * TILE_WIDTH, y + 6 * TILE_HEIGHT, 7, 1);
                renderRoadRow(g, x + 5 * TILE_WIDTH, y + 7 * TILE_HEIGHT, 3, 0);
                break;
            case West:
                g.drawImage(cornerOutsideA, x + TILE_WIDTH * 4, y, null);
                renderRoadColumn(g, x + 6 * TILE_WIDTH, y + 3 * TILE_HEIGHT, 7, 1);
                
                
                
                
                break;
        }
    }
    
    public enum CornerType {
        North, East, South, West
    }
    
    
}
