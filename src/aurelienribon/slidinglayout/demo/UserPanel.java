package aurelienribon.slidinglayout.demo;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import org.sunspotworld.Constants;
import org.sunspotworld.SunSpotHostApplication;
import static org.sunspotworld.SunSpotHostApplication.layer_type;
import javax.swing.text.DefaultCaret;
import org.sunspotworld.Area;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class UserPanel extends JPanel {
	private static final Color FG_COLOR = new Color(0xFFFFFF);
	private static final Color BG_COLOR = new Color(0x3B5998);
	private static final Color BORDER_COLOR = new Color(0x000000);

	private static final TweenManager tweenManager = SLAnimator.createTweenManager();
	private Runnable action;
	private boolean actionEnabled = true;
	private boolean hover = false;
	private int borderThickness = 2;
        private Dimension fixedSize;
        public static JTextArea logs = new JTextArea();
        public static JTextArea predicate_show = new JTextArea();
        public static JPanel cards;
        final static String second = "card for add second";
        final static String third = "card for add third";
        final static String forth = "card for cooc";
        final static String fifth = "card for event";
        static boolean flag=false;    
        static String errorlabel;
        public static JLabel hopLabel = new JLabel("Hopcoount (brute/: ");
        

	public UserPanel(Dimension d) {
		//this.setLayout(c);
                cards=new JPanel(new CardLayout());
                fixedSize=d;
                setBackground(BG_COLOR);
		setLayout(new BorderLayout());
               
                final JPanel controlP=new JPanel();
                JPanel add=new JPanel();
                JPanel cooc = new JPanel();
                JPanel event = new JPanel();
                
                /////fillers, ugly coding..
                JLabel fillers[] = new JLabel[16];
                for(int i=0; i<fillers.length;i++){
                    fillers[i]=new JLabel("                                                                             ");;
                }
                int textBoxWidth = (int)(0.8*fixedSize.width);
                int textBoxHeight = (int)(((float)0.28)*fixedSize.height);
                
                
                /* for add page*/
                
                int locx=10, locy=10;
                JButton add_pre = new JButton("           Send \u03B4           ");
                add_pre.setFont(new Font("SansSerif", Font.PLAIN, 15));
                add_pre.setSize(300, 35);

                final TextField threshold_add = new TextField();
                threshold_add.setFont(new Font("SansSerif", Font.PLAIN, 15));
                threshold_add.setColumns(20);
                threshold_add.setSize(200, 35);
                
                JLabel shres_add_labor = new JLabel("                     Energy Threshold, \u03B4 (mA)           ", JLabel.TRAILING);
                shres_add_labor.setLabelFor(threshold_add);
                shres_add_labor.setFont(new Font("SansSerif", Font.PLAIN, 15));
                shres_add_labor.setSize(325, 20);

                //final TextField area_field_add = new TextField();
                //area_field_add.setFont(new Font("SansSerif", Font.PLAIN, 15));
                //area_field_add.setColumns(20);
                //area_field_add.setSize(200, 35);
               // JLabel area_add_labor = new JLabel("               Minimum Area              ", JLabel.TRAILING);
               // area_add_labor.setLabelFor(area_field_add);
                //area_add_labor.setFont(new Font("SansSerif", Font.PLAIN, 15));
                //area_add_labor.setSize(325, 20);

                //final JComboBox layer_add=new JComboBox();
                //layer_add.setFont(new Font("SansSerif", Font.PLAIN, 15));
                //layer_add.setSize(200, 35);
                //JLabel layer_add_labor=new JLabel("                     Event Type                     ", JLabel.TRAILING);
                //layer_add_labor.setFont(new Font("SansSerif", Font.PLAIN, 15));
                //layer_add_labor.setSize(325, 20);
                //layer_add_labor.setLabelFor(layer_add);
                //layer_add.addItem("Light");
                //layer_add.addItem("Temperature");
                
                shres_add_labor.setLocation(locx, locy);
                locy+=40;
                locx+=120;
                threshold_add.setLocation(locx, locy);
                //locy+=60;
                //locx-=120;
                locy+=85;
                locx-=45;
                add_pre.setLocation(locx, locy);
                
                
                add.setLayout(null);
                add.add(shres_add_labor);
                //add.add(fillers[4]);
                add.add(threshold_add);
                //add.add(fillers[5]);
                //add.add(fillers[9]);
                add.add(add_pre);
                
                /* for occurence panel*/
                JButton add_cooc = new JButton("       Send \u0194      ");
                add_cooc.setFont(new Font("SansSerif", Font.PLAIN, 15));
                add_cooc.setSize(300, 35);
                
                final TextField area_field_add = new TextField();
                area_field_add.setFont(new Font("SansSerif", Font.PLAIN, 15));
                area_field_add.setColumns(20);
                area_field_add.setSize(200, 35);
                JLabel area_add_labor = new JLabel("               Time-Period, \u0194 (S)             ", JLabel.TRAILING);
                area_add_labor.setLabelFor(area_field_add);
                area_add_labor.setFont(new Font("SansSerif", Font.PLAIN, 15));
                area_add_labor.setSize(325, 20);
                 
                locx=10; locy=10;
                area_add_labor.setLocation(locx, locy);
                
                locy+=40;
                locx+=120;
                area_field_add.setLocation(locx, locy);
                
                locy+=85;
                locx-=45;
                add_cooc.setLocation(locx, locy);
                
                cooc.setLayout(null);
                cooc.add(area_add_labor);
                //cooc.add(fillers[0]);
                cooc.add(area_field_add);                
                //cooc.add(fillers[1]);
                //cooc.add(fillers[2]);                
                cooc.add(add_cooc);
                
                
                JButton add_event = new JButton("       Send Event      ");
                add_event.setFont(new Font("SansSerif", Font.PLAIN, 15));
                add_event.setSize(300, 35);
                
                final JComboBox layer_add=new JComboBox();
                layer_add.setFont(new Font("SansSerif", Font.PLAIN, 15));
                layer_add.setSize(200, 35);
                JLabel layer_add_labor=new JLabel("                     Event Type                     ", JLabel.TRAILING);
                layer_add_labor.setFont(new Font("SansSerif", Font.PLAIN, 15));
                layer_add_labor.setSize(325, 20);
                layer_add_labor.setLabelFor(layer_add);
                layer_add.addItem("Light");
                layer_add.addItem("Temperature");
                
                locx=10; locy=10;
                layer_add_labor.setLocation(locx, locy);
                
                locy+=40;
                locx+=120;
                layer_add.setLocation(locx, locy);
                
                locy+=85;
                locx-=45;
                add_event.setLocation(locx, locy);
                
                event.setLayout(null);
                event.add(layer_add_labor);
                //cooc.add(fillers[0]);
                event.add(layer_add);                
                //cooc.add(fillers[1]);
                //cooc.add(fillers[2]);                
                event.add(add_event);               
                
                /* for control page */
                controlP.setPreferredSize(new Dimension(fixedSize.width, fixedSize.height));

                predicate_show.setEditable(false);
                ////////Masud's addition
                DefaultCaret caret = (DefaultCaret)logs.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                DefaultCaret caret2 = (DefaultCaret)predicate_show.getCaret();
                caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                //predicate_show.append("Es#(Threshold, Area, time)\n");
                //predicate.append("Es2(90, 50, now)\n");
                //predicate.append("Eco1(Es1, Es1, 20, 0)\n");
                JScrollPane areaScrollPane = new JScrollPane(predicate_show);
                areaScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                areaScrollPane.setPreferredSize(new Dimension(textBoxWidth, (int)(textBoxHeight*0.75)));
                TitledBorder titleBorder = BorderFactory.createTitledBorder("Parameters");
                titleBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 15));
                areaScrollPane.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createCompoundBorder(
                                       titleBorder,
                                        BorderFactory.createEmptyBorder(2,2,2,2)),
                                areaScrollPane.getBorder()));
                predicate_show.setFont(new Font("SansSerif", Font.PLAIN, 15));



                //logs.append("Es1 occurred\n");
                //logs.append("Es2 occurred\n");
                logs.setEditable(false);
                JScrollPane logs_scroll = new JScrollPane(logs);
                logs_scroll.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                logs_scroll.setPreferredSize(new Dimension(textBoxWidth, textBoxHeight));
                titleBorder = BorderFactory.createTitledBorder("Logs");
                titleBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 15));
                logs_scroll.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createCompoundBorder(
                                        titleBorder,
                                        BorderFactory.createEmptyBorder(2,2,2,2)),
                                logs_scroll.getBorder()));
                logs.setFont(new Font("SansSerif", Font.PLAIN, 15));

                JButton newexp=new JButton("            Reset Parameters            ");
                //JButton addexp=new JButton("                Modify \u03B4                ");
                //JButton gotocooc=new JButton("           Modify \u0194           ");
                JButton addexp=new JButton("           Edit Energy T-hold, mA (\u03B4)           ");
                JButton gotocooc=new JButton("           Edit Time-Period, S (\u0194)           ");
                JButton gotophen=new JButton("          Modify Phenomena Type          ");
                newexp.setFont(new Font("SansSerif", Font.PLAIN, 15));
                addexp.setFont(new Font("SansSerif", Font.PLAIN, 15));
                gotocooc.setFont(new Font("SansSerif", Font.PLAIN, 15));
                gotophen.setFont(new Font("SansSerif", Font.PLAIN, 15));
                //hopLabel = new JLabel
                
                //setting button locations properly
                controlP.add(areaScrollPane);
                controlP.add(fillers[0]);
                controlP.add(newexp);
                controlP.add(fillers[1]);
                controlP.add(addexp);
                controlP.add(fillers[2]);
                controlP.add(gotocooc);
                controlP.add(fillers[3]);
                controlP.add(gotophen);
                controlP.add(fillers[4]);
                controlP.add(logs_scroll);
                
                cards.add(controlP,second);
                cards.add(add, third);
                cards.add(cooc,forth);
                cards.add(event, fifth);
                
                this.add(cards);
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards,second);
                //layer_add.setSelectedIndex(0);
                
                       /*add pre*/
                
        //edit energy threshold
        add_pre.addActionListener(new ActionListener() {           //************* we add predicate here
            public void actionPerformed(ActionEvent e) {
                //print_name.setText("");
                flag=false;

                    //System.out.print(thre_temp);
                    //System.out.print(area_temp);
                    //System.out.println("area: "+area_temp+" threshold: "+thre_temp+" layer: "+layer_add.getSelectedIndex());
                    //***************send temp out
                    //
                    int energy_temp = Integer.parseInt(threshold_add.getText());
                    //Compute Rectangle Area from Energy Threshold
                    // Not transmitting telosb = 2.5 mA
                    // Transmitting telosb = 20 mA
                    int k=(energy_temp/Constants.ENERGY_ACTIVE_MOTE);
                    if (k%2 == 1 && k!=9 && k!=25){
                        k++;
                    }
                    double area = (k * k * (Constants.AREA_HEIGHT-100)* (Constants.AREA_WIDTH-100)) / Constants.TOTAL_MOTES;
                    
                    short a=1, b=1;
                    if(k==1 || k==4 || k==9 || k==16 || k==25 || k==36){
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
                    if(k==20 || k==2) {a=5; b =4;}
                    if(k==24 || k==26 || k==28) {a=6; b=4;}
                    if(k==30 || k==32 || k==34) {a=6; b=5;}
                    if(k==36) {a=6; b=6;}
                    ////////////////////////////////////////////////////////////////////////////////////////////
                    
                    short x = (short)Math.sqrt(area/ (a*a*b*b));
                    short rheight = (short) (b*x);  ///rectangle size
                    short rwidth = (short) (a*x);
                    
                    if(rwidth % 2 == 1) rwidth+=1;
                    if(rheight % 2 == 1) rheight+=1;
                    
                    SunSpotHostApplication.coverage = new Area(rwidth, rheight);
                    SunSpotHostApplication.enclosed_objects_no = (short)k;
                    SunSpotHostApplication.energy_thresh = (short) energy_temp;
                    SunSpotHostApplication.send_setup();
                    
                    String prenew="Energy Threshold, \u03B4 = "+Integer.toString(SunSpotHostApplication.energy_thresh)+" mA.\n"+"Time-Period, \u0194 = "+Integer.toString(SunSpotHostApplication.time_period)+" S.\n"
                            +"Phenomena Type = "+SunSpotHostApplication.layer_type[layer_add.getSelectedIndex()]+". \n"+"Rectangle Size = ("+SunSpotHostApplication.coverage.width+","+SunSpotHostApplication.coverage.height+"). \n"
                            +"No of Active Nodes = "+SunSpotHostApplication.enclosed_objects_no+". \n";

                    predicate_show.setText(prenew);
                    logs.append("Energy Threshold Modified.");
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards,second);
                    //layer_add.setSelectedIndex(0);

                    //SunSpotHostApplication.task.go();
            }
        });
        
               /* add coocurence */
                /// Time-period
        add_cooc.addActionListener(new ActionListener() {           //************* we add predicate here
            public void actionPerformed(ActionEvent e) {
                       
                    SunSpotHostApplication.time_period = Short.parseShort(area_field_add.getText());
                    SunSpotHostApplication.send_setup();
                    String prenew="Energy Threshold, \u03B4 = "+Integer.toString(SunSpotHostApplication.energy_thresh)+" mA.\n"+"Time-Period, \u0194 = "+Integer.toString(SunSpotHostApplication.time_period)+" S.\n"
                            +"Phenomena Type = "+SunSpotHostApplication.layer_type[layer_add.getSelectedIndex()]+". \n"+"Rectangle Size = ("+SunSpotHostApplication.coverage.width+","+SunSpotHostApplication.coverage.height+"). \n"
                            +"No of Active Nodes = "+SunSpotHostApplication.enclosed_objects_no+". \n";
                    predicate_show.setText(prenew);
                    logs.append("Time-period Modified.");
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards,second);
            }
        });
        
          ////  event type //phenomena
        add_event.addActionListener(new ActionListener() {           //************* we add predicate here
            public void actionPerformed(ActionEvent e) {
                //print_name.setText("");                        
                    SunSpotHostApplication.current_phenomena = (short) (layer_add.getSelectedIndex() + 2);
                    SunSpotHostApplication.send_setup();
                    String prenew="Energy Threshold, \u03B4 = "+Integer.toString(SunSpotHostApplication.energy_thresh)+" mA.\n"+"Time-Period, \u0194 = "+Integer.toString(SunSpotHostApplication.time_period)+" S.\n"
                            +"Phenomena Type = "+SunSpotHostApplication.layer_type[layer_add.getSelectedIndex()]+". \n"+"Rectangle Size = ("+SunSpotHostApplication.coverage.width+","+SunSpotHostApplication.coverage.height+"). \n"
                            +"No of Active Nodes = "+SunSpotHostApplication.enclosed_objects_no+". \n";
                    predicate_show.setText(prenew);
                    logs.append("Phenomena Type Modified.");
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards,second);
                    layer_add.setSelectedIndex(0);
            }
        });
        
        
        /*add new exp */
        ///Energy Threshold
        //Ask top?
        addexp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //print_name.setText("");
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, third);
                //SunSpotHostApplication.task.stop();
            }
        });
        
                
        /* go back, RESET*/
        newexp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //print_name.setText("");
                predicate_show.setText("");
                SunSpotHostApplication.BruteHC=0;
                SunSpotHostApplication.OurHC=0;
                SunSpotHostApplication.task.stop();
                logs.setText("");
                
                SunSpotHostApplication.reset_telosb();
                //clearing shape_receieve in reset click
                  SunSpotHostApplication.task.stop();
                  SunSpotHostApplication.frame.shapePanel.repaint();
                  
                  if (action != null && actionEnabled) action.run();

            }
        });
        
                /* goto coocurence page*/
        gotocooc.addActionListener(new ActionListener() {           //************* we add predicate here
            public void actionPerformed(ActionEvent e) {
                //print_name.setText("");

                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards,forth);
                //SunSpotHostApplication.task.stop();
            }
        });

        gotophen.addActionListener(new ActionListener() {           //************* we add predicate here
            public void actionPerformed(ActionEvent e) {
                //print_name.setText("");
                flag=false;
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards,fifth);
                //SunSpotHostApplication.task.stop();
            }
        });
        


		
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
			}*

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
		Tween.to(UserPanel.this, Accessor.BORDER_THICKNESS, 0.4f)
			.target(10)
			.start(tweenManager);
	}

	private void hideBorder() {
		tweenManager.killTarget(borderThickness);
		Tween.to(UserPanel.this, Accessor.BORDER_THICKNESS, 0.4f)
			.target(2)
			.start(tweenManager);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gg = (Graphics2D) g;

		int w = getWidth();
		int h = getHeight();

		int t = borderThickness;
		gg.setColor(BORDER_COLOR);
		gg.fillRect(0, 0, t, h-1);
		gg.fillRect(0, 0, w-1, t);
		gg.fillRect(0, h-1-t, w-1, t);
		gg.fillRect(w-1-t, 0, t, h-1);
	}

	// -------------------------------------------------------------------------
	// Tween Accessor
	// -------------------------------------------------------------------------

	public static class Accessor extends SLAnimator.ComponentAccessor {
		public static final int BORDER_THICKNESS = 100;

		@Override
		public int getValues(Component target, int tweenType, float[] returnValues) {
			UserPanel tp = (UserPanel) target;

			int ret = super.getValues(target, tweenType, returnValues);
			if (ret >= 0) return ret;

			switch (tweenType) {
				case BORDER_THICKNESS: returnValues[0] = tp.borderThickness; return 1;
				default: return -1;
			}
		}

		@Override
		public void setValues(Component target, int tweenType, float[] newValues) {
			UserPanel tp = (UserPanel) target;

			super.setValues(target, tweenType, newValues);

			switch (tweenType) {
				case BORDER_THICKNESS:
					tp.borderThickness = Math.round(newValues[0]);
					tp.repaint();
					break;
			}
		}
	}
}
