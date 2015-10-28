/*
 * To change this license header, choose License Headers in Project Properties
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import com.sun.spot.io.j2me.tinyos.TinyOSRadioConnection;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.resources.Resources;
/*import com.sun.spot.service.BootloaderListenerService;*/
import com.sun.spot.util.IEEEAddress;

import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import java.io.IOException;
/**
 *
 * @author PanitanW
 */

public class Tiny_connection 
{
  private TinyOSRadioConnection tiny_connection = null;
  private boolean is_broadcast = false;
  private Datagram dg = null;
  private String dst_addr = null;
  private int port = -1;
  private final int broadcast_constant = 0xFF;
  private String[] telosb_nodes = null;
  
  // constructor
  public Tiny_connection(String dst_addr, int port, String[] nodes) {
    this.dst_addr = dst_addr;
    this.port = port;
    this.telosb_nodes = nodes;
    
    if(dst_addr == null)
      open_broadcast(port);
    else
      open(dst_addr, port);
  }

  public String node_addr_dot_hex() {
    long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
    return IEEEAddress.toDottedHex(ourAddr);
  }
  
  public boolean open(String dst_addr, int port) {
    if(tiny_connection == null)
    {
      try
      {
        System.out.println("Open Connection: dst: " + dst_addr + " port: " +
                           String.valueOf(port));
        // broadcast the threshold
        tiny_connection = (TinyOSRadioConnection) Connector.open("tinyos://" + dst_addr + ":" + port);
        // Then, we ask for a datagram with the maximum size allowed
        dg = tiny_connection.newDatagram(tiny_connection.getMaximumLength());
        return true;
      } catch (IOException e) {
        System.out.println("Could not open tinyos broadcast connection");
        e.printStackTrace();
      }
    }
    
    return false;
  }

  public boolean open_broadcast(int port) {
    if(tiny_connection == null)
    {
      try
      {
        System.out.println("$TC Open Broadcast port: " + String.valueOf(port));
        // broadcast the threshold
        tiny_connection = (TinyOSRadioConnection) Connector.open("tinyos://:" + port);
        // Then, we ask for a datagram with the maximum size allowed
        dg = tiny_connection.newDatagram(tiny_connection.getMaximumLength());
        is_broadcast = true;
        return true;
      } catch (IOException e) {
        System.out.println("$TC Could not open tinyos broadcast connection");
        e.printStackTrace();
      }
    }
    return false;
  }
  
  public boolean send_reset() {
    if((tiny_connection != null) && (dg != null))
    {
      try {
        // We send the message (UTF encoded)
        dg.reset();
        dg.writeByte(0);
        dg.writeByte(broadcast_constant);
        tiny_connection.send(dg);
        System.out.println("Reset Sent");
        return true;
      } catch (IOException ex) {
        System.out.println("$TC Could not send the reset pck!!!");
        ex.printStackTrace();
      }
    }

    return false;
  }

  public boolean send(int pck_type, Rx_package pck_rx, short[] more_data)
  { // forward the data to telosb    
    if((tiny_connection != null) && (dg != null))
    {
      try {
        // We send the message (UTF encoded)
        dg.reset();
        switch(pck_type)
        {
          case 1:
            // forward the setup data to telosb nodes
            dg.writeByte(1);
            dg.writeByte(pck_rx.get_payload()[0]);
            dg.writeByte(pck_rx.get_payload()[1]);
            break;
            
          case 5:
            dg.writeByte(5);
            for(int i = 0; i < pck_rx.get_payload().length; i++)
              dg.writeShort(pck_rx.get_payload()[i]);
            break;
            
          case 6:
            dg.writeByte(6);
            for(int i = 0; i < more_data.length; i++)
              dg.writeShort(more_data[i]);

            for(int i = 0; i < pck_rx.get_payload().length; i++)
              dg.writeShort(pck_rx.get_payload()[i]);
            break;
            
          case 7:
            dg.writeByte(7);
            for(int i = 0; i < 8; i++)
              dg.writeShort(pck_rx.get_payload()[i]);
            
            for(int i = 0; i < more_data.length; i++)
              dg.writeShort(more_data[i]);

            for(int i = 8; i < pck_rx.get_payload().length; i++)
              dg.writeShort(pck_rx.get_payload()[i]);
            break;
        }
        tiny_connection.send(dg);
        System.out.println("$TC Sent Pck type: " + pck_type);
        return true;
      } catch (IOException ex) {
        System.out.println("$TC Could not send the reset pck!!!");
        ex.printStackTrace();
      }
    }

    return false;
  }
  
