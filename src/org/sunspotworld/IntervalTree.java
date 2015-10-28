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
public class IntervalTree
{
  double discriminant;
  IntervalTree left_child;
  IntervalTree right_child;
  Window window;
  short maxscore; // not sure
  Window target;
  short excess;   // not sure
  IntervalTree father;
  
  public IntervalTree(double discriminant, IntervalTree father)
  {
    this.discriminant = discriminant;
    this.left_child = null;
    this.right_child = null;
    this.window = null;
    this.maxscore = 0;
    this.target = null;
    this.excess = 0;
    this.father = father;
  }

  @Override
  public String toString()
  {
    StringBuilder result = new StringBuilder();
    // String newLine = System.getProperty("line.separator");
    result.append("--------------------\r\n");
    result.append("d: " + String.valueOf(this.discriminant) +
                  " excess: " + String.valueOf(this.excess) +
                  " maxscore: " + String.valueOf(this.maxscore) + "\r\n");
    
    if(this.father != null)
      result.append("father: " + String.valueOf(this.father.discriminant));
    else
      result.append("father: null");
    
    if(this.left_child != null)
      result.append(" left_ch: " + String.valueOf(this.left_child.discriminant));
    else
      result.append(" left_ch: null");

    if(this.right_child != null)
      result.append(" right_ch: " + String.valueOf(this.right_child.discriminant)
                    + "\r\n");
    else
      result.append(" right_ch: null\r\n");

    result.append("win: " + this.window + " target: " + this.target + "\r\n");
    result.append("--------------------\r\n");
    return result.toString();
  }  
}