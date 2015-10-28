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

    public static Vector<ShapePredicate> currentPredicates = new Vector<ShapePredicate>();
    public static Vector<CoOccurencePredicate> curCoOccPredicates = new Vector<CoOccurencePredicate>();
//    public static Vector<ShapeOccurrence> currentShapes= new Vector<ShapeOccurrence>();
    public static Vector<ShapeOccurrence> positiveShapes = new Vector<ShapeOccurrence>();
    public static String[] layer_type={ "Light","Temperature"};
    public static Color[] colorlib={Color.BLUE,Color.GREEN, Color.RED, Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA, Color.BLACK};
    public static String[] Colorname={"Blue", "Green", "Red", "Orange", "Pink", "Cyan", "Magenta", "Black"};
    public static TheFrame frame;
    public static LongTask task=new LongTask();
    public static Vector<Value> currentValues = new Vector<Value>();
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
    
    public static void startReceiverThread() {
        new Thread() {
            public void run() {
                TinyOSRadioConnection conn=null; //tinyos connection 
                Datagram dg=null; //the message holder
	        try{
                   conn = (TinyOSRadioConnection) Connector.open("tinyos://:65");  //opens connection at channel 65
		   dg = conn.newDatagram(conn.getMaximumLength()); //sets up datagram
                } catch (IOException e) {
                    System.out.println("Could not open tinyos receiver connection");
                    e.printStackTrace();
                    return;
                }     
                while(true){
                    try {
			conn.receive(dg); //something is received, blocking wait
                        System.out.println("Something is received "+dg.getAddress() +" "+dg.getData().length);
                        //System.out.println(dg.getData().length);
                        int msg_type = dg.readInt();
                        if (dg.getAddress() != null && dg.getData().length > 12 && (dg.getAddress().equals("0014.4F01.0000.7995") || msg_type==Constants.MOTES_REPORT)) {
                             System.out.println("msg type: "+msg_type);
                             
                             if(msg_type==Constants.SPOT_REPORT){
                                 dg.readInt(); //skip origin
                                 OurHC+=dg.readInt();
                                 
                                 int p_type = dg.readInt();
                                 System.out.println("shape type "+p_type);
                                 
                                 if(p_type==Constants.SHAPE_OCCUR){
                                    int area = dg.readInt();
                                    int thresh = dg.readInt();
                                    int pheno = dg.readInt();

                                    ShapeOccurrence tempOccur;
                                    Vector points=new Vector();
                                    ShapePredicate temppre=new ShapePredicate(area, thresh, pheno);
                                    boolean isFirst=false;
                                    boolean isFinish=false;
                                    boolean isLast=false;
                                    while(!isFinish){
                                        int read=dg.readInt();
                                        System.out.println("received point is "+read);
                                        if(read==Constants.SEPARATOR)
                                        {
                                            isFinish=true;
                                            isFirst=true;
                                            if(dg.readInt()==Constants.TRAILER){
                                                isLast=true;
                                            }
                                        }
                                        else if(read==Constants.TERMINATOR)
                                        {
                                            isFinish=true;
                                            if(dg.readInt()==Constants.TRAILER){
                                                isLast=true;
                                            }
                                        }
                                        else
                                        {
                                            Point readin=Constants.idToLocation(read);
                                            points.addElement(readin);
                                        }

                                     }
                                     System.out.println("Points Size: "+points.size());
                                     tempOccur=new ShapeOccurrence((Vector)points.clone(),temppre);
  /////////////////////////////////////////////////////////////we put the received shape to. without check/////////////////////////////
                                     if(isFirst){
                                         task.stop();
                                         positiveShapes.clear();
                                     }
                                     positiveShapes.add(tempOccur); 
                                     if(isLast){
                                         task.go();
                                     }
                                        for(int i=0;i<currentPredicates.size();i++)
                                        {
                                                	Calendar cal = Calendar.getInstance();
    	                                                cal.getTime();
    	                                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                           
                                                if(SunSpotHostApplication.currentPredicates.get(i).threshold== tempOccur.predicate.threshold
                                                        &&SunSpotHostApplication.currentPredicates.get(i).area== tempOccur.predicate.area
                                                        &&SunSpotHostApplication.currentPredicates.get(i).phenomenonLayer== tempOccur.predicate.phenomenonLayer)
                                                {
                                                    String tolog=" Es"+Integer.toString(i+1)+" occurred   ----   "+sdf.format(cal.getTime())+"\n";
                                                    UserPanel.logs.append(tolog);
                                                }
                                            

                                        }
                                        for(int i=0; i<positiveShapes.size(); i++){
                                            ShapeOccurrence occ=positiveShapes.get(i);
                                            System.out.println(occ.predicate.phenomenonLayer+" "+occ.predicate.threshold+" "+occ.predicate.area);
                                            for(int j=0; j<occ.points.size(); j++){
                                                Point p = (Point)occ.points.get(j);
                                                System.out.println(p.x+" "+p.y);
                                            }
                                        }
                                 }
                                 else if (p_type==Constants.PARTIAL_SHAPE){
                                     System.err.println("recived partial shape");
                                 }
                                 else if(p_type==Constants.CO_OCCUR){
                                     System.out.println("Received Co-Occ ");
                                     
                                     int area = dg.readInt();
                                     int threshold = dg.readInt();
                                     int phenomenon = dg.readInt();
                                     ShapePredicate sp1 = new ShapePredicate(area, threshold, phenomenon);
                                     
                                     area = dg.readInt();
                                     threshold = dg.readInt();
                                     phenomenon = dg.readInt();
                                     ShapePredicate sp2 = new ShapePredicate(area, threshold, phenomenon);
                                     Point p1 = Constants.idToLocation(dg.readInt());
                                     Point p2 = Constants.idToLocation(dg.readInt());
                                     
                                     for(int i=0; i<curCoOccPredicates.size(); i++){
                                         CoOccurencePredicate co_oc = curCoOccPredicates.elementAt(i);
                                         if((sp1.area==co_oc.sp1.area && sp1.phenomenonLayer==co_oc.sp1.phenomenonLayer && sp1.threshold==co_oc.sp1.threshold)
                                                 &&(sp2.area==co_oc.sp2.area && sp2.phenomenonLayer==co_oc.sp2.phenomenonLayer && sp2.threshold==co_oc.sp2.threshold)){
                                            Calendar cal = Calendar.getInstance();
    	                                    cal.getTime();
    	                                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                            String tolog=" Cooc"+Integer.toString(i+1)+" occurred   ----   "+sdf.format(cal.getTime())+"\n";
                                            UserPanel.logs.append(tolog);
                                            break;
                                         }
                                     }
                                     
                                 }
                             }
                             else if(msg_type==Constants.MOTES_REPORT){
                                 dg.readInt(); //skip origin
                                 int hopcount=dg.readInt(); 
                                 BruteHC+=hopcount;//we will use it later
                                 
                                 int id = dg.readInt();
                                 currentValues.get(id-1).light=dg.readInt();
                                 currentValues.get(id-1).temperature=dg.readInt();
                                 System.out.println("Telos Value Report: "+id+" "+currentValues.get(id-1).light+" "+currentValues.get(id-1).temperature+" "+" hop :"+hopcount);
                             }
                        } 
                            
                    } catch (Exception e) {
                        System.out.println("Nothing received");
                        e.printStackTrace();
                    }
                }
            }
        }.start();
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
        Constants.setNodeLocations(); //setting constants
        //setting up the sliding GUI
        for(int i=0; i<Constants.TOTAL_MOTES;i++){
            Value t=new Value(40, 75);
            currentValues.add(t);
        }
    }
}

