/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state.race;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Stores a race car's progress around the track
 * @author ian
 */
public class RaceCarPlace {
    public int LapNumber;
    public int LastCheckPointIndex;
    public long LastCheckPointTime;
    public int Ordinal;
    public int PrevOrdinal;
    
    public RaceCarPlace() {
        LapNumber = 0;
        LastCheckPointIndex = 0;
        LastCheckPointTime = 0;
        Ordinal = 0;
    }
    
    // Called when the car passes a checkpoint
    public void onCheckPoint(int index, long time) {
        if (index == 0) {
            LapNumber++;
        }
        LastCheckPointIndex = index;
        LastCheckPointTime = time;
    }
    
    protected static CarPlaceComparator comparator = new CarPlaceComparator();
    public static void calculate(List<RaceCar> cars) {
        
        // Copy the collection
        ArrayList<RaceCar> sortedCars = new ArrayList<RaceCar>(cars.size());
        sortedCars.addAll(cars);
        
        // sort the cars
        Collections.sort(sortedCars, comparator);
        
        // give the cars ordinals based on their sorted position
        for (int i = 0; i < sortedCars.size(); i++) {
            sortedCars.get(0).Place.Ordinal = (i + 1);
        }
    }
    
    /**
     * Used to sort cars by progress around the track
     */
    public static class CarPlaceComparator implements Comparator<RaceCar> {

        @Override
        public int compare(RaceCar o1, RaceCar o2) {
            return compare(o1.Place, o2.Place);
        }
        
        public int compare(RaceCarPlace rcp1, RaceCarPlace rcp2) {
            
            // Different laps
            if (rcp1.LapNumber != rcp2.LapNumber) {
                return rcp1.LapNumber - rcp2.LapNumber;
            }
            
            // Different checkpoints
            if (rcp1.LastCheckPointIndex != rcp2.LastCheckPointIndex) {
                return rcp1.LastCheckPointIndex - rcp2.LastCheckPointIndex;
            }
            
            // Checkpoint crossing time
            return (int) (rcp1.LastCheckPointTime = rcp2.LastCheckPointTime);
        }
    }
}
