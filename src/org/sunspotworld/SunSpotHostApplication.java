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
    public static Window Opt_Window = new Window((short)141,(short)141,(short)197,(short)654);
    public static int OurHC=0;
    public static int BruteHC=0;
    public static short current_phenomena = -1;
    //centralised processing
    public static Vector<Object> currentObjects = new Vector<Object>();
    public static Vector<Rectangle> currentRectangle = new Vector<Rectangle>();
    public static Area area;
    public static Area coverage;
    
    /**
     * Print out our radio address.
     */
    public void run() { 
      long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
      System.out.println("*** Startup the Base Station Program ***");        
      System.out.println("Our radio address = " + IEEEAddress.toDottedHex(ourAddr));
      
      Tiny_connection base_broadcast = new Tiny_connection(Constants.BROADCAST_ID,
                                                           Constants.CONNECTION_PORT,
                                                           Constants.TELOSB_NODES);
      // send the reset message
      System.out.println("Send the reset message");
      base_broadcast.send_reset();
      base_broadcast.close();
      base_broadcast = null;
      
      Utils.sleep(100); // do not delete this line      
      System.out.println("Start the receiver thread");
      startReceiverThread();
        //System.exit(0);
        //-------------------------------------------------------------------------------
        //TODO: Paste Top's Code here 
        //startReceiverThread(); //start the reciver thread to listen
        //-------------------------------------------------------------------------------
        initialize();
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
             //Tiny_connection rx_broadcast = new Tiny_connection(null,
             //                                                   Constants.CONNECTION_PORT,
             //                                                   Constants.TELOSB_NODES);
             TinyOSRadioConnection conn=null; //tinyos connection
             Datagram dg=null; //the message holder
             try{
               conn = (TinyOSRadioConnection) Connector.open("tinyos://:65");  //opens connection at channel 65
               dg = conn.newDatagram(conn.getMaximumLength()); //sets up datagram
             } catch (Exception e) {
               System.out.println("Could not open tinyos receiver connection");
               e.printStackTrace();
               return;
             }

             int temp = 0;

             while(true) {
               Rx_package pck_rx = null;
               Short x = 0;
               // do {
               //   thread_message("5555 Waiting for the data");
               //   //pck_rx = rx_broadcast.receive();
               // } while(pck_rx == null);
               System.out.println("print y");
               try {
                 conn.receive(dg); //something is received, blocking wait
                 System.out.println("print x");
               } catch (Exception e) {
                 e.printStackTrace();
               }
               continue;
               // thread_message("Rx type: " + pck_rx.get_pck_type());
               // if((sensor_type == 2) && (pck_rx.get_pck_type() == 8))
               // {
               //   int node_index = pck_rx.get_node_index();
               //   lsensor_all.set(pck_rx.get_node_index(), pck_rx.get_payload()[0]);
               //   thread_message("node index: " + node_index + 
               //                  " val: " + pck_rx.get_payload()[0]);
               // } else if((sensor_type == 3) && (pck_rx.get_pck_type() == 9))
               // {
               //   int node_index = pck_rx.get_node_index();
               //   tsensor_all.set(pck_rx.get_node_index(), pck_rx.get_payload()[0]);
               //   thread_message("node index: " + node_index + 
               //                  " val: " + pck_rx.get_payload()[0]);

               // } else if(pck_rx.get_pck_type() == 7)
               // {
                 //print_data_dis(pck_rx);
                 // temp++;
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
               // }      
               // System.out.println("Temp " + temp + " sensor_type " + sensor_type);
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
                                      int[] values, String address){
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

       public static long raw_temp_to_c(short raw) {
         return (long) (-38.4 + 0.0098 * ((long) raw));
       }


       static void thread_message(String message) {
         String thread_name = Thread.currentThread().getName();
         System.out.println("*** T: " + thread_name + ": " + message);
       }
    
    /////****GUI *//////////////////////////////////////
    Dimension initialScreenSize(){
        Dimension currentScreen = Toolkit.getDefaultToolkit().getScreenSize();
        currentScreen.height = (int)(0.9555 * currentScreen.height);
        //currentScreen.width = (int) (0.9 * currentScreen.width);
        System.out.println(currentScreen.width+" "+currentScreen.height);
        return currentScreen;
    }
    
    void initialize(){
        area = new Area(Constants.AREA_WIDTH, Constants.AREA_HEIGHT);
        coverage = new Area((short)80, (short)80);
        short def_val=40;
        if(current_phenomena == Constants.TEMP_PHENOMENA){
            def_val=75;
        }
        //setting up the sliding GUI
        for(int i=0; i<Constants.TOTAL_MOTES;i++){
            short t=40;
            currentValues.add(t);
        }
        
        short offsetw = Constants.AREA_WIDTH/7;
        short offseth = Constants.AREA_HEIGHT/7;
        for(int i=0; i<Constants.TOTAL_MOTES;i++){
            currentValues.add(def_val);
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
    
}