  public Rx_package receive() {
    Rx_package res = null;
    try
    {
      dg.reset();
      System.out.println("Waiting for the data ");
      tiny_connection.receive(dg);             // a blocking call
      String dst_addr = dg.getAddress();
      int pck_type = dg.readByte();      
      System.out.println("\n\n\nReceive a package from " + dst_addr);
      
      int node_index = Constants.getNodeId(last_4addr(dst_addr));
      if(node_index == -1)
      {
        System.out.println(" Node Index: " + node_index + ", not in the network.");
        return null; // receive from other nodes not in the same cluster
      }
    
      if(pck_type == 0)
      {
        short[] data = new short[1];
        data[0] = (short) dg.readByte();
        res = new Rx_package(pck_type, node_index, dst_addr,
                             data);
      } else if(pck_type == 1)
      { // update period and pck type
        short[] temp = new short[2];
        temp[0] = (short) dg.readByte();
        temp[1] = dg.readByte();
        res = new Rx_package(pck_type, node_index, dst_addr,
                             temp);
      } else if((pck_type == 2) || (pck_type == 3))
      { // either light or temp sensor data 
        short[] temp = new short[1];
        temp[0] = dg.readShort();
        res = new Rx_package(pck_type, node_index, dst_addr,
                             temp);
      } else if(pck_type == 4)
      { // both temp and light sensors
        short[] temp = new short[2];
        temp[0] = dg.readShort();
        temp[1] = dg.readShort();
        res = new Rx_package(pck_type, node_index, dst_addr,
                             temp);        
      } else if(pck_type == 5)
      {
        short[] temp = new short[8];
        // System.out.println("=== Pck type: 5 Content ====");
        for(int i = 0; i < temp.length; i++)
        {
          temp[i] = dg.readShort();
          // System.out.println("==== i: " + i + ":" + temp[i] + " ====");
        }
        // System.out.println("==== Pck_type 5 End");
        res = new Rx_package(pck_type, node_index, dst_addr,
                             temp);                
      } else if(pck_type == 6)
      {
        short[] temp = new short[1];
        temp[0] = dg.readByte();
        res = new Rx_package(pck_type, node_index, dst_addr,
                             temp);                        
      } else if(pck_type == 7)
      {
        short[] temp = new short[32];
        for(int i = 0; i < 32; i++)
          temp[i] = dg.readShort();
        res = new Rx_package(pck_type, node_index, dst_addr, temp);
        
      } else if((pck_type == 8) || (pck_type == 9))
      {
        short[] temp = new short[1];
        temp[0] = dg.readShort();
        res = new Rx_package(pck_type, node_index, dst_addr, temp);
      }
    } catch (IOException e) {
      System.out.println("Nothing Received");
      res = null;
    }
    return res;
  }
  
  public void close() {
    if(tiny_connection != null)
    {
      if(is_broadcast)
        System.out.println("Close the broadcast connection");
      else
        System.out.println("Close T: " + dst_addr + " connection");
    
      try {
        // error handling for radio broadcasting
        tiny_connection.close();
        tiny_connection = null;
        dg = null;
        is_broadcast = false;
        port = -1;
      } catch (IOException e) {
        System.out.println("Close the connection");
        e.printStackTrace();
      }
    }
  }
  
  public String last_4addr(String addr) {
    // "0014.4F01.0000.7F42" -> "7F42"
    return addr.substring(15, addr.length());
  }
}
