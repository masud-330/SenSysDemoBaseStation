/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld;

import java.util.Hashtable;

/**
 *
 * @author beSim
 */
public class Constants {
    ////////////////////////Default static values..............................
    public static final short AREA_WIDTH= 350;  //area width
    public static final short AREA_HEIGHT= 350;  //area size
    public static final short GAP_HEIGHT= AREA_HEIGHT/7;  //area gap
    public static final short GAP_WIDTH= AREA_WIDTH/7;  //area gap
    
    public static final short TOTAL_MOTES = 36;
    
    public static final short LIGHT_PHENOMENA = 2;  //if the phenomena is light
    public static final short TEMP_PHENOMENA = 3;  //if the phenomena is temperature
    
    public static final short ENERGY_INACTIVE_MOTE = 3;
    public static final short ENERGY_ACTIVE_MOTE = 25;
    
    public static final short MAX_X = 350;
    public static final short MAX_Y = 350;
    
    public static Hashtable nodeLocations = new Hashtable();
    
    private static int number_telosb = 32;

    public static String[] ss_id = new String[] { "7EBA", "7F45", "79A3", "7997"};
    public static final short TERMINATOR = -127;
    public static final int VALUE_SIZE = 31;
    public static final String T1205_ID = "0014.4F01.0000.1205";
    public static final String BROADCAST_ID = "0014.4F01.0000.FFFF";
    public static final String[] TELOSB_NODES = {"1205"}; 
    public static final int CONNECTION_PORT = 65;        
    /**
     * T1           T4      :T13          T16
     *      S1              :       S4
     * T2           T3      :T14          T15
     * -------------------------------------------------
     * T5           T8      :T9           T12
     *      S2              :       S3
     * T6           T7      :T10          T11
     * 
     * there is a factor of 25 units offset from the edges, nodes are
     * 50 units apart
     */

    public static Point idToLocation(int id){
        return (Point)nodeLocations.get(new Integer(id));
    }

    public static int locationToID(Point p){
       // System.out.println("In the location to id function");
        //System.out.println("nodeLocations size "+nodeLocations.size());
        //System.out.println("10th element in nodeLocations "+nodeLocations.get(new Integer(10)));
        for(int i = 1; i < nodeLocations.size() + 1; i ++){
            if(((Point)nodeLocations.get(new Integer(i))).x == p.x &&
                    ((Point)nodeLocations.get(new Integer(i))).y == p.y)
                return i;
        }
        return -1;
    }
    ///////////////////OUR PART *******************////////////////
    
    private static String last_4addr(String addr)
    {   // return the last 4 digit of the address 
        String node_last4 = null;
        if(addr.length() == 19)
            node_last4 = addr.substring(15, addr.length());
        else if(addr.length() == 4)
            node_last4 = addr;    
        return node_last4;
    }
            
    ///////////////////////////// PUBLIC ///////////////////////////////////////


    public static Hashtable<String, Integer> nodeIds= new Hashtable<String, Integer>();
    
    public static void setAddresIDMapping(){
        nodeIds.put("0000", 0);
        nodeIds.put("0101", 1);
        nodeIds.put("0202", 2);
        nodeIds.put("1003", 3);
        nodeIds.put("1104", 4);
        nodeIds.put("1205", 5);
        nodeIds.put("0306", 6);
        nodeIds.put("7EBA", 7);
        nodeIds.put("0407", 8);
        nodeIds.put("1308", 9);
        nodeIds.put("7F45", 10);
        nodeIds.put("1409", 11);
        nodeIds.put("0510", 12);
        nodeIds.put("0611", 13);
        nodeIds.put("0712", 14);
        nodeIds.put("1513", 15);
        nodeIds.put("1614", 16);
        nodeIds.put("1715", 17);
        nodeIds.put("2016", 18);
        nodeIds.put("2117", 19);
        nodeIds.put("2218", 20);
        nodeIds.put("3019", 21);
        nodeIds.put("3120", 22);
        nodeIds.put("3221", 23);
        nodeIds.put("2322", 24);
        nodeIds.put("79A3", 25);
        nodeIds.put("2423", 26);
        nodeIds.put("3324", 27);
        nodeIds.put("7997", 28);
        nodeIds.put("3425", 29);
        nodeIds.put("2526", 30);
        nodeIds.put("2627", 31);
        nodeIds.put("2728", 32);
        nodeIds.put("3529", 33);
        nodeIds.put("3630", 34);
        nodeIds.put("3731", 35);
    }
    
    public static short getNodeId(String addr){
        if(nodeIds.contains(addr))
            return nodeIds.get(addr).shortValue();
        return -1;    
    }
    
    public static Point getNodeLocation(short id){
        if(id<0 || id>35){
            return null;
        }
        short offsetw = Constants.AREA_WIDTH/7;
        short offseth = Constants.AREA_HEIGHT/7;
        int indw = id%6;
        int indh = id/6;
        return new Point((short)(offsetw+(indw*offsetw)),(short)(offseth+(indh*offseth)));
    }
    
    public static boolean isTelos(short id){
        if(id==7 || id==10 || id==25 || id==28){
            return false;
        }
        return true;
    } 
        
}
