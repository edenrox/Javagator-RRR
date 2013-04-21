/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author ian
 */
public class ColorSwap {
    
    public static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 0);
    
    public static void main(String args[]) throws Exception {
        
        /***************
         * Configuration
         */
        
        String inputFile = "images/cars/effects/explosion.png";
        String outputFile = "C:/Users/Ian/Desktop/explosion.png";
        
        String[] palleteConversion = new String[] {
            "000000 => B00000",
            "003973 => F82800",
            "085242 => F85800",
            "215A10 => F88800",
            "005A21 => F8B018",
            "314E18 => F8D040",
            "4F3500 => F8E860",
            "600000 => F8F880"
        };
        
        /***************
         * Application
         */
        
        // Load the input image
        InputStream is = ColorSwap.class.getClassLoader().getResourceAsStream(inputFile);
        BufferedImage inputImage = ImageIO.read(is);
        
        // Create the output image
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());
        
        // build the color translation table
        HashMap<Integer, Integer> translationTable = new HashMap<Integer, Integer>();
        translationTable.put(COLOR_TRANSPARENT.getRGB(), COLOR_TRANSPARENT.getRGB());
        for(String item : palleteConversion) {
            String parts[] = item.split(" => ");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Pallete Conversion table must have entries of the format: color => color");
            }
            translationTable.put(colorFromHex(parts[0]), colorFromHex(parts[1]));
        }
        
        // Iterate through the pixels of the image translating each color through the table
        for(int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                int color = inputImage.getRGB(x, y);
                if ((color & 0xff000000) == 0) {
                    color = COLOR_TRANSPARENT.getRGB();
                }
                if (!translationTable.containsKey(color)) {
                    throw new IllegalArgumentException(String.format("Unknown color in input image: %x", color));
                }
                color = translationTable.get(color);
                outputImage.setRGB(x, y, color);
            }
        }
        
        // Save the output image
        ImageIO.write(outputImage, "PNG", new File(outputFile));
    }
    
    
    protected static int colorFromHex(String hex) {
        return 0xff000000 | Integer.parseInt(hex, 16);
    }
    
}
