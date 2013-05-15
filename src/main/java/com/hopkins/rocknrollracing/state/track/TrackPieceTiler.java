/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.track;

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
        if (track == null) {
            return 0;// we aren't rendering a track
        }
        return 13 * 5 + tx * 6 - ty * 6;
    }
    
    protected int getPieceY() {
        if (track == null) {
            return 5; // we aren't rendering a track
        }
        return 6 + 6 * (tx + ty);
    }
    
    
    public TrackPieceTiler(Track track, TiledTrack tt) {
        this.track = track;
        this.fg = tt.getFG();
        this.bg = tt.getBG();
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
                renderCornerInside(CornerDirection.South);
                renderCornerInside(CornerDirection.West);
                renderPillars(true);
                break;
            case NotUp:
                renderEdge(EdgeDirection.Up);
                renderCornerInside(CornerDirection.North);
                renderCornerInside(CornerDirection.East);
                break;
            case NotLeft:
                renderEdge(EdgeDirection.Left);
                renderCornerInside(CornerDirection.West);
                renderCornerInside(CornerDirection.North);
                break;
            case NotRight:
                renderEdge(EdgeDirection.Right);
                renderCornerInside(CornerDirection.South);
                renderCornerInside(CornerDirection.East);
                renderPillars(false);
                break;
            case StartRight:
                renderEdge(EdgeDirection.Up);
                renderEdge(EdgeDirection.Down);
                renderStart(true);
                renderPillars(true);
                break;
            case StartUp:
                renderEdge(EdgeDirection.Left);
                renderEdge(EdgeDirection.Right);
                renderStart(false);
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
                break;
            case CornerDownRight:
                renderCornerOutside(CornerDirection.North);
                renderCornerInside(CornerDirection.North);
                break;
            case CornerUpLeft:
                renderCornerOutside(CornerDirection.South);
                renderCornerInside(CornerDirection.South);
                break;
            case CornerUpRight:
                renderCornerOutside(CornerDirection.West);
                renderCornerInside(CornerDirection.West);
                break;
            case Cross:
                renderCornerInside(CornerDirection.North);
                renderCornerInside(CornerDirection.East);
                renderCornerInside(CornerDirection.South);
                renderCornerInside(CornerDirection.West);
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
        
        if (track == null) {
            // incase we are just rendering a single piece
            prev = curPiece;
            next = curPiece;
        } else {
            switch (curPiece.getType()) {
                case StartUp:
                case StraightUp:
                case JumpUp:
                case JumpDown:
                case NotLeft:
                case NotRight:
                    prev = track.getPiece(tx, ty + 1);
                    next = track.getPiece(tx, ty - 1);
                    break;
                case StartRight:
                case StraightRight:
                case JumpRight:
                case JumpLeft:
                case NotUp:
                case NotDown:
                    prev = track.getPiece(tx - 1, ty);
                    next = track.getPiece(tx + 1, ty);
                    break;
            }
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
            if (slope == -2) {
                cy -= 1;
            }
            if ((ox[i] == 6)
                    && (oy[i] == 1) 
                    && (fg.getTile(px, py) != TilePiece.Empty)) {
                // skip this for the inside of corners
            } else {
                fg.setTiles(px, cy, cp, 0, ph, isFlip);
            }
            
            
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
    
    protected void renderStart(boolean isFlip) {
        int ox[] = new int[] { 5, 5, 6, 7, 8};
        int oy[] = new int[] { 3, 4, 5, 6, 7};
        TilePiece tp = TilePiece.FlatFinish;
        
        if (isFlip) {
            ox = new int[] { 8, 7, 6, 5, 5};
            oy = new int[] { 5, 6, 7, 8, 9};
        }
        
        int height = curPiece.getHeight();
        for(int i = 0; i < ox.length; i++) {
            int px = getPieceX() + ox[i];
            int py = getPieceY() + oy[i] - height;
            if (i == 0) {
                // top
                fg.setTile(px, py, tp, (isFlip) ? 1 : 0, isFlip);
                fg.setTile(px+1, py, tp, (isFlip) ? 0 : 1, isFlip);
            } else if (i == ox.length - 1) {
                // bottom
                fg.setTile(px, py, tp, (isFlip) ? 3 : 2, isFlip);
                fg.setTile(px+1, py, tp, (isFlip) ? 2 : 3, isFlip);
            } else {
                // middle
                if (isFlip) {
                    // left
                    fg.setTile(px, py, tp, 1, true);
                    // right
                    fg.setTile(px + 2, py, tp, 3, false);
                } else {
                    // left
                    fg.setTile(px, py, tp, 2, false);
                    // right
                    fg.setTile(px + 2, py, tp, 1, false);
                }
                // center
                fg.setTile(px + 1, py, tp, 4, false);
            }
        }
    }
    
    protected void renderJump(EdgeDirection dir) {
        
    }

    
    protected void renderFlatRoadColumn(int px, int py, int height, boolean startOnX) {
        boolean isX = startOnX;
        TilePiece tp;
        int frame;
        for (int i = 0; i < height; i++) {
            tp = (isX) ? TilePiece.FlatRoad : TilePiece.TransitionFlat;
            frame = (isX) ? 0 : 2;
            fg.setTile(px, py + i, tp, frame, false);
            isX = !isX;
        }
    }
    
    protected void renderCornerInside(CornerDirection dir) {
        int px = getPieceX();
        int py = getPieceY() - curPiece.getHeight();
        switch(dir) {
            case North:
                fg.setTiles(px + 6, py + 11, TilePiece.CornerNorthInside, 0, 3, false);
                
                renderFlatRoadColumn(px + 4, py + 9, 1, true);
                renderFlatRoadColumn(px + 5, py + 8, 3, true);
                renderFlatRoadColumn(px + 6, py + 7, 4, true);
                renderFlatRoadColumn(px + 7, py + 8, 3, true);
                renderFlatRoadColumn(px + 8, py + 9, 1, true);
                
                break;
            case South:
                fg.setTiles(px + 6, py, TilePiece.CornerSouthInside, 0, 2, false);
                renderFlatRoadColumn(px + 3, py + 3, 1, false);
                renderFlatRoadColumn(px + 4, py + 2, 3, false);
                renderFlatRoadColumn(px + 5, py + 1, 5, false);
                renderFlatRoadColumn(px + 6, py + 2, 5, false);
                renderFlatRoadColumn(px + 7, py + 1, 5, false);
                renderFlatRoadColumn(px + 8, py + 2, 3, false);
                renderFlatRoadColumn(px + 9, py + 3, 1, false);
                
                break;
            case East:
                fg.setTiles(px, py + 5, TilePiece.CornerWestInside, 0, 3, true);
                renderFlatRoadColumn(px + 1, py + 5, 2, false);
                renderFlatRoadColumn(px + 2, py + 4, 4, false);
                renderFlatRoadColumn(px + 3, py + 4, 5, true);
                renderFlatRoadColumn(px + 4, py + 5, 4, true);
                renderFlatRoadColumn(px + 5, py + 6, 2, true);
                break;
            case West:
                fg.setTiles(px + 12, py + 5, TilePiece.CornerWestInside, 0, 3, false);
                renderFlatRoadColumn(px + 11, py + 5, 2, false);
                renderFlatRoadColumn(px + 10, py + 4, 4, false);
                renderFlatRoadColumn(px + 9, py + 4, 5, true);
                renderFlatRoadColumn(px + 8, py + 5, 4, true);
                renderFlatRoadColumn(px + 7, py + 6, 2, true);
                break;
        }
    }
    
    protected void renderCornerTiles(TileLayer layer, int px, int py, TilePiece piece, int width, int height, boolean flip) {
        int frame;
        for(int x = 0; x < width; x++) {
            if (flip) {
                frame = (width - x - 1) * height;
            } else {
                frame = x * height;
            }
            for (int y = 0; y < height; y++){
                layer.setTile(px + x, py + y, piece, frame, flip);
                frame++;
            }
        }
    }
    
    protected void renderCornerOutside(CornerDirection dir) {
        int px = getPieceX();
        int py = getPieceY() - curPiece.getHeight();
        switch(dir) {
            case North:
                renderCornerTiles(fg, px+1, py+3, TilePiece.CornerNorthOutside, 11, 4, false);
                renderFlatRoadColumn(px + 2, py + 7, 1, true);
                renderFlatRoadColumn(px + 3, py + 6, 3, true);
                renderFlatRoadColumn(px + 4, py + 5, 4, true);
                renderFlatRoadColumn(px + 5, py + 5, 3, false);
                renderFlatRoadColumn(px + 6, py + 5, 2, true);
                renderFlatRoadColumn(px + 7, py + 5, 3, false);
                renderFlatRoadColumn(px + 8, py + 5, 4, true);
                renderFlatRoadColumn(px + 9, py + 6, 3, true);
                renderFlatRoadColumn(px + 10, py + 7, 1, true);
                break;
            case South:
                renderCornerTiles(fg, px+1, py+6, TilePiece.CornerSouthOutside, 11, 5, false);
                renderFlatRoadColumn(px + 1, py + 5, 1, false);
                renderFlatRoadColumn(px + 2, py + 4, 2, false);
                renderFlatRoadColumn(px + 3, py + 4, 3, true);
                renderFlatRoadColumn(px + 4, py + 5, 2, true);
                renderFlatRoadColumn(px + 5, py + 6, 2, true);
                renderFlatRoadColumn(px + 6, py + 7, 1, true);
                renderFlatRoadColumn(px + 7, py + 6, 2, true);
                renderFlatRoadColumn(px + 8, py + 5, 2, true);
                renderFlatRoadColumn(px + 9, py + 4, 3, true);
                renderFlatRoadColumn(px + 10, py + 4, 2, false);
                renderFlatRoadColumn(px + 11, py + 5, 1, false);
                break;
            case East:
                renderCornerTiles(fg, px+6, py, TilePiece.CornerWestOutside, 3, 14, true);
                renderFlatRoadColumn(px + 3, py + 3, 1, false);
                renderFlatRoadColumn(px + 4, py + 2, 8, false);
                renderFlatRoadColumn(px + 5, py + 1, 10, false);
                renderFlatRoadColumn(px + 6, py + 3, 7, true);
                
                break;
            case West:
                renderCornerTiles(fg, px+4, py, TilePiece.CornerWestOutside, 3, 14, false);
                
                renderFlatRoadColumn(px + 6, py + 3, 7, true);
                renderFlatRoadColumn(px + 7, py + 1, 10, false);
                renderFlatRoadColumn(px + 8, py + 2, 8, false);
                renderFlatRoadColumn(px + 9, py + 3, 1, false);
                break;
        }
    }
    
    protected void renderPillar(int px, int py, int h, boolean isFlip) {
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
            
            renderPillar(px, py, h, isFlip);
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
    
    public enum CornerDirection {
        North, East, South, West
    }
    public enum EdgeDirection {
        Up, Right, Down, Left
    }
}

