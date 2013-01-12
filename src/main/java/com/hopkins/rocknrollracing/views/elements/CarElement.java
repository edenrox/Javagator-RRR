/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.CarModel;
import com.hopkins.rocknrollracing.state.CarState;
import com.hopkins.rocknrollracing.utils.ArrayUtils;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import com.hopkins.rocknrollracing.utils.StringUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 *
 * @author ian
 */
public class CarElement extends AppElement {
    public static String SPRITE_PATH = "images/cars/%s.png";
    public static int WIDTH = 48;
    public static int HEIGHT = 48;
    public static final int[] ORIGIN_PALETTE = new int[] {
            0x00000000, 0xff000000, 0xffFFB059, 0xff003973, 
            0xff085242, 0xff215A10, 0xff600000, 0xff00FF00, 
            0xff001084, 0xff08008C, 0xff42007B, 0xff63005A, 
            0xff6B0010, 0xff005A21, 0xff4F3500, 0xff314E18};
    public static int CAR_COLOR_OFFSET = 8;
    public static int TIRE_COLOR_OFFSET = 13;
    public static int[] TIRE_COLORS = new int[] {
        0xff424142, 0xff292829, 0xff101010
    };
    
    
    protected IndexedImage cars[];
    protected BufferedImage shadow;
    protected BufferedImage paletteBuffer;
    protected int[] paletteBufferPixels;
    protected int palette[];
    

    @Override
    public void load() throws Exception {
        
        // load the indexed car images
        int numModels = CarModel.All.length;
        cars = new IndexedImage[numModels];
        for(int i = 0; i < numModels; i++) {
            String modelName = StringUtils.nameToPath(CarModel.All[i].getName());
            BufferedImage carImage = ImageUtils.loadSprite(String.format(
                    SPRITE_PATH,
                    modelName));
            
            cars[i] = new IndexedImage();
            cars[i].loadFromImage(carImage, ORIGIN_PALETTE);
        }
        
        
        // initialize the pallete (static colors)
        palette = new int[] {
            0x00000000, 0xff000000, 0xff4A494A, 0xff737173,  
            0xff9C9A9C, 0xffEEF3EE, 0xffAD393A, 0xff525152,
            0, 0, 0, 0,
            0, 0, 0, 0
        };
        
        // initialize a pallete buffer and pixels array
        paletteBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        paletteBufferPixels = ((DataBufferInt) paletteBuffer.getRaster().getDataBuffer()).getData();
        
        // load the shadow
        shadow = ImageUtils.loadSprite(String.format(SPRITE_PATH, "shadow"));
    }
    
    protected void renderShadow(Graphics g, CarState cs) {
        // If the car is off the ground, then draw a shadow below it
        if (cs.z > cs.trackZ) {
            int x = cs.x;
            int y = cs.y + cs.trackZ;
            g.drawImage(shadow, x, y, null);
        }
    }
    
    protected void copyColors(CarState cs) {
        // Copy the car color into the palette
        int[] carColors = cs.color.getColors();
        for(int i = 0; i < carColors.length; i++) {
            palette[CAR_COLOR_OFFSET + i] = carColors[i];
        }
        
        for(int i = 0; i < TIRE_COLORS.length; i++) {
            int tireColorIndex = (i + cs.wheelPosition) % TIRE_COLORS.length;
            palette[TIRE_COLOR_OFFSET + i] = TIRE_COLORS[tireColorIndex];
        }
    }
    
    protected void colorizeCar(CarState cs) {
        int angleIndex = cs.frame;
        if (cs.isHFlip()) {
            angleIndex = 24 - cs.frame;
        }
        int carIndex = ArrayUtils.indexOfObject(CarModel.All, cs.model);
        cars[carIndex].render(palette, paletteBufferPixels, angleIndex * WIDTH * HEIGHT, WIDTH * HEIGHT);
    }
    
    protected void renderColorizedCar(Graphics g, CarState cs) {
        int x, y, w, h;
        x = cs.x;
        w = WIDTH;
        y = cs.y + cs.z;
        h = HEIGHT;
        
        // flip for some angles
        if (cs.isHFlip()) {
            x = x + w;
            w = -w;
        }
        g.drawImage(paletteBuffer, x, y, w, h, null);
    }
    
    public void render(Graphics g, CarState cs) {
        // render the shadow if required
        renderShadow(g, cs);
        
        // copy the colors into the palette
        copyColors(cs);
        
        // Draw the car to the pallete buffer
        colorizeCar(cs);
        
        // Draw the pallete bufer to the screen
        renderColorizedCar(g, cs);
    }
    
    
    public static class IndexedImage {
        
        protected byte pixels[];
        
        public void loadFromImage(BufferedImage image, int[] palette) {
            // allocate storage for the pixel data
            pixels = new byte[image.getWidth() * image.getHeight()];
            
            // initialize the pixel data
            int offset = 0;
            for(int y = 0; y < image.getHeight(); y++) {
                for(int x = 0; x < image.getWidth(); x++) {
                    int color = image.getRGB(x, y);
                    int colorIndex = ArrayUtils.indexOf(palette, color);
                    if (colorIndex == -1) {
                        //System.err.println(String.format("%x", color));
                        colorIndex = 0;
                    }
                    pixels[offset] = (byte) colorIndex;
                    offset++;
                }
            }
        }
        
        public void render(int[] palette, int[] destPixels, int offset, int length) {
            for(int i = 0; i < length; i++) {
                destPixels[i] = palette[pixels[offset + i]];
            }
        }
    }
    
}
