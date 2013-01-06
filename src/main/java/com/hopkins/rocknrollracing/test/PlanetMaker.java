/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.test;

import com.hopkins.rocknrollracing.state.Planet;
import com.hopkins.rocknrollracing.utils.ArrayUtils;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import com.hopkins.rocknrollracing.views.elements.PlanetElement;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author ian
 */
public class PlanetMaker {
    
    public static int[] originPalette = new int[] {
        0xFF101008, 0xFF181810, 0xFF202018, 0xFF303020, 0xFF383820, 0xFF484828, 0xFF505028, 0xFF586028, 
        0xFF686828, 0xFF707028, 0xFF788028, 0xFF888820, 0xFF909820, 0xFF98A018, 0xFFA8B018, 0x00000000
    };
    
    public static int[][] palettes = new int[][] {
        new int[] { 
            0xFF080000, 0xFF180000, 0xFF280000, 0xFF380000, 0xFF480000, 0xFF580000, 0xFF680000, 0xFF680000,
            0xFF780000, 0xFF800000, 0xFF900000, 0xFFA00000, 0xFFB00000, 0xFFC00000, 0xFFD00000,
        },
        new int[] { 
            0xFF000800, 0xFF001800, 0xFF002800, 0xFF003800, 0xFF084800, 0xFF085800, 0xFF086800, 0xFF086800,
            0xFF107800, 0xFF108000, 0xFF109000, 0xFF18A000, 0xFF18B000, 0xFF18C000, 0xFF20D000
        },
        new int[] { 
            0xFF280010, 0xFF300018, 0xFF400020, 0xFF480028, 0xFF580030, 0xFF600038, 0xFF700040, 0xFF800050, 
            0xFF880058, 0xFF980060, 0xFFA00070, 0xFFB00078, 0xFFB80088, 0xFFC80090, 0xFFD800A0
        },
        new int[] { 
            0xFF100020, 0xFF180028, 0xFF200030, 0xFF280040, 0xFF300048, 0xFF300058, 0xFF380060, 0xFF400868, 
            0xFF480878, 0xFF501080, 0xFF501088, 0xFF581898, 0xFF6018A0, 0xFF6820B0, 0xFF7028B8
        },
        new int[] {
            0xFF100020, 0xFF183008, 
            0xFF183808, 0xFF184800, 0xFF184860, 0xFF185000, 0xFF186000, 0xFF286070, 0xFF407880, 0xFF589090, 
            0xFF001048, 0xFF001050, 0xFF001060, 0xFF001870, 0xFF001880, 0xFF002800,
        },
        new int[] {
            0xFF002800, 0xFF183008, 
            0xFF183808, 0xFF184800, 0xFF184860, 0xFF185000, 0xFF186000, 0xFF286070, 0xFF407880, 0xFF589090, 
            0xFF001048, 0xFF001050, 0xFF001060, 0xFF001870, 0xFF589090, 0xFF002800,
        },
        new int[] { 
            0xFF002800, 0xFF080800, 0xFF201800, 0xFF302800, 0xFF403000, 0xFF504000, 0xFF605000, 0xFF706000, 
            0xFF887000, 0xFF987800, 0xFFA88800, 0xFFB89800, 0xFFC8A800, 0xFFD8B800, 0xFFF0C800, 
        },
        new int[] { 
            0xFF000000, 0xFF000800, 0xFF002010, 0xFF003018, 0xFF084020, 0xFF085828, 0xFF106838, 0xFF107840,
            0xFF188848, 0xFF18A050, 0xFF20B060, 0xFF20C068, 0xFF28D870, 0xFF28E878, 0xFF30F888, 
 
        },
    };
    
    public static void main(String args[]) throws Exception {
        PlanetElement p = new PlanetElement();
        p.load();
        String path = p.getFilename(Planet.ChemVI);
        BufferedImage origin = ImageUtils.loadSprite(path);
        int pixels[] = new int[origin.getWidth() * origin.getHeight()];
        int pos = 0;
        for(int y = 0; y < origin.getHeight(); y++) {
            for(int x = 0; x < origin.getWidth(); x++) {
                int color = origin.getRGB(x, y);
                pixels[pos] = ArrayUtils.indexOf(originPalette, color);
                pos++;
            }
        }

        BufferedImage bi = new BufferedImage(origin.getWidth(), origin.getHeight(), BufferedImage.TYPE_INT_ARGB);
       
        for(int i = 0; i < palettes.length; i++) {
            pos = 0;
            for (int y = 0; y < origin.getHeight(); y++) {
                for (int x = 0; x < origin.getWidth(); x++) {
                    int color = 0x00000000;
                    if (pixels[pos] < 15) {
                        color = palettes[i][pixels[pos]];
                    }
                    bi.setRGB(x, y, color);
                    pos++;
                }
            }
            ImageIO.write(bi, "PNG", new File(String.format("planet-%d.png", i)));
        }
        
        
    }
    
    
}
