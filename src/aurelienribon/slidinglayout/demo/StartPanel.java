/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aurelienribon.slidinglayout.demo;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.sunspotworld.*;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;

/**
 *
 * @author Muhammed
 */
public class StartPanel extends javax.swing.JPanel {

  /**
   * Creates new form StartPanel
   */
    
  private static final Color FG_COLOR = new Color(0xFFFFFF); 
  private static final Color BG_COLOR = new Color(0xFFFFFF);
  private static final Color BORDER_COLOR = new Color(0x000000);

  private static final TweenManager tweenManager = SLAnimator.createTweenManager();
    
  private Runnable action;
  private boolean actionEnabled = true;
  private boolean hover = false;
  private int borderThickness = 2;
  private BufferedImage bgImg;
    
  public StartPanel(Dimension d) {
    setBackground(BG_COLOR);
    setLayout(new BorderLayout());
    initComponents();
    jcomboType.removeAllItems();
    jcomboType.addItem("Light");
    jcomboType.addItem("Temperature");
        
    try {
      bgImg = ImageIO.read(new File("data/startpage.jpg"));
    } catch (IOException ex) {
      System.err.println("[error] cannot read image path '" + "data/startpage.jpg" + "'");
      //add(label, BorderLayout.CENTER);
    }
        
    //jLabel1.setLocation(d.width, WIDTH);()
    addMouseListener(new MouseAdapter() {
        /*@Override
          public void mouseEntered(MouseEvent e) {
          hover = true;
          if (actionEnabled) showBorder();
          }

          @Override
          public void mouseExited(MouseEvent e) {
          hover = false;
          hideBorder();
          }*/

        /*@Override
          public void mouseReleased(MouseEvent e) {
          if (action != null && actionEnabled) action.run();
          }*/
      });
  }
    
  public void setAction(Runnable action) {this.action = action;}
  public void enableAction() {actionEnabled = true; if (hover) showBorder();}
  public void disableAction() {actionEnabled = false;}

  private void showBorder() {
    tweenManager.killTarget(borderThickness);
    Tween.to(StartPanel.this, Accessor.BORDER_THICKNESS, 0.4f)
      .target(10)
      .start(tweenManager);
  }

  private void hideBorder() {
    tweenManager.killTarget(borderThickness);
    Tween.to(StartPanel.this, Accessor.BORDER_THICKNESS, 0.4f)
      .target(2)
      .start(tweenManager);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D gg = (Graphics2D) g;

    int w = getWidth();
    int h = getHeight();
                
    int locx=(w/2)-(jlabelEnergy.getWidth()/2);
    int locy=h/2 + 5*(jlabelEnergy.getHeight()/2);
    int imgw = (int)(w/1.9);
    int imgl = (int) (h/2.45);
    gg.drawImage(bgImg, (int)(w/2.0)- (int)(imgw/2.0), (int)(h/10.00), imgw, imgl, null);
    jlabelEnergy.setLocation(locx, locy);
    jlabelEnergy.setText("Energy Threshold, mA (\u03B4)");
                
    locy+=jlabelEnergy.getHeight();
    jtfEnergy.setLocation(locx, locy);
                
    locy+=2*jlabelEnergy.getHeight();
    jlabelTime.setLocation(locx, locy);
    jlabelTime.setText("Time-Period, S (\u0194)");
                
    locy+=jlabelEnergy.getHeight();
    jtfTime.setLocation(locx, locy);
                
    locy+=2*jlabelEnergy.getHeight();
    jlabelType.setLocation(locx, locy);
                
    locy+=jlabelEnergy.getHeight();                
    jcomboType.setLocation(locx, locy);
                
    locx-=(int)(jbuttonStart.getSize().width/4.0);
    locy+=2*jlabelEnergy.getHeight();
    jbuttonStart.setLocation(locx, locy);
  }

  // -------------------------------------------------------------------------
  // Tween Accessor
  // -------------------------------------------------------------------------

  public static class Accessor extends SLAnimator.ComponentAccessor {
    public static final int BORDER_THICKNESS = 100;

