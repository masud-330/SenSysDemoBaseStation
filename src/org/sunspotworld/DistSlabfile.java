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
public class DistSlabfile {
    Vector<Window> hintervals = new Vector<Window>();
    Vector<Short> neededValues = new Vector<Short>();
    
    DistSlabfile(Vector<Window> hi, Vector<Short> nv){
        hintervals = hi;
        neededValues = nv;
    }
}
