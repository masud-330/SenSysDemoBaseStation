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
public class Window implements Comparable<Window>
{ // root.window = Window(xs[0],xs[-1],-5,0)
  public short l;
  public short r;
  public short h;
  public short score;

  public Window(short l, short r, short h, short score)
  {
    this.l = l;
    this.r = r;
    this.h = h;
    this.score = score;
  }
  
  public Window clone()
  { //Deep copy
    Window clone = new Window(this.l, this.r, this.h, this.score);
    return clone;
  }

  @Override
  public String toString()
  {
    StringBuilder result = new StringBuilder();
    // String newLine = System.getProperty("line.separator");
    result.append("win[l: " + String.valueOf(this.l) +
                  " r: " + String.valueOf(this.r) +
                  " h: " + String.valueOf(this.h) +
                  " s: " + String.valueOf(this.score) + "]");
    return result.toString();
  }
  
    public int compareTo(Window o)
    { // used in the collections.sort()
        if(this.h < o.h)
            return 1;  // more than the one we are checking 
        else if(this.h == o.h)
            return 0;  // equal to the one we are checking
        else
            return -1; // less then the one we are checking 
  }
}