package aurelienribon.slidinglayout.demo;

import aurelienribon.slidinglayout.SLAnimator;
import static aurelienribon.slidinglayout.demo.UserPanel.cards;
import static aurelienribon.slidinglayout.demo.UserPanel.third;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.sunspotworld.*;
import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ValuePanel extends JPanel {
  private static final Color FG_COLOR = new Color(0xFFFFFF);
  private static final Color BG_COLOR = new Color(0x3B5998);
  private static final Color BORDER_COLOR = new Color(0x000000);

  private static final TweenManager tweenManager = SLAnimator.createTweenManager();
  private Runnable action;
  private boolean actionEnabled = true;
  private boolean hover = false;
  private int borderThickness = 2;
  private double X_multiplier;
  private double Y_multiplier;
  private JButton switchButton = new JButton("Switch Phenomenon");
  private int switchWidth=0;
  private int switchHeight=0;
  private boolean isLight=true;

  public ValuePanel(Dimension d) {
    //setBackground(BG_COLOR);
    setLayout(new BorderLayout());
                
    //initial multipliers
    X_multiplier = d.width / (double) Constants.MAX_X;
    Y_multiplier = d.height / (double) Constants.MAX_Y;
    this.setLayout(null);
                
    switchWidth=(int)((1.0/8.0)*d.width);
    switchHeight=(int)((1.0/20.0)*d.height);
    switchButton.setSize(switchWidth, switchHeight);
    switchButton.setLocation((d.width-switchWidth),0);
    //this.add(switchButton);
    /*add new exp */
                
    //action listener for the button
    switchButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //print_name.setText("");
          isLight = !isLight;
          repaint();
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
          }*/

        @Override
        public void mouseReleased(MouseEvent e) {
          if (action != null && actionEnabled) action.run();
        }
      });
  }

  public void setAction(Runnable action) {this.action = action;}
  public void enableAction() {actionEnabled = true; if (hover) showBorder();}
  public void disableAction() {actionEnabled = false;}

  private void showBorder() {
    tweenManager.killTarget(borderThickness);
    Tween.to(ValuePanel.this, Accessor.BORDER_THICKNESS, 0.4f)
      .target(10)
      .start(tweenManager);
  }

  private void hideBorder() {
    tweenManager.killTarget(borderThickness);
    Tween.to(ValuePanel.this, Accessor.BORDER_THICKNESS, 0.4f)
      .target(2)
      .start(tweenManager);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D gg = (Graphics2D) g;

    int w = getWidth();
    int h = getHeight();
                
    switchWidth=(int)((1.0/8.0)*w);
    switchHeight=(int)((1.0/20.0)*h);
    switchButton.setSize(switchWidth, switchHeight);
    switchButton.setLocation((w-switchWidth),0);
                
    //initial multipliers
    X_multiplier = w / (double) Constants.MAX_X;
    Y_multiplier = h  / (double) Constants.MAX_Y;
    double radius = (0.023*w)+(0.023*h);
    int FontSize = (int)(0.0075*w + 0.0075*h);

    gg.setStroke(new BasicStroke(3));
    gg.setFont(new Font("SansSerif", Font.BOLD, FontSize));
                
    int showValues[]=new int[Constants.TOTAL_MOTES];
    gg.setPaint(new Color(128,0,128,255));
    if(SunSpotHostApplication.current_phenomena==Constants.LIGHT_PHENOMENA){
      gg.drawString("Light",(int)(radius/2), (int)(radius/2));
    }
    else{
      gg.drawString("Temperature",(int)(radius/2), (int)(radius/2));
    }
                
    for(int i=0; i<Constants.TOTAL_MOTES; i++){
      showValues[i]=SunSpotHostApplication.currentValues.get(i);
    }
                
    for(int i=0; i<Constants.TOTAL_MOTES; i++){
      Point p = Constants.getNodeLocation((short)i);
      int x = (short)(p.x * X_multiplier);
      int y = (short)(p.y * Y_multiplier);
                    
      Color color = Color.YELLOW;
      int alpha = 100;
                    
      alpha = (int)((showValues[i]/100.00) * 255.0);
      if(alpha>255){
        alpha=255;
      }
                    
      if(alpha < 0)
        alpha=0;
                    
      // = (SunSpotHostApplication.currentValues.get(i))
      Color colTransparent = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
      gg.setPaint(colTransparent);
      if(Constants.isTelos((short)i)){
        gg.fillOval((int)(x-(radius/2)), (int)(y-(radius/2)),(int)radius, (int)radius);
      }
      else{
        gg.fillRect((int)(x-(radius/2)), (int)(y-(radius/2)),(int)(radius), (int)(radius));
      }
      gg.setPaint(new Color(0,0,0,255));
      gg.drawString(Integer.toString(showValues[i]), x-(int)(radius/8), y+(int)(radius/20));
    }
                
    gg.setPaint(new Color(128,0,128,255));
    gg.setFont(new Font("SansSerif", Font.BOLD, FontSize));
    gg.drawString("HopCount (Ours/Centralized): "+(int)(1.5*SunSpotHostApplication.OurHC)+"/"+(int)(1.5*SunSpotHostApplication.BruteHC),(w/2)-(int)(1.5*radius), (int)(radius/2)); 
  }

  // -------------------------------------------------------------------------
  // Tween Accessor
  // -------------------------------------------------------------------------

  public static class Accessor extends SLAnimator.ComponentAccessor {
    public static final int BORDER_THICKNESS = 100;

    @Override
    public int getValues(Component target, int tweenType, float[] returnValues) {
      ValuePanel tp = (ValuePanel) target;

      int ret = super.getValues(target, tweenType, returnValues);
      if (ret >= 0) return ret;

      switch (tweenType) {
        case BORDER_THICKNESS: returnValues[0] = tp.borderThickness; return 1;
        default: return -1;
      }
    }

    @Override
    public void setValues(Component target, int tweenType, float[] newValues) {
      ValuePanel tp = (ValuePanel) target;

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
