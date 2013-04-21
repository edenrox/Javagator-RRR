/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author ian
 */
public class ColorSwapper {
    
    /**
     * Convert a set of integer colors to be opaque (set the first byte to 0xFF)
     * @return the array passed in
     */
    public static int[] makeColorsOpaque(int[] colors) {
        for(int i = 0; i < colors.length; i++) {
            colors[i] = 0xff000000 | colors[i];
        }
        return colors;
    }
    
    /**
     * Build a lookup table mapping origin colors to destination colors
     * @return a lookup table
     */
    public static HashMap<Integer, Integer> buildColorLookup(int[] originColors, int[] destinationColors) {
        HashMap<Integer, Integer> rv = new HashMap<Integer, Integer>();
        if (originColors.length != destinationColors.length) {
            throw new IllegalArgumentException("Origin and destination color arrays must be the same length");
        }
        for (int i = 0; i < originColors.length; i++) {
            rv.put(originColors[i], destinationColors[i]);
        }
        
        return rv;
    }
    
    /**
     * Find all the colors in an buffered image
     * @param src the image to look through
     * @return an array of integer colors
     */
    public static int[] loadPalette(BufferedImage src) {
        Integer value = new Integer(1);
        
        // Initialize an empty lookup table
        HashMap<Integer, Integer> lu = new HashMap<Integer, Integer>();
        
        // Loop through the image pixels and add any colors found to the lookup table
        for(int y = 0; y < src.getHeight(); y++) {
            for(int x = 0; x < src.getWidth(); x++) {
                int color = src.getRGB(x, y);
                if (!lu.containsKey(color)) {
                    lu.put(color, value);
                }
            }
        }
        
        // Copy the keyset into an array of keys
        int[] rv = new int[lu.keySet().size()];
        int i = 0;
        for(Integer item : lu.keySet()) {
            rv[i] = item.intValue();
            i++;
        }
        return rv;
    }
    
    /**
     * Create an image by swapping colors based on the lookup table.
     * Colors not in the table will simply be copied
     * @param src the image to palette swap
     * @param colorLookup the lookup table used to swap
     * @return the generated image
     */
    public static BufferedImage paletteSwap(BufferedImage src, HashMap<Integer, Integer> colorLookup) {
        BufferedImage rv = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        
        for(int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                int color = src.getRGB(x, y);
                if (colorLookup.containsKey(color)) {
                    color = colorLookup.get(color);
                }
                rv.setRGB(x,y,color);
            }
        }
        
        return rv;
    }
    
    
}