    @Override
    public int getValues(Component target, int tweenType, float[] returnValues) {
      StartPanel tp = (StartPanel) target;

      int ret = super.getValues(target, tweenType, returnValues);
      if (ret >= 0) return ret;

      switch (tweenType) {
        case BORDER_THICKNESS: returnValues[0] = tp.borderThickness; return 1;
        default: return -1;
      }
    }

    @Override
    public void setValues(Component target, int tweenType, float[] newValues) {
      StartPanel tp = (StartPanel) target;

      super.setValues(target, tweenType, newValues);

      switch (tweenType) {
        case BORDER_THICKNESS:
          tp.borderThickness = Math.round(newValues[0]);
          tp.repaint();
          break;
      }
    }
  }
    

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jlabelEnergy = new javax.swing.JLabel();
    jtfEnergy = new javax.swing.JTextField();
    jlabelTime = new javax.swing.JLabel();
    jtfTime = new javax.swing.JTextField();
    jlabelType = new javax.swing.JLabel();
    jcomboType = new javax.swing.JComboBox();
    jbuttonStart = new javax.swing.JButton();

    setPreferredSize(new java.awt.Dimension(1920, 1031));

    jlabelEnergy.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
    jlabelEnergy.setText("Energy Threshold (Delta)");

    jtfEnergy.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
    jtfEnergy.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          jtfEnergyActionPerformed(evt);
        }
      });

    jlabelTime.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
    jlabelTime.setText("Time-Period (Gamma)");

    jtfTime.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
    jtfTime.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          jtfTimeActionPerformed(evt);
        }
      });

    jlabelType.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
    jlabelType.setText("Event Type");

    jcomboType.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
    jcomboType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    jbuttonStart.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
    jbuttonStart.setText("Start Experiment");
    jbuttonStart.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseReleased(java.awt.event.MouseEvent evt) {
          jbuttonStartMouseReleased(evt);
        }
      });
    jbuttonStart.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          jbuttonStartActionPerformed(evt);
        }
      });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
                .addContainerGap(1225, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                              .addComponent(jcomboType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                              .addComponent(jlabelType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                              .addComponent(jtfTime)
                                              .addComponent(jlabelTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                              .addComponent(jtfEnergy)
                                              .addComponent(jlabelEnergy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(449, 449, 449))
                          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jbuttonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(395, 395, 395))))
      );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(692, Short.MAX_VALUE)
                .addComponent(jlabelEnergy, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfEnergy, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlabelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfTime, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelType, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcomboType, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jbuttonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
      );
  }// </editor-fold>//GEN-END:initComponents

  private void jtfEnergyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfEnergyActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_jtfEnergyActionPerformed

  private void jtfTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfTimeActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_jtfTimeActionPerformed

  private void jbuttonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonStartActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_jbuttonStartActionPerformed

  private void jbuttonStartMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbuttonStartMouseReleased
    // TODO add your handling code here:
    boolean flag=false;
    String errorlabel = new String(); 
    int energy_temp=0;
    int time_temp=0;
    int layerchoice;

    if(jtfEnergy.getText().equals(""))
    {
      flag=true;
      errorlabel="empty";

    }
    
    if(jtfTime.getText().equals(""))
    {
      flag=true;
      errorlabel="empty";
    }
    
    try{
      energy_temp = Integer.parseInt(jtfEnergy.getText());
      jtfEnergy.setText("");
    }
    catch(NumberFormatException nfe)
    {
      flag=true;
      errorlabel="nfe";
    }
    
    try{
      time_temp = Integer.parseInt(jtfTime.getText());
      jtfTime.setText("");

    }
    catch(NumberFormatException nfe)
    {
      flag=true;
      errorlabel="nfe";
    }

    if(flag==true)
    {
      if(errorlabel.equals("empty"))
      {
        JOptionPane.showMessageDialog(this,
                                      "Can not have empty input field","Input mistake",
                                      JOptionPane.WARNING_MESSAGE);
        jtfEnergy.setText("");
        jtfTime.setText("");
      }
      if(errorlabel.equals("nfe"))
      {
        JOptionPane.showMessageDialog(this,
                                      "Energy Threshold and Time-Period must be number","Input mistake",
                                      JOptionPane.WARNING_MESSAGE);
        jtfEnergy.setText("");
        jtfTime.setText("");
      }
    }
    //if all are correct
    else{
      //Compute Rectangle Area from Energy Threshold
      // Not transmitting telosb = 3 mA (inactive)
      // Transmitting telosb = 25 mA (active)
      int k=(energy_temp/Constants.ENERGY_ACTIVE_MOTE); // number of nodes will be covered.
      
      // set the range of the k from 0 - 36.
      k = Math.max(1, k);
      k = Math.min(k, 36);
      System.out.println("energy_temp: " + energy_temp + " k: " + k);

      if (k%2 == 1 && k!=9 && k!=25){
        k++;
      } // number of nodes should be even.
      
      double area = (k * k * (Constants.AREA_HEIGHT-100)* (Constants.AREA_WIDTH-100)) / Constants.MAX_ID;
      
      // hard code the shape of the rectangle and the number of nodes 
      // in width and ratio_length
      short a=1, b=1; // a = ratio_length, b = width
      if(k==1 || k==4 || k==9 || k==16 || k==25){
        a=(short) Math.sqrt(k);
        b=a;
      }
                    
      if(k==2) {a=2; b=1;}
      if(k==6) {a=3; b=2;}
      if(k==8) {a=4; b=2;}
      if(k==10) {a=5; b=2;}
      if(k==12) {a=4; b=3;}
      if(k==14) {a=6; b=2;}
      if(k==18) {a=6; b=3;}
      if(k==20 || k==22) {a=5; b =4;}
      if(k==24 || k==26 || k==28) {a=6; b=4;}
      if(k==30 || k==32 || k==34) {a=6; b=5;}
      if(k==36) {a=6; b=6;}
      ////////////////////////////////////////////////////////////////////////////////////////////
      short x = (short)Math.sqrt(area/ (a*a*b*b));
      short rheight = (short) (b*x);  ///rectangle size
      short rwidth = (short) (a*x);
      
      if(rwidth % 2 == 1) rwidth+=1;
      if(rheight % 2 == 1) rheight+=1;
      System.out.println("a: " + a + " b: " + b);      
      
      SunSpotHostApplication.coverage = new Area(rwidth, rheight);
      SunSpotHostApplication.enclosed_objects_no = (short)k;
      SunSpotHostApplication.time_period = (short) time_temp;
      System.out.println(k+" "+rwidth +"---------"+rheight);
      
      //TODO: Copy Top's code here directly to send the Setup packet!
      SunSpotHostApplication.current_phenomena = (short)(jcomboType.getSelectedIndex()+2);
      SunSpotHostApplication.send_setup();
      //////////////////////////DONE/////////////////////////////////////////////
                    
      String prenew="Energy Threshold, \u03B4 = "+Integer.toString(energy_temp)+" mA.\n"+"Time-Period, \u0194 = "+Integer.toString(time_temp)+" S.\n"
        +"Phenomena Type = "+SunSpotHostApplication.layer_type[jcomboType.getSelectedIndex()]+". \n"+"Rectangle Size = ("+rwidth+","+rheight+"). \n"
        +"No of Active Nodes = "+k+". \n";
                
      UserPanel.predicate_show.append(prenew);
                    
      String lognew = "Parameters Received. \n";
      UserPanel.logs.append(lognew);
      //CardLayout cl = (CardLayout)(cards.getLayout());
      //cl.show(cards,second);
      SunSpotHostApplication.BruteHC=0;
      SunSpotHostApplication.OurHC=0;
      SunSpotHostApplication.task.go();
      if (action != null && actionEnabled) action.run();
      this.jcomboType.setSelectedIndex(0);
    }
  }//GEN-LAST:event_jbuttonStartMouseReleased


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jbuttonStart;
  private javax.swing.JComboBox jcomboType;
  private javax.swing.JLabel jlabelEnergy;
  private javax.swing.JLabel jlabelTime;
  private javax.swing.JLabel jlabelType;
  private javax.swing.JTextField jtfEnergy;
  private javax.swing.JTextField jtfTime;
  // End of variables declaration//GEN-END:variables
}
