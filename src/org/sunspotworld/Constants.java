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

    public static final int LIGHT_PHENOMENA = 0x00;  //Indicates the phenomena of the predicate is light
    public static final int TEMP_PHENOMENA = 0x01;   //Indicates the phenomena of the predicate is temperature

    public static final int TERMINATOR = -127;  //the value which indicates no more values are needed
    public static final int VALUE_SIZE = 22;
    
    //public static final int TOTAL_MOTES = 20;
    public static final int TOTAL_TELOS = 16;
    public static final double MAX_X = 200;
    public static final double MAX_Y = 200;
    
    //////////////////////******************************************
    ///////////////////OUR PART *******************////////////////
    public static final short ENERGY_INACTIVE_MOTE = 3;
    public static final short ENERGY_ACTIVE_MOTE = 25;
    
    public static final short AREA_WIDTH= 350;  //area width
    public static final short AREA_HEIGHT= 350;  //area size
    public static final short TOTAL_MOTES = 36;
    public static final short GAP_HEIGHT= AREA_HEIGHT/7;  //area gap
    public static final short GAP_WIDTH= AREA_WIDTH/7;  //area gap
     
    public static Hashtable nodeLocations = new Hashtable();
    
    private static int number_telosb = 32;
    //                                           0  1  2  3  4  5  6  7  
    private static int[] node_count = new int[] {6, 5, 4, 6, 4, 6, 5, 4,
                                                 3, 2, 1, 3, 2, 3, 3, 3,
                                                 6, 5, 4, 6, 5, 6, 6, 6, 
                                                 4, 4, 4, 5, 5, 6, 6, 6,
                                                 5, // S:0x7BEA
                                                 2, // S:0x7F45
                                                 5, // S:0x79A3
                                                 5};// S:0x7997
    public static String[] ss_id = new String[] { "7EBA", "7F45", "79A3", "7997"};
    public static final String T1205_ID = "0014.4F01.0000.1205";
    public static final String BROADCAST_ID = "0014.4F01.0000.FFFF";
    public static final String[] TELOSB_NODES = {"1205"}; 
    public static final int CONNECTION_PORT = 37;        
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
            
    private static int ss_node_index(String node_last4)
    {   // return the node index of ss 
        // { "7EBA", "7F45", "79A3", "7997"} 0, 1, 2, 3
        // -1 if not the address is of ss.
        for(int i = 0; i < ss_id.length; i++)
        {
            if(ss_id[i].equals(node_last4))
                return i + number_telosb;
        }
        return -1;
    }    
    
    ///////////////////////////// PUBLIC ///////////////////////////////////////

    // @param String node_id node id of either telosb or ss
    // return int the node count in the centralized approach
    public static int cost_node_count(String node_id)
    {   
        String node_last4 = last_4addr(node_id);

        int index = ss_node_index(node_last4);
        if(index != -1)
          return node_count[index];
          
        index = Integer.parseInt(node_last4.substring(2, 4));
        return node_count[index];
    }

    // @param String node_id node id of either telosb or ss
    // return int the node index for the lsensor_all or tsensor_all
    public static int node_index(String node_id)    
    {
        String node_last4 = last_4addr(node_id);
        int index = ss_node_index(node_last4);
        //System.out.println(node_last4 + " ss index: " + index);
        if(index != -1)
          return index;
        // find the node index based on the last 2 digits of the nodes
        // int temp = Integer.parseInt(node_last4.substring(2, 4));
        
        // find the node index based on the node cluster number and 
        // the node cluster id which are the first and second digits
        int temp = Integer.parseInt(node_last4.substring(0, 1)) * 8
                   + Integer.parseInt(node_last4.substring(1, 2));
        //System.out.println(node_last4 + " int parse index: " + temp);        
        return temp;
    }    
}
