/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.track;

import com.hopkins.rocknrollracing.trackeditor.PieceFeatureType;

/**
 *
 * @author ian
 */
public class TrackPieceTiler {

    protected TileLayer fg;
    protected TileLayer bg;
    protected Track track;
    protected TrackPiece curPiece;
    protected int tx;
    protected int ty;
    
    protected int getPieceX() {
        return 13 * 5 + tx * 6 - ty * 6;
    }
    
    protected int getPieceY() {
        return 6 + 6 * (tx + ty);
    }
    
    
    public TrackPieceTiler(Track track, TileLayer fg, TileLayer bg) {
        this.track = track;
        this.fg = fg;
        this.bg = bg;
    }
    
    public void renderTrack() {
        for(ty = 0; ty < Track.HEIGHT; ty++) {
            for (tx = 0; tx < Track.WIDTH; tx++) {
                curPiece = track.getPiece(tx, ty);
                renderPiece();
            }
        }
    }
    
    public void renderPiece(TrackPiece trackPiece, int tx, int ty) {
        this.curPiece = trackPiece;
        this.tx = tx;
        this.ty = ty;
        renderPiece();
    }
    
    protected void renderPiece() {
        if (curPiece.getType() == TrackPieceType.Empty) {
            return;
        }
        switch (curPiece.getType()) {
            case NotDown:
                renderEdge(EdgeDirection.Down);
                renderCornerInside(CornerDirection.North);
                renderCornerInside(CornerDirection.East);
                break;
            case NotUp:
                renderEdge(EdgeDirection.Up);
                renderCornerInside(CornerDirection.South);
                renderCornerInside(CornerDirection.West);
                break;
            case NotLeft:
                renderEdge(EdgeDirection.Left);
                renderCornerInside(CornerDirection.East);
                renderCornerInside(CornerDirection.South);
                break;
            case NotRight:
                renderEdge(EdgeDirection.Right);
                renderCornerInside(CornerDirection.North);
                renderCornerInside(CornerDirection.East);
                break;
            case StartRight:
                renderEdge(EdgeDirection.Up);
                renderEdge(EdgeDirection.Down);
                renderStart(EdgeDirection.Right);
                renderPillars(true);
                break;
            case StartUp:
                renderEdge(EdgeDirection.Left);
                renderEdge(EdgeDirection.Right);
                renderStart(EdgeDirection.Up);
                renderPillars(false);
                break;
            case StraightUp:
                renderEdge(EdgeDirection.Left);
                renderEdge(EdgeDirection.Right);
                renderPillars(false);
                break;
            case StraightRight:
                renderEdge(EdgeDirection.Up);
                renderEdge(EdgeDirection.Down);
                renderPillars(true);
                break;
            case JumpDown:
                renderEdge(EdgeDirection.Left);
                renderEdge(EdgeDirection.Right);
                renderJump(EdgeDirection.Down);
                renderPillars(false);
                break;
            case JumpRight:
                renderEdge(EdgeDirection.Up);
                renderEdge(EdgeDirection.Down);
                renderJump(EdgeDirection.Right);
                renderPillars(true);
                break;
            case CornerDownLeft:
                renderCornerOutside(CornerDirection.East);
                renderCornerInside(CornerDirection.East);
                renderPillars(false);
                break;
            case CornerDownRight:
                renderCornerOutside(CornerDirection.North);
                renderCornerInside(CornerDirection.North);
                break;
            case CornerUpLeft:
                renderCornerOutside(CornerDirection.South);
                renderCornerInside(CornerDirection.South);
                renderPillars(true);
                renderPillars(false);
                break;
            case CornerUpRight:
                renderCornerOutside(CornerDirection.West);
                renderCornerInside(CornerDirection.West);
                renderPillars(true);
                break;
            
            default:
            case JumpUp:
            case JumpLeft:
                throw new RuntimeException("Impossible piece type: " + curPiece.getType().toString());
        }
    }
    
    protected int getSlope(int a, int b) {
        TrackPiece prev = null, next = null;
        int ha = 0, hb = 0;
        
        switch (curPiece.getType()) {
            case StartUp:
            case StraightUp:
            case JumpUp:
            case JumpDown:
                prev = track.getPiece(tx, ty + 1);
                next = track.getPiece(tx, ty - 1);
                break;
            case StartRight:
            case StraightRight:
            case JumpRight:
            case JumpLeft:
                prev = track.getPiece(tx - 1, ty);
                next = track.getPiece(tx + 1, ty);
                break;
        }
        if (a == -1) {
            ha = prev.getHeight(5);
        } else if (a == 7) {
            ha = next.getHeight(1);
        } else {
            ha = curPiece.getHeight(a);
        }
        if (a == -1) {
            hb = prev.getHeight(5);
        } else if (b == 7) {
            hb = next.getHeight(1);
        } else {
            hb = curPiece.getHeight(b);
        }
        return ha - hb;
    }
    
