/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.HasFace;
import com.hopkins.rocknrollracing.state.NPC;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import com.hopkins.rocknrollracing.utils.Inflector;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author ian
 */
public class FaceElement {
    
    public static final Logger log = Logger.getLogger(FaceElement.class);
    
    public static final String SPRITE_PATH = "images/faces/%s.png";
    
    
    protected HashMap<HasFace, BufferedImage> cache;
    
    public FaceElement() {
        cache = new HashMap<HasFace, BufferedImage>();
    }
    
    protected BufferedImage loadFaceSprite(HasFace c) {
        String filename = String.format(SPRITE_PATH,
                Inflector.underscore(c.getName()));
        
        try {
            return ImageUtils.loadSprite(filename);
        } catch (ImageUtils.ImageLoadException ex) {
            log.error("Could not load Face Sprite for Character: " + c.getName(), ex);
            return AppElement.EMPTY;
        }
    }
    
    protected BufferedImage getFaceSprite(HasFace c) {
        if (!cache.containsKey(c)) {
            cache.put(c, loadFaceSprite(c));
        }
        return cache.get(c);
    }
    
    public void render(Graphics g, int x, int y, HasFace c) {
        
        // shadow behind the face
        g.setColor(PanelElement.BOX_SHADOW);
        g.fillRoundRect(x + 8, y + 8, 64, 64, 32, 32);
        
        // the face
        g.drawImage(getFaceSprite(c), x, y, null);
    }
    
    
}
