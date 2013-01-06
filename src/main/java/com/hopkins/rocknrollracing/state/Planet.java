/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ian
 */
public class Planet {
    
    public static final Planet ChemVI = new Planet("Chem VI");
    public static final Planet Drakonis = new Planet("Drakonis");
    public static final Planet Bogmire = new Planet("Bogmire");
    public static final Planet NewMojave = new Planet("New Mojave");
    public static final Planet Nho = new Planet("Nho");
    public static final Planet Inferno = new Planet("Inferno");
    
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
    
    public String getName() {
        return name;
    }
    
    public Planet(String name) {
        this.name = name;
    }
}
