package com.hopkins.rocknrollracing.test;

import com.hopkins.rocknrollracing.Application;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class SpriteInfo 
{
    public static final Logger log = Logger.getLogger(SpriteInfo.class);
    
    public static void main( String[] args ) throws Exception
    {
        
        Application app = new Application();
        BufferedImage img = ImageUtils.loadSprite("images/cars/battle_trak.png");
        //BufferedImage img = ImageIO.read(new File("C:\\Users\\ian\\Desktop\\1.png"));
        
        log.info(String.format("Image Size: %dx%d pixels",
                    img.getWidth(), img.getHeight()));
        
        ColorTable tbl = new ColorTable();
        for(int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int color = img.getRGB(x, y);
                tbl.addColor(color);
            }
        }
        tbl.sort();
        
        log.info(String.format("Palette: %d colours", tbl.length()) );
        for(Integer item : tbl.getAllColors()) {
            System.out.print(String.format("0x%06X, ", (item.intValue() & 0xFFFFFF)));
        }
        System.out.print("\n");
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
        
        public void sort() {
            Collections.sort(data);
        }
        
        public int length() {
            return data.size();
        }
    }
}
