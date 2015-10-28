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
public class SunSpotHostApplication {

    public static String[] layer_type={"Light","Temperature"};
    public static TheFrame frame;
    public static LongTask task=new LongTask();
    public static Vector<Short> currentValues = new Vector<Short>();
    public static int OurHC=0;
    public static int BruteHC=0;
    
    
    /**
     * Print out our radio address.
     */
    public void run() { 
        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        System.out.println("Our radio address = " + IEEEAddress.toDottedHex(ourAddr));
        //System.exit(0);
        //startReceiverThread(); //start the reciver thread to listen
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
                        
                    } catch (Exception e) {
                        System.out.println("Nothing received");
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    
    /////****GUI *//////////////////////////////////////
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
        //setting up the sliding GUI
        for(int i=0; i<Constants.TOTAL_MOTES;i++){
            short t=40;
            currentValues.add(t);
        }
    }
    
}

