/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.test;

import com.hopkins.rocknrollracing.views.elements.BigCarElement;
import com.hopkins.rocknrollracing.views.elements.ColorSwapper;
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
public class CarBGGenerate {
    
    public static void main(String args[]) throws Exception {
        
        /***************
         * Configuration
         */
        
        String inputFile = "images/cars/large/blue.png";
        String outputFile = "C:/Users/Ian/Desktop/out.png";
        
        /***************
         * Application
         */
        
        // Load the input image
        InputStream is = ColorSwap.class.getClassLoader().getResourceAsStream(inputFile);
        BufferedImage inputImage = ImageIO.read(is);
        
        HashMap<Integer, Integer> cu = ColorSwapper.buildColorLookup(
                ColorSwapper.makeColorsOpaque(BigCarElement.BlueColors),
                ColorSwapper.makeColorsOpaque(BigCarElement.YellowColors)
                );
        
        // Create the output image
        BufferedImage outputImage = ColorSwapper.paletteSwap(inputImage, cu);
        
        // Save the output image
        ImageIO.write(outputImage, "PNG", new File(outputFile));
    }
    
    
    protected static int colorFromHex(String hex) {
        return 0xff000000 | Integer.parseInt(hex, 16);
    }
}
