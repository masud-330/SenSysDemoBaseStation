/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

/**
 *
 * @author PanitanW
 */
public class Rx_package {
  private int pck_type = -1;
  private int node_index = -1;
  private String dst_addr = null;
  private short[] payload = null;

  public Rx_package(int pck_type, int node_index,
                    String dest_addr, short[] data)
  {
    this.pck_type = pck_type;
    this.node_index = node_index;
    
    if(dest_addr != null)
      this.dst_addr = new String(dest_addr);
    
    this.payload = new short[data.length];
    System.arraycopy(data, 0, this.payload, 0, data.length);    
  }

  public int get_pck_type() {
    return this.pck_type;
  }

  public int get_node_index() {
    return this.node_index;
  }

  public String get_dst_addr() {
    return this.dst_addr;
  }

  public short[] get_payload() {
    return this.payload;
  }

  public void set_payload(int i, short val) {
    payload[i] = val;
  }
}

