/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aurelienribon.slidinglayout.demo;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;
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
                    // Not transmitting telosb = 2.5 mA
                    // Transmitting telosb = 20 mA
                    int k=(energy_temp/Constants.ENERGY_ACTIVE_MOTE);
                    if (k%2 == 1){
                        k++;
                    }
                    double area = (k * (Constants.AREA_HEIGHT - 2*Constants.GAP_HEIGHT) * (Constants.AREA_WIDTH - 2*Constants.GAP_WIDTH)) / Constants.TOTAL_MOTES;
                    short rwidth = (short)Math.sqrt(area);
                    short rheight = rwidth;  ///rectangle size
                    
                    System.out.println(k+" "+rwidth +"---------"+rheight);
                    
                    //System.out.print(thre_temp);
                    //System.out.print(area_temp);
                    ShapePredicate temp=new ShapePredicate(energy_temp,time_temp,jcomboType.getSelectedIndex());
                    SunSpotHostApplication.currentPredicates.add(temp);
                    //***************send temp out
                          SunSpotHostApplication.sensor_type = jcomboType.getSelectedIndex() + 2;
                          long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
                          String base_addr = IEEEAddress.toDottedHex(ourAddr);
                          int[] arr = new int[Constants.VALUE_SIZE];
                          arr[0]= Integer.parseInt(base_addr.substring(15)); 
                          arr[1]= time_temp;
                          arr[2]= SunSpotHostApplication.sensor_type; // waiting for the sensor type
                          arr[3]= rwidth;
                          arr[4]= rheight;
                          SunSpotHostApplication.sendMessage(15, 4, arr, base_addr);
                          
        /*    try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(StartPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
                             arr[1] = 65;
                             SunSpotHostApplication.sendMessage(Constants.SPOT_NEW_PREDICATE,arr ,"0014.4F01.0000.7EB8");*/
                    
                    String prenew=" Es"+Integer.toString((SunSpotHostApplication.currentPredicates.size()))+"("+Integer.toString(temp.threshold)
                            +", "+Integer.toString(temp.area)+", "+SunSpotHostApplication.layer_type[jcomboType.getSelectedIndex()]+", "+SunSpotHostApplication.Colorname[SunSpotHostApplication.currentPredicates.size()-1]+")\n";       ///****
                    // System.out.println(prenew); test prenew 
                    UserPanel.predicate_show.append(prenew);
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
