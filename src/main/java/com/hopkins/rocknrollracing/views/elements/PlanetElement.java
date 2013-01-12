/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views.elements;

import com.hopkins.rocknrollracing.state.Planet;
import com.hopkins.rocknrollracing.utils.ArrayUtils;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import com.hopkins.rocknrollracing.utils.MathUtils;
import com.hopkins.rocknrollracing.utils.StringUtils;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author ihopkins
 */
public class PlanetElement extends AppElement {
    
    public static final String SPRITE_PATH_FORMAT = "images/planets/%s.png";
    public static final String STARS_SPRITE_PATH = "images/planets/stars.png";
    public static final double RADIANS_PER_DEGREE = 0.0174532925;
    
    public static final int PLANET_WIDTH = 62;
    public static final int PLANET_HEIGHT = 62;
    
    public static final int STAR_WIDTH = 5;
    public static final int STAR_HEIGHT = 5;
    public static final int STAR_COUNT = 5;
    
    public static final double LARGE_SCALE = 96.0/62.0;
    
    protected BufferedImage[] heroPlanets;
    protected BufferedImage[] enemyPlanets;
    protected BufferedImage stars;

    @Override
    public void load() throws Exception {
        heroPlanets = new BufferedImage[Planet.HeroPlanets.length];
        enemyPlanets = new BufferedImage[Planet.EnemyPlanets.length];
        int i;
        String filename;
        Planet planet;
                
        // Load the planets
        for(i = 0; i < heroPlanets.length; i++) {
            planet = Planet.HeroPlanets[i];
            filename = getFilename(planet);
            heroPlanets[i] = ImageUtils.loadSprite(filename);
        }
        for(i = 0; i < enemyPlanets.length; i++) {
            planet = Planet.EnemyPlanets[i];
            filename = getFilename(planet);
            enemyPlanets[i] = ImageUtils.loadSprite(filename);
        }
        
        // Load the stars
        stars = ImageUtils.loadSprite(STARS_SPRITE_PATH);
    }
    
    public String getFilename(Planet p) {
        return String.format(SPRITE_PATH_FORMAT,
                StringUtils.nameToPath(p.getName())
                );
    }
    
    protected double getAngleInRadians(int angleInDegrees) {
        return angleInDegrees * RADIANS_PER_DEGREE;
    }
    
    public void renderLarge(Graphics g, int x, int y, int angleInDegrees, Planet planet) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        BufferedImage planetImage = getPlanetImage(planet);
        
        // Rotate the planet
        double radians = getAngleInRadians(angleInDegrees);
        
        // position the planet
        at.translate(x, y);
        
        // scale the planet
        at.scale(LARGE_SCALE, LARGE_SCALE);

        // Rotate the planet around its center
        int cx = (int) (PLANET_WIDTH / 2);
        int cy = (int) (PLANET_HEIGHT / 2);
        at.rotate(radians, cx, cy);
        
        
        // Scale 
        //
        
        // Draw the planet
        g2d.drawImage(planetImage, at, null);
    }
    
    public void renderSmall(Graphics g, int x, int y, Planet planet) {
        g.drawImage(getPlanetImage(planet), x, y, null);
    }
    
    protected BufferedImage getPlanetImage(Planet planet) {
        int pos = ArrayUtils.indexOfObject(Planet.EnemyPlanets, planet);
        if (pos > -1) {
            return enemyPlanets[pos];
        } else {
            pos = ArrayUtils.indexOfObject(Planet.HeroPlanets, planet);
            return heroPlanets[pos];
        }
    }
    
    public void renderStar(Graphics g, int x, int y, int intensity) {
        int frame = MathUtils.forceInRange(intensity - 1, 0, STAR_COUNT - 1);
        
        SpriteRenderer.render(g, stars, x, y, STAR_WIDTH, STAR_HEIGHT, frame, false, false);
    }
    
    
    
}