    protected void renderEdge(EdgeDirection dir) {
        boolean isTopEdge = (dir == EdgeDirection.Up) || (dir == EdgeDirection.Left);
        boolean isFlip = (dir == EdgeDirection.Up) || (dir == EdgeDirection.Down);
        int px, py, ph;
        TilePiece cp, transPiece = null;
        int ox[], oy[];
        int transFrame = 0;
        
        // figure out the edge positions
        switch(dir) {
            case Up:
                ox = new int[] { 6, 7, 8, 9,10,11};
                oy = new int[] { 1, 2, 3, 4, 5, 6};
                break;
            case Right:
                ox = new int[] {6, 7, 8, 9,10, 11};
                oy = new int[] {11,10, 9, 8, 7, 6};
                break;
            case Down:
                ox = new int[] { 1, 2, 3, 4, 5, 6};
                oy = new int[] { 6, 7, 8, 9,10,11};
                break;
            case Left:
            default:
                ox = new int[] { 1, 2, 3, 4, 5, 6};
                oy = new int[] { 6, 5, 4, 3, 2, 1};
                break;
        }
        
        
        // Render the edge and its associated road tiles
        for (int i = 0; i < ox.length; i++) {
            boolean isLast = (i == ox.length - 1);
            int height = curPiece.getHeight(i+1);
            int slope = getSlope(i+1, i);
            int nextSlope = getSlope(i+2, i+1);
            if (isFlip) {
                height = curPiece.getHeight(i);
                slope = getSlope(i, i+1);
                nextSlope = getSlope(i-1, i);
            }
            px = getPieceX() + ox[i];
            py = getPieceY() + oy[i] - height;
            cp = getEdgePiece(slope, isTopEdge);

            // draw the edge piece tiles
            ph = getPieceHeight(cp);
            int cy = py;
            if (isTopEdge) {
                cy -= 1;
            }
            fg.setTiles(px, cy, cp, 0, ph, isFlip);
            
            // draw the road extending from the edge
            cp = getRoadPiece(slope);
            ph = getPieceHeight(cp);
            transPiece = getTransitionPiece(slope);
            transFrame = getTransitionFrame(nextSlope);

            if ((isTopEdge) && (slope != -2)) {
                fg.setTile(px + ((isFlip) ? -1 : 1), py, transPiece, transFrame, isFlip);
            }
            for (int j = 0; j < 2; j++) {
                if (isTopEdge) {
                    py += 1;
                } else {
                    py -= 1;
                }
                if (isFlip ^ isTopEdge) {
                    px += 1;
                } else {
                    px -= 1;
                }
                fg.setTiles(px, py, cp, 0, ph, isFlip);
                if (slope != -2) {
                    fg.setTile(px + ((isFlip) ? -1 : 1), py, transPiece, transFrame, isFlip);
                }
            }
            
        }
    }
    protected TilePiece getTransitionPiece(int slope) {
        switch (slope) {
            case -2:
                return TilePiece.TransitionSteepDown;
            case -1:
                return TilePiece.TransitionShallowDown;
            case 0:
            default:
                return TilePiece.TransitionFlat;
            case 1:
                return TilePiece.TransitionShallowUp;
            case 2:
                return TilePiece.TransitionSteepUp;
        }
    }
    protected int getTransitionFrame(int nextSlope) {
        return nextSlope + 2;
    }
    
    protected TilePiece getRoadPiece(int slope) {
        switch (slope) {
            case -2:
                return TilePiece.SteepDownRoad;
            case -1:
                return TilePiece.ShallowDownRoad;
            case 0:
            default:
                return TilePiece.FlatRoad;
            case 1:
                return TilePiece.ShallowUpRoad;
            case 2:
                return TilePiece.SteepUpRoad;
        }
    }
    protected TilePiece getEdgePiece(int slope, boolean isTopEdge) {
        switch (slope) {
            case -2:
                return (isTopEdge) ? TilePiece.SteepDownEdgeTop : TilePiece.SteepDownEdgeBottom;
            case -1:
                return (isTopEdge) ? TilePiece.ShallowDownEdgeTop : TilePiece.ShallowDownEdgeBottom;
            case 0:
            default:
                return (isTopEdge) ? TilePiece.FlatEdgeTop : TilePiece.FlatEdgeBottom;
            case 1:
                return (isTopEdge) ? TilePiece.ShallowUpEdgeTop : TilePiece.ShallowUpEdgeBottom;
            case 2:
                return (isTopEdge) ? TilePiece.SteepUpEdgeTop : TilePiece.SteepUpEdgeBottom;
        }
    }
    
