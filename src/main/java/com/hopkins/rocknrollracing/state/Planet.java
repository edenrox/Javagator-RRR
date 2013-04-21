/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

import com.hopkins.rocknrollracing.utils.ArrayUtils;

/**
 *
 * @author ian
 */
public class Planet {
    
    public static final Planet ChemVI = new Planet("Chem VI", 94, "Bring your gas mask to race on this smoggy, overcrowded world.");
    public static final Planet Drakonis = new Planet("Drakonis", 110, "The locals here are very fond of humans ... for dinner.");
    public static final Planet Bogmire = new Planet("Bogmire", 80, "The swampy domain of ragewortt, self proclaimed ruler of everything.");
    public static final Planet NewMojave = new Planet("New Mojave", 115, "Prison planet of the galaxy, victory will not come easy here.");
    public static final Planet Nho = new Planet("Nho", -20, "Cars need special care to race on this icy world.");
    public static final Planet Inferno = new Planet("Inferno", 160, "The toughest planet in the federation. Watch out for lava pools.");
    
    public static final Planet Terra = new Planet("Terra");
    public static final Planet Serpentis = new Planet("Serpentis");
    public static final Planet Fleagull = new Planet("Fleagull");
    public static final Planet PanterosV = new Planet("Panteros V");
    public static final Planet XenoPrime = new Planet("Xeno Prime");
    public static final Planet Aurora = new Planet("Aurora");
    public static final Planet Valhalla = new Planet("Valhalla");
    
    
    public static final Planet[] EnemyPlanets = new Planet[] {
        ChemVI, Drakonis, Bogmire, NewMojave, Nho, Inferno
    };
    
    public static final Planet[] HeroPlanets = new Planet[] {
        Terra, Serpentis, Fleagull, PanterosV, XenoPrime, Aurora, Valhalla
    };
    
    protected String name;
    protected String description;
    protected int temperature;
    
    public String getName() {
        return name;
    }
    public int getTemperature() {
        return temperature;
    }
    public String getDescription() {
        return description;
    }
    
    public Planet(String name) {
        this.name = name;
        this.temperature = 0;
        this.description = "";
    }
    
    public Planet(String name, int temperature, String description) {
        this.name = name;
        this.temperature = temperature;
        this.description = description;
    }
}
