/*
 * SunSpotHostApplication.java
 *
 * Created on Oct 29, 2014 5:26:29 PM;
 */

package org.sunspotworld;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.demo.TheFrame;
import aurelienribon.slidinglayout.demo.ThePanel;
import aurelienribon.slidinglayout.demo.UserPanel;
import aurelienribon.slidinglayout.demo.LongTask;
import aurelienribon.tweenengine.Tween;
import java.awt.*;

import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.peripheral.radio.IRadioPolicyManager;
import com.sun.spot.io.j2me.radiostream.*;
import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.io.j2me.tinyos.TinyOSRadioConnection;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;

import java.io.*;
import java.util.Vector;
import javax.microedition.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Sample Sun SPOT host application
 */
public class SunSpotHostApplication {

    public static String[] layer_type={ "Light","Temperature"};
    public static TheFrame frame;
    public static LongTask task=new LongTask();
    public static Vector<Short> currentValues = new Vector<Short>();
    public static int OurHC=0;
    public static int BruteHC=0;

    //// ********************* TOP Code *********************************   
    public static int sensor_type = -1;
    public static Vector<Short> lsensor_all = null;
    public static Vector<Short> tsensor_all = null;
    
    /**
     * Print out our radio address.
     */
    public void run() { 
      long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
      System.out.println("*** Startup the Base Station Program ***");        
      System.out.println("Our radio address = " + IEEEAddress.toDottedHex(ourAddr));
      //System.exit(0);
      //startReceiverThread(); //start the reciver thread to listen
      Tiny_connection base_broadcast = new Tiny_connection(Constants.BROADCAST_ID,
                                                           Constants.CONNECTION_PORT,
                                                           Constants.TELOSB_NODES);
      // send the reset message
      System.out.println("Send the reset message");
      base_broadcast.send_reset();
      base_broadcast.close();
      base_broadcast = null;
      
      Utils.sleep(100); // do not delete this line      
      
      //initialize();
        /*****GUI*////////////////////
        Tween.registerAccessor(ThePanel.class, new ThePanel.Accessor());
	SLAnimator.start();
        frame = new TheFrame(initialScreenSize());  //initilize the screen based on desktop resolution
    }

    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) {
        SunSpotHostApplication app = new SunSpotHostApplication();
        app.run(); //this will set the application off
    }

  public static void startReceiverThread2() {
    new Thread() {
      public void run() {
        Tiny_connection rx_broadcast = new Tiny_connection(null,
                                                           Constants.CONNECTION_PORT,
                                                           Constants.TELOSB_NODES);
        int temp = 0;
        if(sensor_type == 2) {
          lsensor_all = new Vector<Short>(36);
          tsensor_all = null;
        }
        
        if(sensor_type == 3) {
          tsensor_all = new Vector<Short>(36);
          lsensor_all = null;
        }
        
        while(true) {
          Rx_package pck_rx = null;
          Short x = 0;
          do {
            pck_rx = rx_broadcast.receive();
          } while(pck_rx == null);
          
          System.out.println("Rx type: " + pck_rx.get_pck_type());
          if((sensor_type == 2) && (pck_rx.get_pck_type() == 8))
          {
            int node_index = pck_rx.get_node_index();
            lsensor_all.set(pck_rx.get_node_index(), pck_rx.get_payload()[0]);
            System.out.println("node index: " + node_index + 
                               " val: " + pck_rx.get_payload()[0]);
          } else if((sensor_type == 3) && (pck_rx.get_pck_type() == 9))
          {
            int node_index = pck_rx.get_node_index();
            tsensor_all.set(pck_rx.get_node_index(), pck_rx.get_payload()[0]);
            System.out.println("node index: " + node_index + 
                               " val: " + pck_rx.get_payload()[0]);
            
          } else if(pck_rx.get_pck_type() == 7)
          {
            //print_data_dis(pck_rx);
            temp++;
            // if(temp == 10)
            // {
            //   if(sensor_type == 2)
            //   {
            //     sensor_type = 3;
            //     lsensor_all = null;
            //     tsensor_all = new int[36];
            //     System.out.println("Sensor_type = 2 light -> = 3 temp");
            //   } else if(sensor_type == 3)
            //   {
            //     sensor_type = 2;
            //     lsensor_all = new int[36];
            //     tsensor_all = null;
            //     System.out.println("Sensor_type = temp -> = 2 light");            
            //   }

            // data = new int[]{10, sensor_type};
            // pck = new Rx_package(1, -1, T1205_ID, data);
            // rx_broadcast.send(1, pck, null);
            // System.out.println("Send a new setup package");
            // temp = 0;
          }      
          System.out.println("Temp " + temp + " sensor_type " + sensor_type);
        }
      }
    }.start();
  }

  // protected static void print_data_dis(Rx_package pck) {
      
  //   short[] data = pck.get_payload();
  //   if(sensor_type == 2)
  //     System.out.println("Dis --- Light Sensor Data ---");
  //   else if(sensor_type == 3)
  //     System.out.println("Dis --- Temperature Sensor Data ---");
  //   else if(sensor_type == 4)
  //     System.out.println("Dis --- Temperature & Light Sensor Data ---");
                
  //   int t_cluster = 0;
  //   int t_cl_id = 0;
  //   for(int i = 0; i < 32; i++, t_cl_id++)
  //   {
  //     if((i != 0) & (i % 8 == 0))
  //     {
  //       t_cluster++;
  //       t_cl_id = 0;
  //     }
      
  //     if(sensor_type == 2)
  //     {
  //       System.out.println("cl: " + t_cluster + " cl id: " + t_cl_id + " = dis: " + 
  //                          data[i] + " cen: " + lsensor_all[i]);
  //       if(i == 31)
  //       {
  //         System.out.println("cl: 1. S:0x7EBA cen: " + lsensor_all[32]);
  //         System.out.println("cl: 1. S:0x7F45 cen: " + lsensor_all[33]);
  //         System.out.println("cl: 1. S:0x79A3 cen: " + lsensor_all[34]);
  //         System.out.println("cl: 1. S:0x7997 cen: " + lsensor_all[35]);
  //       }
  //     } else if(sensor_type == 3)
  //     {        
  //       long temp = raw_temp_to_c(data[i]);
  //       System.out.println("cl: " + t_cluster + " cl id: " + t_cl_id + " = dis: " + 
  //                          data[i] + " cen: " + tsensor_all[i] + " : C = " + temp);
  //       if(i == 31)
  //       {
  //         System.out.println("cl: 1. S:0x7EBA cen: " + tsensor_all[32] + 
  //                            " : C = " + raw_temp_to_c(tsensor_all[32]));
  //         System.out.println("cl: 1. S:0x7F45 cen: " + tsensor_all[33] + 
  //                            " : C = " + raw_temp_to_c(tsensor_all[33]));
  //         System.out.println("cl: 1. S:0x79A3 cen: " + tsensor_all[34] + 
  //                            " : C = " + raw_temp_to_c(tsensor_all[34]));
  //         System.out.println("cl: 1. S:0x7997 cen: " + tsensor_all[35] + 
  //                            " : C = " + raw_temp_to_c(tsensor_all[35]));
  //       }        
  //     }
  //   }
  // }
  
    /////****GUI *//////////////////////////////////////
    Dimension initialScreenSize(){
        Dimension currentScreen = Toolkit.getDefaultToolkit().getScreenSize();
        currentScreen.height = (int)(0.9555 * currentScreen.height);
        //currentScreen.width = (int) (0.9 * currentScreen.width);
        System.out.println(currentScreen.width+" "+currentScreen.height);
        return currentScreen;
    }
    
       
  // Objective: a function for spots to send various type of messages
  // @type: int type of packages
  // @values_last_index: int the last index of the data in the values
  // @values: int payload
  // @address: String 16 digit addresses of the destination
  public static void sendMessage(int type, int values_last_index, int[] values, String address){
    TinyOSRadioConnection conn = null;
    try {
      conn = (TinyOSRadioConnection) Connector.open("tinyos://"+address+":" 
                                                    + Constants.CONNECTION_PORT);
    } catch (IOException ex) {
      System.out.println("Could not open radiogram connection to " + address);
      ex.printStackTrace();
      return;
    }
    try {
      Datagram dg = conn.newDatagram(conn.getMaximumLength());
      dg.reset();
      dg.writeByte(type);
      for(int i=0; i<= values_last_index; i++)
        dg.writeShort(values[i]);
        
      for(int i=values_last_index + 1; i< values.length; i++)
        dg.writeShort(Constants.TERMINATOR);
      
      conn.send(dg);
      System.out.println("sent the data to " + address);
    } catch (IOException ex) {
      System.err.println("Problem while sending message");
      ex.printStackTrace();
    }
    finally{
      try{
        conn.close();
      } catch (IOException ex){
        System.err.println("Problem while closing the connection");
        ex.printStackTrace();        
      }
    }
  }

  public static long raw_temp_to_c(short raw) {
    return (long) (-38.4 + 0.0098 * ((long) raw));
  }

    void initialize(){
        //setting up the sliding GUI
        for(int i=0; i<Constants.TOTAL_MOTES;i++){
            short t=40;
            currentValues.add(t);
        }
    }
}
