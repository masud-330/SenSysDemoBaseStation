/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld;

/**
 *
 * @author Muhammed
 */
public class CoOccurencePredicate {
    public ShapePredicate sp1;
    public ShapePredicate sp2;
    public int distance;
    
    public CoOccurencePredicate(ShapePredicate ec1in, ShapePredicate ec2in, int disin)
    {
        sp1=ec1in;
        sp2=ec2in;
        distance=disin;
    }
}
