package org.sunspotworld;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author beSim
 */
public final class Point {
    public  int x;    // x-coordinate
    public  int y;    // y-coordinate

    // random point
    public Point() {
        x = 0;
        y = 0;
    }

    // point initialized from parameters
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // accessor methods
    public double x() { return x; }
    public double y() { return y; }
    public double r() { return Math.sqrt(x*x + y*y); }

    // Euclidean distance between this point and that point
    public double distanceTo(Point that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    // return a string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
