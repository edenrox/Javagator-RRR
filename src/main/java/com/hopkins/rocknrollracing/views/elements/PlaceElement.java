/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.CarColor;
import com.hopkins.rocknrollracing.utils.ArrayUtils;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 *
 * @author ian
 */
public class PlaceElement extends AppElement {
    
    public static final String SPRITE_PATH = "images/places/%s.png";
    public static final int NUM_PLACES = 4;
    
    public static int[] originPalette = new int[] {
        0x00000000, 0xFF101010, 0xFF0094FF, 0xFF08008C
    };

    protected BufferedImage places;
    protected BufferedImage buffer;
    protected int[] bufferPixels;
    protected int[][] pixels;
    protected int[] destPalette = new int[] {
        0x00000000, 0xFF101010, 0, 0
    };
    
    // Load in the place images and build arrays that are indicies into the palette table
    @Override
    public void load() throws Exception {
        buffer = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        bufferPixels = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        pixels = new int[NUM_PLACES][];
        
        // Load each place image
        for(int i = 0; i < NUM_PLACES; i++) {
            BufferedImage placeImage = loadSprite(SPRITE_PATH, "" + (i + 1));
            pixels[i] = new int[placeImage.getWidth() * placeImage.getHeight()];
            int j = 0;
            
            // Build a table of color indices
            for(int y = 0; y < placeImage.getHeight(); y++) {
                for(int x = 0; x < placeImage.getWidth(); x++) {
                    int color = placeImage.getRGB(x, y);
                    int index = ArrayUtils.indexOf(originPalette, color);
                    pixels[i][j] = index;
                    j++;
                }
            }
        }
    }
    
    // Set the two colors we need from the CarColors
    protected void setDestinationPalette(CarColor c) {
        destPalette[2] = c.getColors()[0];
        destPalette[3] = c.getColors()[1];
    }
    
    // Translate the pixel indices to palette colors
    protected void colorizeBuffer(int imageIndex) {
        int[] source = pixels[imageIndex];
        for(int i = 0; i < source.length; i++) {
            bufferPixels[i] = destPalette[source[i]];
        }
    }
    
    
    public void renderPlace(Graphics g, int x, int y, CarColor color, int position) {
        // Set the palette
        setDestinationPalette(color);
        
        // Write the image to the buffer
        colorizeBuffer(position);
        
        // Draw the buffer to the graphics context
        g.drawImage(buffer, x, y, null);
    }
    
    
    
}
