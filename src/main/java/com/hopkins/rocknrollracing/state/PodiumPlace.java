/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ihopkins
 */
public class PodiumPlace {
 
    
    public static final PodiumPlace Gold = new PodiumPlace("Gold", 10000, 400);
    public static final PodiumPlace Silver = new PodiumPlace("Silver", 7000, 200);
    public static final PodiumPlace Bronze = new PodiumPlace("Bronze", 4000, 100);
    public static final PodiumPlace Last = new PodiumPlace("Last", 0, 0);
    
    public static final PodiumPlace[] All = new PodiumPlace[] {Gold, Silver, Bronze, Last};
    
    protected String name;
    protected int prizeMoney;
    protected int points;
    
    public String getName() {
        return name;
    }
    public int getPrizeMoney() {
        return prizeMoney;
    }
    public int getPoints() {
        return points;
    }
    
    public PodiumPlace(String name, int prizeMoney, int points) {
        this.name = name;
        this.prizeMoney = prizeMoney;
        this.points = points;
    }
}
