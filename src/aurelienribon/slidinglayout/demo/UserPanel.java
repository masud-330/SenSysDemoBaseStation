package aurelienribon.slidinglayout.demo;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
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
import org.sunspotworld.CoOccurencePredicate;
import org.sunspotworld.Constants;
import org.sunspotworld.ShapePredicate;
import org.sunspotworld.SunSpotHostApplication;
import static org.sunspotworld.SunSpotHostApplication.Colorname;
import static org.sunspotworld.SunSpotHostApplication.layer_type;
import static org.sunspotworld.SunSpotHostApplication.sendMessage;
import javax.swing.text.DefaultCaret;

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
                
                /////fillers, ugly coding..
                JLabel fillers[] = new JLabel[15];
                for(int i=0; i<fillers.length;i++){
                    fillers[i]=new JLabel("                                                                             ");;
                }
                int textBoxWidth = (int)(0.8*fixedSize.width);
                int textBoxHeight = (int)(((float)10/30)*fixedSize.height);
                
                
                /* for add page*/
                
                int locx=10, locy=10;
                JButton add_pre = new JButton("           Send \u03B4           ");
                add_pre.setFont(new Font("SansSerif", Font.PLAIN, 15));
                add_pre.setSize(300, 35);

                final TextField threshold_add = new TextField();
                threshold_add.setFont(new Font("SansSerif", Font.PLAIN, 15));
                threshold_add.setColumns(20);
                threshold_add.setSize(200, 35);
                
                JLabel shres_add_labor = new JLabel("             Threshold Value             ", JLabel.TRAILING);
                shres_add_labor.setLabelFor(threshold_add);
                shres_add_labor.setFont(new Font("SansSerif", Font.PLAIN, 15));
                shres_add_labor.setSize(325, 20);

                final TextField area_field_add = new TextField();
                area_field_add.setFont(new Font("SansSerif", Font.PLAIN, 15));
                area_field_add.setColumns(20);
                area_field_add.setSize(200, 35);
                JLabel area_add_labor = new JLabel("               Minimum Area              ", JLabel.TRAILING);
                area_add_labor.setLabelFor(area_field_add);
                area_add_labor.setFont(new Font("SansSerif", Font.PLAIN, 15));
                area_add_labor.setSize(325, 20);

                final JComboBox layer_add=new JComboBox();
                layer_add.setFont(new Font("SansSerif", Font.PLAIN, 15));
                layer_add.setSize(200, 35);
                JLabel layer_add_labor=new JLabel("                     Event Type                     ", JLabel.TRAILING);
                layer_add_labor.setFont(new Font("SansSerif", Font.PLAIN, 15));
                layer_add_labor.setSize(325, 20);
                layer_add_labor.setLabelFor(layer_add);
                layer_add.addItem("Light");
                layer_add.addItem("Temperature");
                
                shres_add_labor.setLocation(locx, locy);
                locy+=40;
                locx+=120;
                threshold_add.setLocation(locx, locy);
                locy+=60;
                locx-=120;
                area_add_labor.setLocation(locx, locy);
                locy+=40;
                locx+=120;
                area_field_add.setLocation(locx, locy);
                locy+=60;
                locx-=120;
                layer_add_labor.setLocation(locx, locy);
                locy+=40;
                locx+=120;
                layer_add.setLocation(locx, locy);
                locy+=85;
                locx-=45;
                add_pre.setLocation(locx, locy);
                
                
                add.setLayout(null);
                add.add(shres_add_labor);
                //add.add(fillers[4]);
                add.add(threshold_add);
                //add.add(fillers[5]);
                add.add(area_add_labor);
                //add.add(fillers[6]);
                add.add(area_field_add);
                //add.add(fillers[7]);
                add.add(layer_add_labor);
                //add.add(fillers[8]);
                add.add(layer_add);
                //add.add(fillers[9]);
                add.add(add_pre);
                
                /* for occurence panel*/
                JButton add_cooc = new JButton("       Send \u0194      ");
                add_cooc.setFont(new Font("SansSerif", Font.PLAIN, 15));
                add_cooc.setSize(300, 35);
                
                final JComboBox occurence1=new JComboBox();
                final JComboBox occurence2=new JComboBox();
                occurence1.setFont(new Font("SansSerif", Font.PLAIN, 15));
                occurence2.setFont(new Font("SansSerif", Font.PLAIN, 15));
                occurence1.setSize(200, 35);
                occurence2.setSize(200, 35);

                final TextField distance_in = new TextField();
                distance_in.setFont(new Font("SansSerif", Font.PLAIN, 15));
                distance_in.setColumns(20);
                distance_in.setSize(200, 35);
                
                JLabel distance_in_labor = new JLabel("                Distance                 ", JLabel.TRAILING);
                occurence2.setFont(new Font("SansSerif", Font.PLAIN, 15));
                distance_in.setSize(200, 35);
                distance_in_labor.setLabelFor(distance_in);
                distance_in_labor.setSize(325, 20);
                distance_in_labor.setFont(new Font("SansSerif", Font.PLAIN, 15));
                 
                locx=130;locy=10;
                occurence1.setLocation(locx, locy);
                
                locy+=60;
                occurence2.setLocation(locx, locy);
                
                locy+=60;
                locx-=130;
                distance_in_labor.setLocation(locx, locy);
                
                locx+=130;
                locy+=40;
                distance_in.setLocation(locx, locy);
                
                locx-=45;
                locy+=85;
                add_cooc.setLocation(locx, locy);
                
                cooc.setLayout(null);
                cooc.add(occurence1);
                //cooc.add(fillers[0]);
                cooc.add(occurence2);                
                //cooc.add(fillers[1]);
                cooc.add(distance_in_labor);
                cooc.add(distance_in);
                //cooc.add(fillers[2]);                
                cooc.add(add_cooc);
                
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
                JButton addexp=new JButton("               Modify \u03B4               ");
                JButton gotocooc=new JButton("           Modify \u0194           ");
                JButton gotophen=new JButton("          Modify Phenomena           ");
                newexp.setFont(new Font("SansSerif", Font.PLAIN, 15));
                addexp.setFont(new Font("SansSerif", Font.PLAIN, 15));
                gotocooc.setFont(new Font("SansSerif", Font.PLAIN, 15));
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
                
                this.add(cards);
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards,second);
                layer_add.setSelectedIndex(0);
                
                       /*add pre*/
        add_pre.addActionListener(new ActionListener() {           //************* we add predicate here
            public void actionPerformed(ActionEvent e) {
                //print_name.setText("");
                flag=false;
                int thre_temp=0;
                int area_temp=0;
                if(threshold_add.getText().equals(""))
                {
                    flag=true;
                    errorlabel="empty";

                }
                if(area_field_add.getText().equals(""))
                {
                    flag=true;
                    errorlabel="empty";
                }
                try{
                    thre_temp = Integer.parseInt(threshold_add.getText());
                    threshold_add.setText("");
                }
                catch(NumberFormatException nfe)
                {
                    flag=true;
                    errorlabel="nfe";
                }
                try{
                    area_temp = Integer.parseInt(area_field_add.getText());
                    area_field_add.setText("");

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
                        JOptionPane.showMessageDialog(cards,
                                "Can not have empty input field","Input mistake",
                                JOptionPane.WARNING_MESSAGE);
                        threshold_add.setText("");
                        area_field_add.setText("");
                    }
                    if(errorlabel.equals("nfe"))
                    {
                        JOptionPane.showMessageDialog(cards,
                                "predicate must be number","Input mistake",
                                JOptionPane.WARNING_MESSAGE);
                        threshold_add.setText("");
                        area_field_add.setText("");
                    }

                }
                if(flag==false)
                {
                    //System.out.print(thre_temp);
                    //System.out.print(area_temp);
                    ShapePredicate temp=new ShapePredicate(area_temp,thre_temp,layer_add.getSelectedIndex());
                    System.out.println("area: "+area_temp+" threshold: "+thre_temp+" layer: "+layer_add.getSelectedIndex());
                    SunSpotHostApplication.currentPredicates.add(temp);
                    //***************send temp out
                    
                            int[] arr = new int[Constants.VALUE_SIZE];
                            arr[0]=temp.phenomenonLayer; arr[1]=temp.threshold;
                            arr[2]=temp.area;
                            arr[3]=Constants.TERMINATOR;
                            sendMessage(Constants.SPOT_NEW_PREDICATE,arr ,"0014.4F01.0000.7995" );
                    
                    String prenew=" Es"+Integer.toString((SunSpotHostApplication.currentPredicates.size()))+"("+Integer.toString(temp.threshold)
                            +", "+Integer.toString(temp.area)+", "+SunSpotHostApplication.layer_type[layer_add.getSelectedIndex()]+", "+SunSpotHostApplication.Colorname[SunSpotHostApplication.currentPredicates.size()-1]+")\n"; 

                    predicate_show.append(prenew);
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards,second);
                    layer_add.setSelectedIndex(0);

                    //SunSpotHostApplication.task.go();
                }
            }
        });
        
               /* add coocurence */

        add_cooc.addActionListener(new ActionListener() {           //************* we add predicate here
            public void actionPerformed(ActionEvent e) {
                //print_name.setText("");
                flag=false;
                int distance_temp=0;
                if(distance_in.getText().equals(""))
                {
                    flag=true;
                    errorlabel="empty";

                }
                try{
                    distance_temp = Integer.parseInt(distance_in.getText());
                    distance_in.setText("");
                }
                catch(NumberFormatException nfe)
                {
                    flag=true;
                    errorlabel="nfe";
                }
                if(occurence1.getSelectedIndex()==occurence2.getSelectedIndex())
                {
                    flag=true;
                    errorlabel="sameoccurence";
                }

                if(flag==true)
                {
                    if(errorlabel.equals("empty"))
                    {
                        JOptionPane.showMessageDialog(cards,
                                "Can not have empty input field","Input mistake",
                                JOptionPane.WARNING_MESSAGE);
                        distance_in.setText("");
                    }
                    if(errorlabel.equals("nfe"))
                    {
                        JOptionPane.showMessageDialog(cards,
                                "predicate must be number","Input mistake",
                                JOptionPane.WARNING_MESSAGE);
                        distance_in.setText("");
                    }
                    if(errorlabel.equals("sameoccurence"))
                    {
                        JOptionPane.showMessageDialog(cards,
                                "Occurrence selection can not be same","Selection mistake",
                                JOptionPane.WARNING_MESSAGE);

                    }

                }
                if(flag==false)
                {
                    //System.out.print(thre_temp);
                    //System.out.print(area_temp);
                    CoOccurencePredicate temp_cooc=new CoOccurencePredicate(SunSpotHostApplication.currentPredicates.get(occurence1.getSelectedIndex()),SunSpotHostApplication.currentPredicates.get(occurence2.getSelectedIndex()),distance_temp);
                    SunSpotHostApplication.curCoOccPredicates.add(temp_cooc);
                    
                    //***************send temp out
                    
                            int[] arr = new int[Constants.VALUE_SIZE];
                            arr[0]=temp_cooc.sp1.area; arr[1]=temp_cooc.sp1.threshold;arr[2]=temp_cooc.sp1.phenomenonLayer;
                            arr[3]=temp_cooc.sp2.area; arr[4]=temp_cooc.sp2.threshold;arr[5]=temp_cooc.sp2.phenomenonLayer;
                            arr[6]=temp_cooc.distance;
                            arr[7]=Constants.TERMINATOR;
                            sendMessage(Constants.SPOT_NEW_COOC,arr ,"0014.4F01.0000.7995" );
                    String prenew=" Cooc"+Integer.toString((SunSpotHostApplication.curCoOccPredicates.size()))+"(Es"+Integer.toString(occurence1.getSelectedIndex()+1)
                            +", Es"+Integer.toString(occurence2.getSelectedIndex()+1)+", "+Integer.toString(distance_temp)+")\n";

                    predicate_show.append(prenew);
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards,second);
                    occurence1.removeAllItems();
                    occurence2.removeAllItems();

                    //SunSpotHostApplication.task.go();
                }
            }
        });
        
        /*add new exp */
        addexp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //print_name.setText("");
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, third);
                //SunSpotHostApplication.task.stop();
            }
        });
        
                /* go back, RESET */
        newexp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //print_name.setText("");
                predicate_show.setText("");
                SunSpotHostApplication.currentPredicates.clear();
                SunSpotHostApplication.curCoOccPredicates.clear();
                SunSpotHostApplication.positiveShapes.clear();
                SunSpotHostApplication.BruteHC=0;
                SunSpotHostApplication.OurHC=0;
                SunSpotHostApplication.task.stop();
                logs.setText("");
                //clearing shape_receieve in reset click

                        
                  int[] arr = new int[Constants.VALUE_SIZE];
                  //System.out.println(arr[5]);
                  arr[0]=Constants.TERMINATOR;
                  SunSpotHostApplication.sendMessage(Constants.SPOT_RESET,arr ,"0014.4F01.0000.7995" );
                  SunSpotHostApplication.task.stop();
                  SunSpotHostApplication.frame.shapePanel.repaint();
                  
                  if (action != null && actionEnabled) action.run();

            }
        });
        
                /* goto coocurence page*/
        gotocooc.addActionListener(new ActionListener() {           //************* we add predicate here
            public void actionPerformed(ActionEvent e) {
                //print_name.setText("");
                flag=false;
                if(SunSpotHostApplication.currentPredicates.size()<=1)
                {
                    flag=true;
                }

                if(flag==true)
                {

                        JOptionPane.showMessageDialog(cards,
                                "Need more than one predicate","Input mistake",
                                JOptionPane.WARNING_MESSAGE);



                }
                if(flag==false)
                {
                    for(int i=0;i<SunSpotHostApplication.currentPredicates.size();i++)
                    {
                        String content= " Es"+Integer.toString(i+1)+"("+Double.toString(SunSpotHostApplication.currentPredicates.get(i).threshold)
                                +", "+Double.toString(SunSpotHostApplication.currentPredicates.get(i).area)+", "+layer_type[SunSpotHostApplication.currentPredicates.get(i).phenomenonLayer]+")";
                        occurence1.addItem(content);
                        occurence2.addItem(content);
                    }
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards,forth);
                }
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
