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
    public static short enclosed_objects_no = 0;
    public static short time_period = 0;
    public static Window Opt_Window = null;
    public static int OurHC=0;
    public static int BruteHC=0;
    public static short current_phenomena = Constants.LIGHT_PHENOMENA;
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
        System.out.println("Our radio address = " + IEEEAddress.toDottedHex(ourAddr));
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