    protected int getPieceHeight(TilePiece tp) {
        switch (tp) {
            case Background:
            case FlatRoad:
            case ShallowDownEdgeTop:
            case PillarMiddleSmall:
                return 1;
            case FlatEdgeTop:
            case ShallowDownEdgeBottom:
            case ShallowUpRoad:        
            case SteepDownEdgeTop:
                return 2;
            case FlatEdgeBottom:
            case ShallowUpEdgeTop:
            case SteepUpRoad:
            case SteepDownEdgeBottom:
            case PillarTop:
            case PillarMiddle:
            case PillarBottom:
                return 3;
            case ShallowUpEdgeBottom:
            case SteepUpEdgeTop:
                return 4;
            case SteepUpEdgeBottom:
                return 5;
            case Empty:
            case SteepDownRoad:
            case ShallowDownRoad:
            default:
                return 0;
        }
    }
    
    protected void renderStart(EdgeDirection dir) {
        
    }
    
    protected void renderJump(EdgeDirection dir) {
        
    }

    
    protected void renderCornerInside(CornerDirection dir) {
        
    }
    
    protected void renderCornerOutside(CornerDirection dir) {
        
    }
    
    protected void iterateEdge() {
        
    }
    
    protected void renderPillars(boolean isFlip) {
        int px = 0, py = 0;
        int ox[], oy[];
        int h;
        if (isFlip) {
            ox = new int[] { 1, 2, 3, 4, 5, 6};
            oy = new int[] { 6, 7, 8, 9,10,11};
        } else {
            ox = new int[] {6, 7, 8, 9,10, 11};
            oy = new int[] {11,10, 9, 8, 7, 6};
        }    
        
        for (int i = 0; i < ox.length; i++) {
            h = (isFlip) ? curPiece.getHeight(i) : curPiece.getHeight(i+1);
            px = getPieceX() + ox[i];
            py = getPieceY() + oy[i];
            
            // Top Piece
            bg.setTiles(px, py - h, TilePiece.PillarTop, 0, 4, isFlip);
            
            // Height adjustable piece
            for(int j = 0; j < h; j++) {
                int offset = (int) (Math.random() * 4);
                bg.setTile(px, py + 4 - h + j, TilePiece.PillarMiddleSmall, offset, isFlip);
            }

            // Center Piece
            py += 4;
            int offset = (int) (Math.random() * 4);
            bg.setTiles(px, py, TilePiece.PillarMiddle, offset * 4, 4, isFlip);
            
            // Bottom Piece
            py += 4;
            bg.setTiles(px, py, TilePiece.PillarBottom, 0, 4, isFlip);
        }

    }
    
    protected void renderPuddle() {
         boolean isFlip = (curPiece.getType() == TrackPieceType.StartRight)
                 || (curPiece.getType() == TrackPieceType.StraightRight)
                 || (curPiece.getType() == TrackPieceType.JumpRight)
                 || (curPiece.getType() == TrackPieceType.JumpLeft)
                 || (curPiece.getType() == TrackPieceType.NotUp)
                 || (curPiece.getType() == TrackPieceType.NotDown);
         
        int x = getPieceX();
        int y = getPieceY();
        for (int i = 0; i < 6; i++) {
            int ox = i / 3;
            int oy = i % 3;
            if (isFlip) {
                ox = 1 - ox;
            }
            fg.setTile(x + ox, y + oy, TilePiece.Puddle, 0, isFlip);
        }
    }
    
    protected void renderPillar(int x, int y, TileLayer bg) {
        renderPillarPiece(x, y, bg, TilePiece.PillarTop);
        renderPillarPiece(x, y+4, bg, TilePiece.PillarMiddle);
        renderPillarPiece(x, y+8, bg, TilePiece.PillarBottom);
    }
    
    protected void renderPillarPiece(int x, int y, TileLayer bg, TilePiece piece) {
        bg.setTile(x, y, piece, 0, false);
        bg.setTile(x, y+1, piece, 0, false);
        bg.setTile(x, y+2, piece, 0, false);
        bg.setTile(x, y+3, piece, 0, false);
    } 

    
    public enum CornerDirection {
        North, East, South, West
    }
    public enum EdgeDirection {
        Up, Right, Down, Left
    }
}

