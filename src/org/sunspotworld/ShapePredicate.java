/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld;

/**
 *
 * @author beSim
 */
public class ShapePredicate {
    
    //phenomenon layer codes for light and temperature
    public static final int TEMPERATURE_LAYER = 1;
    public static final int LIGHT_LAYER = 0;

    public int area;
    public int threshold;
    public int phenomenonLayer;

    public ShapePredicate(int area, int threshold, int phenomenonLayer) {
        this.area = area;
        this.threshold = threshold;
        this.phenomenonLayer = phenomenonLayer;
    }

    public ShapePredicate(){
        area = threshold = phenomenonLayer = 0;
    }

}
