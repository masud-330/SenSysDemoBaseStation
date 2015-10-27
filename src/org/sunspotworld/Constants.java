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
    public static final int TELOS_REPORT = 0x01;  //telos report when e phenomena changes to/from threshold value
    public static final int SPOT_NEW_PREDICATE = 0x02;  //when a new predicate arrives, the principal notifies its nodes
    public static final int SPOT_ALL_PREDICATES = 0x03;  //spot->telos, sending all predicates
    public static final int TELOS_REQUEST_PREDICATES = 0x04; //requesting preducates (telos -> spot), when a telos wakes up
    public static final int SPOT_RESET = 0x05;  //the principal's message to telos to reset
    public static final int SPOT_REPORT = 0x06; //a spot reporting to another spot type(multi-hop)
    public static final int MOTES_REPORT = 0x07;
    public static final int SPOT_NEW_COOC = 0x08;

    public static final int SHAPE_OCCUR = 0x00;  //indicates shape predicate occurence
    public static final int CO_OCCUR = 0x01;   //indicates co-occurence predictae occurence
    public static final int PARTIAL_SHAPE = 0x02; //indicates a set of points representing partial shape
    public static final int SEPARATOR = -126;
    public static final int TRAILER = -125;

    public static final int TERMINATOR = -127;  //the value which indicates no more values are needed
    public static final int VALUE_SIZE = 22;
    
    //public static final int TOTAL_MOTES = 20;
    public static final int TOTAL_TELOS = 16;
    public static final double MAX_X = 200;
    public static final double MAX_Y = 200;
    public static Hashtable nodeLocations = new Hashtable();
    
    //////////////////////******************************************
    ///////////////////OUR PART *******************////////////////
   ////////////////////////Default static values..............................
    public static final short AREA_WIDTH= 350;  //area width
    public static final short AREA_HEIGHT= 350;  //area size
    public static final short GAP_HEIGHT= AREA_HEIGHT/7;  //area gap
     public static final short GAP_WIDTH= AREA_WIDTH/7;  //area gap
    
    public static final short TOTAL_MOTES = 36;
    
    public static final short LIGHT_PHENOMENA = 0;  //if the phenomena is light
    public static final short TEMP_PHENOMENA = 1;  //if the phenomena is temperature
    
    public static final short ENERGY_INACTIVE_MOTE = 3;
    public static final short ENERGY_ACTIVE_MOTE = 25;
    
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
    
    
    ///////////////////END OF OUR PART///////////////////////////////////////////////////////////////////////
    ////////////////////************************************///////////////////////////////////////////
    
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
    public static void setNodeLocations(){
        int offset = 25;
        int sparseness = offset*2;
        nodeLocations.put(new Integer(1) , new Point(offset, offset));
        nodeLocations.put(new Integer(2) , new Point(offset, offset + sparseness));
        nodeLocations.put(new Integer(3) , new Point(offset + sparseness, offset + sparseness));
        nodeLocations.put(new Integer(4) , new Point(offset + sparseness, offset));
        nodeLocations.put(new Integer(5) , new Point(offset, offset + 2*sparseness));
        nodeLocations.put(new Integer(6) , new Point(offset, offset + sparseness + 2*sparseness));
        nodeLocations.put(new Integer(7) , new Point(offset + sparseness, offset + sparseness + 2*sparseness));
        nodeLocations.put(new Integer(8) , new Point(offset + sparseness, offset + 2*sparseness));
        nodeLocations.put(new Integer(13) , new Point(offset + 2*sparseness, offset));
        nodeLocations.put(new Integer(14) , new Point(offset + 2*sparseness, offset + sparseness));
        nodeLocations.put(new Integer(15) , new Point(offset + sparseness + 2*sparseness, offset + sparseness));
        nodeLocations.put(new Integer(16) , new Point(offset + sparseness + 2*sparseness, offset));
        nodeLocations.put(new Integer(9) , new Point(offset + 2*sparseness, offset + 2*sparseness));
        nodeLocations.put(new Integer(10) , new Point(offset + 2*sparseness, offset + sparseness + 2*sparseness));
        nodeLocations.put(new Integer(11) , new Point(offset + sparseness + 2*sparseness, offset + sparseness + 2*sparseness));
        nodeLocations.put(new Integer(12) , new Point(offset + sparseness + 2*sparseness, offset + 2*sparseness));
        
        //now add SunSPOTs
        nodeLocations.put(new Integer(17) , new Point(sparseness, sparseness));
        nodeLocations.put(new Integer(18) , new Point(sparseness, 3*sparseness));
        nodeLocations.put(new Integer(19) , new Point(3*sparseness, sparseness));
        nodeLocations.put(new Integer(20) , new Point(3*sparseness, 3*sparseness));
    }

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
}
