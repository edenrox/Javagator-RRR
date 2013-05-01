/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.test;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

/**
 *
 * @author ian
 */
public class SpriteExtractor {
    
    // Path to the SNES ROM where the sprites are stored.
    public static final String PATH_TO_ROM = "C:/Games/SNES/roms/Rock N' Roll Racing.smc";
    public static final int SPRITE_OFFSET_IN_ROM = 0x050200;
    public static final int SPRITE_TILES_WIDTH = 6;
    public static final int SPRITE_TILES_HEIGHT = 6;
    
    public static final int BITS_PER_PIXEL = 4;
    public static final int BITS_PER_BYTE = 8;
    
    public static final int[] COLOR_ARRAY = new int[] {
        0x00000000,0xFF001084,
        0xFF08008C,0xFF42007B,
        
        0xFF63005A,0xFF6B0010,
        0xFF600000,0xFF4F3500,
        
        0xFF314E18,0xFF005A21,
        0xFF215A10,0xFF085242,
        
        0xFF003973,0xFF000000,
        0xFF323232,0xFF525252
    };
    
    
    public static void echoTile(byte[] tile) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                System.out.print(String.format("%02X ", tile[y * 8 + x]));
            }
            System.out.println("");
        }
    }
    
    public static byte readBit(byte source, int offset) {
        return (byte) (source >> (7 - offset) & 0x1);
    }
    
    public static byte[] readTile(DataInputStream dis) throws Exception {
        byte[] buffer = new byte[32];
        byte[] rv = new byte[64];
        dis.read(buffer);
        
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                int rvIndex = y * 8 + x;
                
                rv[rvIndex] = readBit(buffer[y * 2], x);
                rv[rvIndex] += (byte) (readBit(buffer[y * 2 + 1], x) << 1);
                rv[rvIndex] += (byte) (readBit(buffer[y * 2 + 16], x) << 2);
                rv[rvIndex] += (byte) (readBit(buffer[y * 2 + 17], x) << 3);
            }
        }

        
        //echoTile(rv);
        return rv;
    }
    
    public static void renderTile(BufferedImage bi, int x, int y, byte[] tile) {
        for(int oy = 0; oy < 8; oy++) {
            for (int ox = 0; ox < 8; ox++) {
                int color = COLOR_ARRAY[tile[oy * 8 + ox]];
                int iy = y * 8 + oy;
                int ix = x * 8 + ox;
                bi.setRGB(ix, iy, color);
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        File rom = new File(PATH_TO_ROM);
        System.err.println(String.format("File Size: %s bytes", rom.length()));
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(rom)));
        
        dis.skip(SPRITE_OFFSET_IN_ROM);
        
        int sheetWidth = 48;
        int sheetHeight = 48 * 45;
        int numFlatFrames = 13;
        int numFrames = numFlatFrames + 32;
        
        BufferedImage bi = new BufferedImage(sheetWidth, sheetHeight, BufferedImage.TYPE_INT_ARGB);
        for (int carIndex = 0; carIndex < 5; carIndex++) {
            int fx = 0;
            int fy = 0;
            for (int frameIndex = 0; frameIndex < numFrames; frameIndex++) {
                System.out.println(String.format("%d,%d", fx, fy));
                for (int y = 0; y < 6; y++) {
                    for (int x = 0; x < 6; x++) {
                        byte[] tile = readTile(dis);
                        renderTile(bi, fx * 6 + x, fy * 6 + y, tile);
                    }
                }
                fy++;
            }
            ImageIO.write(bi, "PNG", new File(String.format("sheet-%03d.png", carIndex)));
        }
    }
    
}
