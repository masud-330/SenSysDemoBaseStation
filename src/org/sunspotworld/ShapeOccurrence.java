/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld;
import java.util.Vector;

/**
 *
 * @author Muhammed
 */
public class ShapeOccurrence {
    public ShapePredicate predicate;
    public Vector<Point> points;

    public ShapeOccurrence(Vector points, ShapePredicate predicate) {
        this.predicate = predicate;
        this.points = points;
    }
    
}
