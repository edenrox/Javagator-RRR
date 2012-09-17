/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.model;

/**
 *
 * @author ian
 */
public class NamedModels {
    
    public NamedModelLookup<CarModel> carModels;
    public NamedModelLookup<CarModelColor> carModelColors;
    
    public void load() throws Exception {
        carModels = new NamedModelLookup<CarModel>();
        carModels.load(CarModel.class);
        
        carModelColors = new NamedModelLookup<CarModelColor>();
        carModelColors.load(CarModelColor.class);
    }
    
    
}
