/*
 * SunSpotHostApplication.java
 *
 * Created on Oct 29, 2014 5:26:29 PM;
 */

package org.sunspotworld;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.demo.LongTask;
import aurelienribon.slidinglayout.demo.TheFrame;
import aurelienribon.slidinglayout.demo.ThePanel;
import aurelienribon.slidinglayout.demo.UserPanel;
import aurelienribon.tweenengine.Tween;
import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.io.j2me.radiostream.*;
import com.sun.spot.io.j2me.tinyos.TinyOSRadioConnection;
import com.sun.spot.peripheral.radio.IRadioPolicyManager;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;
import java.awt.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.microedition.io.*;


/**
 * Sample Sun SPOT host application
 */
public class SunSpotHostApplication {

  public static String[] layer_type={"Light","Temperature"};
  public static TheFrame frame;
  public static LongTask task=new LongTask();
  public static Vector<Short> currentValues = new Vector<Short>();
  public static short enclosed_objects_no = 0;
  public static short time_period = 0;
  public static Window Opt_Window = null;
  public static int OurHC=0;
  public static int BruteHC=0;
  public static short current_phenomena = -1;
  //centralised processing
  public static Vector<Object> currentObjects = new Vector<Object>();
  public static Vector<Rectangle> currentRectangle = new Vector<Rectangle>(); 
  // the currentRectangle variable is not used by any file/class somewhere else.
  public static Area area;
  public static Area coverage; // width and height of the rectangle
  public static int base_id = -1; // last 4 digits of the base id
  /**
   * Print out our radio address.
   */
  public void run() { 
    long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
    String base_addr = IEEEAddress.toDottedHex(ourAddr);
    System.out.println("*** Startup the Base Station Program ***");        
    System.out.println("Our radio address = " + base_addr);
    base_id = Integer.parseInt(base_addr.substring(15), 16);
    initialize();
    reset_telosb();
    Utils.sleep(100); // do not delete this line      
    System.out.println("Start the receiver thread");
    startReceiverThread();
    //System.exit(0);
    //-------------------------------------------------------------------------------
    //TODO: Paste Top's Code here 
    //startReceiverThread(); //start the reciver thread to listen
    //-------------------------------------------------------------------------------
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
    
  ///////////////////////////////////TOP Receiver Function////////////////////////////////////////////////////////
    
  public static void startReceiverThread() {
    new Thread("Updating Thread") {
      public void run() {
        thread_message("In the updating thread");
        Tiny_connection rx_broadcast = new Tiny_connection(null,
                                                           Constants.CONNECTION_PORT,
                                                           Constants.TELOSB_NODES);

        while(true) {
          Rx_package pck_rx = null;
          do {
            thread_message("Waiting for the data");
            pck_rx = rx_broadcast.receive();
            // receive the centralized data from sensors
          } while(pck_rx == null);

          thread_message("Rx type: " + pck_rx.get_pck_type());     
           
          // save the data based on the sensor id and the data type
          if((pck_rx.get_pck_type() == 8) || (pck_rx.get_pck_type() == 9))
          {
            short node_index = (short) pck_rx.get_node_index();
            thread_message("node index: " + node_index + 
                           " val: " + pck_rx.get_payload()[0]);    
             
            if(pck_rx.get_pck_type() == 8)
              currentValues.set(node_index, pck_rx.get_payload()[0]);
            else if(pck_rx.get_pck_type() == 9) {
              if(Constants.isTelos(node_index)) {
                double temp_c = raw_temp_to_c(pck_rx.get_payload()[0]);
                short farenheit = (short) (((9.0*temp_c)/5.0)+32.0);
                currentValues.set(node_index, farenheit);
                thread_message("node i: " + node_index + 
                               " f local: " + farenheit + 
                               " f saved: " + currentValues.elementAt(node_index));
              } else 
                currentValues.set(node_index, pck_rx.get_payload()[0]);
            }  
          } else if(pck_rx.get_pck_type() == 7)
            Opt_Window = pck_rx.get_window();
            OurHC += Constants.DIS_HOP_COUNT;
            BruteHC += Constants.CEN_HOP_COUNT;
            System.out.println(OurHC+" "+BruteHC);
          
        }
      }
    }.start();
  }

  // Objective: a function for spots to send various type of messages
  // @type: int type of packages
  // @values_last_index: int the last index of the data in the values
  // @values: int payload
  // @address: String 16 digit addresses of the destination
  public static void sendMessage(int type, int values_last_index, 
                                 int[] values, String address) {
    TinyOSRadioConnection conn = null;
    try {
      System.out.println("Open Connection to: " + address);
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

      for(int i=values_last_index + 1; i < values.length; i++)
        dg.writeShort(Constants.TERMINATOR);

      conn.send(dg);
      System.out.println("sent the data to " + address);
    } catch (IOException ex) {
      System.err.println("Problem while sending message");
      ex.printStackTrace();
    }
    finally{
      try{
        System.out.println("Close Connection to: " + address);  
        conn.close();
      } catch (IOException ex){
        System.err.println("Problem while closing the connection");
        ex.printStackTrace();        
      }
    }
  }

  public static double raw_temp_to_c(short raw) {
    return (double) (-38.4 + 0.0098 * ((double) raw));
  }

  static void thread_message(String message) {
    String thread_name = Thread.currentThread().getName();
    System.out.println("*** T: " + thread_name + " : " + message);
  }
    
  /////****GUI *//////////////////////////////////////
  Dimension initialScreenSize(){
    Dimension currentScreen = Toolkit.getDefaultToolkit().getScreenSize();
    currentScreen.height = (int)(0.9555 * currentScreen.height);
    //currentScreen.width = (int) (0.9 * currentScreen.width);
    System.out.println(currentScreen.width+" "+currentScreen.height);
    return currentScreen;
  }
    
  // set up all initial values
  void initialize(){
    Constants.setAddresIDMapping();
    area = new Area(Constants.AREA_WIDTH, Constants.AREA_HEIGHT);
    coverage = new Area((short)80, (short)80);
    short def_val=40;
    if(current_phenomena == Constants.TEMP_PHENOMENA){
      def_val=0;  // fine with the gui bug
    }
    //setting up the sliding GUI
    for(int i=0; i<Constants.MAX_ID;i++){
      short t=0;
      currentValues.add(t);
    }
        
    short offsetw = Constants.AREA_WIDTH/7;
    short offseth = Constants.AREA_HEIGHT/7;
    for(int i=0; i<Constants.MAX_ID;i++) {
      int indw = i%6;
      int indh = i/6;
      currentObjects.add(new Object((short)(offsetw+(indw*offsetw)),(short)(offseth+(indh*offseth)),def_val));
    }
    
    for(Object obj : currentObjects){
      currentRectangle.add(new Rectangle((short)Math.max(0, obj.x - coverage.width/2),
                                         (short)Math.max(0, obj.y - coverage.height/2),
                                         (short)Math.min(area.width,
                                                         obj.x + coverage.width/2),
                                         (short)Math.min(area.height,
                                                         obj.y + coverage.height/2),
                                         obj.weight));
    }
  }
  
  // set the setup packet to the nodes
  public static void send_setup() {
    int[] arr = new int[Constants.VALUE_SIZE];
    arr[0]= base_id; 
    arr[1]= time_period;
    arr[2]= current_phenomena; // waiting for the sensor type
    arr[3]= coverage.width;
    arr[4]= coverage.height;
    // send the setup package to other nodes
    sendMessage(15, 4, arr, Constants.T1205_ID);
    System.out.println("Sent the setup");
  }

  // set the reset packet to the telosbs to reset themselves
  public static void reset_telosb() {
    Tiny_connection base_broadcast = new Tiny_connection(Constants.BROADCAST_ID,
                                                         Constants.CONNECTION_PORT,
                                                         Constants.TELOSB_NODES);
    // send the reset message
    System.out.println("Send the reset message");
    base_broadcast.send_reset();
    base_broadcast.close();
    base_broadcast = null;
  }
}

