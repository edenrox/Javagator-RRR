/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.inject.InjectLoadable;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public abstract class AppElement implements InjectLoadable {
    
    public static final BufferedImage EMPTY = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    public abstract void load() throws Exception;

}
