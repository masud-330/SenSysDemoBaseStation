package org.sunspotworld;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author beSim
 */
public final class Value {
    public int light;    // x-coordinate
    public int temperature;    // y-coordinate

    // random point
    public Value() {
        light = 0;
        temperature = 0;
    }

    // point initialized from parameters
    public Value(int x, int y) {
        this.light = x;
        this.temperature = y;
    }

    // accessor methods
    public double light() { return light; }
    public double temperature() { return temperature; }
    //public double r() { return Math.sqrt(x*x + y*y); }

    // return a string representation of this point
    public String toString() {
        return "(" + light + ", " + temperature + ")";
    }

}
