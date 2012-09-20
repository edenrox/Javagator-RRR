package com.hopkins.rocknrollracing.test;

import com.hopkins.rocknrollracing.Application;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class SpriteInfo 
{
    public static final Logger log = Logger.getLogger(SpriteInfo.class);
    
    public static void main( String[] args )
    {
        
        Application app = new Application();
        BufferedImage img = app.sprites.loadSprite("images/planet.png");
        
        log.info(String.format("Image Size: %dx%d pixels",
                    img.getWidth(), img.getHeight()));
        
        ColorTable tbl = new ColorTable();
        for(int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int color = img.getRGB(x, y);
                tbl.addColor(color);
            }
        }
        log.info(String.format("Palette: %d colours", tbl.length()) );
        for(Integer item : tbl.getAllColors()) {
            log.info(String.format("Color: #%06X", (item.intValue() & 0xFFFFFF)));
        }
    }
    
    public static class ColorTable {
        private ArrayList<Integer> data;
        
        public ColorTable() {
            data = new ArrayList<Integer>();
        }
        
        public Iterable<Integer> getAllColors() {
            return data;
        }
        
        public void addColor(int color) {
            if (!hasColor(color)) {
                data.add(color);
            }
        }
        public boolean hasColor(int color) {
            for(Integer item : data) {
                if (item.intValue() == color) {
                    return true;
                }
            }
            return false;
        }
        
        public int length() {
            return data.size();
        }
    }
}
