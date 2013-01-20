/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.track.TrackPieceType;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

/**
 *
 * @author ian
 */
public class TrackPieceElement extends AppElement {
    public static final String BASE_SPRITE_PATH = "images/track/%s.png";
    public static final String PLANET_SPRITE_PATH = "images/track/%s/%s.png";
    
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 8;
    
    public static final int WIDTH = 11 * TILE_WIDTH;
    public static final int HEIGHT = 13 * TILE_HEIGHT;
    
    
    
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
            BufferedImage bi = loadSprite(planet, piece);
            Field f = clazz.getDeclaredField(piece);
            f.setAccessible(true);
            f.set(this, bi);
        }
        
        finishLine = ImageUtils.loadSprite(String.format(BASE_SPRITE_PATH, "finishLine"));
    }
    
    protected BufferedImage loadSprite(String planet, String name) throws Exception {
        return ImageUtils.loadSprite(String.format(PLANET_SPRITE_PATH, planet, name));
    }
    
    
    public void renderPiece(Graphics g, int x, int y, TrackPieceType type) {
        switch (type) {
            case NotDown:
                renderInsideCorner(g, x, y, CornerType.South);
                renderInsideCorner(g, x, y, CornerType.West);
                renderBottomEdge(g, x, y, false);
                break;
            case NotRight:
                renderInsideCorner(g, x, y, CornerType.South);
                renderInsideCorner(g, x, y, CornerType.East);
                renderBottomEdge(g, x, y, true);
                break;
            case NotLeft:
                renderTopEdge(g, x, y, true);
                renderInsideCorner(g, x, y, CornerType.West);
                renderInsideCorner(g, x, y, CornerType.North);
                break;
            case NotUp:
                renderTopEdge(g, x, y, false);
                renderInsideCorner(g, x, y, CornerType.North);
                renderInsideCorner(g, x, y, CornerType.East);
                break;
            case Cross:
                renderInsideCorner(g, x, y, CornerType.North);
                renderInsideCorner(g, x, y, CornerType.East);
                renderInsideCorner(g, x, y, CornerType.South);
                renderInsideCorner(g, x, y, CornerType.West);
                break;
            case CornerDownLeft:
                renderInsideCorner(g, x, y, CornerType.East);
                renderOutsideCorner(g, x, y, CornerType.East);
                break;
            case CornerDownRight:
                renderInsideCorner(g, x, y, CornerType.North);
                renderOutsideCorner(g, x, y, CornerType.North);
                break;
            case CornerUpLeft:
                renderInsideCorner(g, x, y, CornerType.South);
                renderOutsideCorner(g, x, y, CornerType.South);
                break;
            case CornerUpRight:
                renderInsideCorner(g, x, y, CornerType.West);
                renderOutsideCorner(g, x, y, CornerType.West);
                break;
            case StartRight:
                renderTopEdge(g, x, y, false);
                renderRoad(g, x, y);
                renderBottomEdge(g, x, y, false);
                break;
            case StraightRight:
                renderTopEdge(g, x, y, false);
                renderRoad(g, x, y);
                renderBottomEdge(g, x, y, false);
                break;
            case StartUp:
                renderTopEdge(g, x, y, true);
                renderRoad(g, x, y);
                renderBottomEdge(g, x, y, true);
                break;
            case StraightUp:
                renderTopEdge(g, x, y, true);
                renderRoad(g, x, y);
                renderBottomEdge(g, x, y, true);
                break;
            case Empty:
            default:
                // noop
                break;
        }
    }
    
    protected void renderRoad(Graphics g, int x, int y) {
        int frame = 0;
        for(int j = 0; j < HEIGHT / TILE_HEIGHT; j++) {
            for (int i = 0; i < WIDTH / TILE_WIDTH; i++) {
                int px = x + i * TILE_WIDTH;
                int py = y + j * TILE_HEIGHT;
                SpriteRenderer.render(g, road, x, y, TILE_WIDTH, TILE_HEIGHT, frame, false, false);
                frame = (frame + 1) % 2;
            }
        }
    }
    
    protected void renderBottomEdge(Graphics g, int x, int y, boolean flip) {
        int ox = (flip) ? (6) : (0);
        for(int i = 0; i < 6; i++) {
            int oy = (flip) ? (10 - i) : (i + 5);
            int px = x + (i + ox) * TILE_WIDTH;
            int py = y + oy * TILE_HEIGHT;
            SpriteRenderer.render(g, edgeBottom, px, py, TILE_WIDTH, TILE_HEIGHT * 3, 0, false, flip);
        }
    }
    
    protected void renderTopEdge(Graphics g, int x, int y, boolean flip) {
        int ox = (flip) ? (0) : (6);
        for(int i = 0; i < 6; i++) {
            int oy = (flip) ? (5 - i) : (i);
            int px = x + (i + ox) * TILE_WIDTH;
            int py = y + oy * TILE_HEIGHT;
            SpriteRenderer.render(g, edgeTop, px, py, TILE_WIDTH, TILE_HEIGHT * 2, 0, false, flip);
        }
    }
    
    protected void renderInsideCorner(Graphics g, int x, int y, CornerType type) {
        switch (type) {
            case North:
                g.drawImage(cornerInsideB, x + 160, y + HEIGHT - 24, null);
                break;
            case East:
                g.drawImage(cornerInsideA, x + 32, y + 40, -30, 24, null);
                break;
            case South:
                g.drawImage(cornerInsideC, x + 160, y, null);
                break;
            case West:
                g.drawImage(cornerInsideA, x + WIDTH - 32, y + 40, null);
                break;
        }
    }
    
    protected void renderOutsideCorner(Graphics g, int x, int y, CornerType type) {
        switch (type) {
            case North:
                g.drawImage(cornerOutsideB, x, y, null);
                break;
            case East:
                g.drawImage(cornerOutsideA, x + WIDTH, y, -80, 112, null);
                break;
            case South:
                g.drawImage(cornerOutsideC, x, y + HEIGHT - 40, null);
                break;
            case West:
                g.drawImage(cornerOutsideA, x, y, null);
                break;
        }
    }
    
    public enum CornerType {
        North, East, South, West
    }
    
    
}
