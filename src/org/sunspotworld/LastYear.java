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

import java.io.*;
import java.util.Vector;
import javax.microedition.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Sample Sun SPOT host application
 */
public class LastYear {

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
    /**
     * Print out our radio address.
     */
    public void run() { 
        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        System.out.println("Our radio address = " + IEEEAddress.toDottedHex(ourAddr));
        //System.exit(0);
        startReceiverThread(); //start the reciver thread to listen
        initialize();
        Tween.registerAccessor(ThePanel.class, new ThePanel.Accessor());
	SLAnimator.start();
        frame = new TheFrame(initialScreenSize());  //initilize the screen based on desktop resolution
    }

    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    /*public static void main(String[] args) {
        SunSpotHostApplication app = new SunSpotHostApplication();
        app.run(); //this will set the application off
    }*/
    
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
    
    Dimension initialScreenSize(){
        Dimension currentScreen = Toolkit.getDefaultToolkit().getScreenSize();
        currentScreen.height = (int)(0.9555 * currentScreen.height);
        //currentScreen.width = (int) (0.9 * currentScreen.width);
        System.out.println(currentScreen.width+" "+currentScreen.height);
        return currentScreen;
    }
    
       
        //a function for spots to send various type of messages
    public static void sendMessage(int type, int[] values, String address){
        TinyOSRadioConnection conn = null;
        try {
            conn = (TinyOSRadioConnection) Connector.open("tinyos://"+address+":65");
        } catch (IOException ex) {
            System.out.println("Could not open radiogram broadcast connection");
            ex.printStackTrace();
            return;
        }
        try {
                //for origin thing setup
                long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
                // We send the message (UTF encoded)
                Datagram dg = conn.newDatagram(conn.getMaximumLength());
                dg.reset();
                dg.writeInt(type);
                dg.writeInt(Integer.parseInt(IEEEAddress.toDottedHex(ourAddr).substring(15), 16)); //origin
                dg.writeInt(1); //hocount
                for(int i=0; i<values.length; i++){
                    dg.writeInt(values[i]);
                }
                conn.send(dg);
                System.out.println("Updated info sent...");
                
        } catch (IOException ex) {
            System.err.println("Problem while sending message");
            ex.printStackTrace();
        }
        finally{
            try{
                conn.close();
            } catch (IOException ex){
                
            }
        }
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

