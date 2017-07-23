package aurelienribon.slidinglayout.demo;

import aurelienribon.slidinglayout.SLAnimator;
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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sunspotworld.Object;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class ShapePanel extends JPanel {
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

	public ShapePanel(Dimension d) {
		//setBackground(BG_COLOR);
		setLayout(new BorderLayout());
                
    //initial multipliers
    X_multiplier = d.width / (double) Constants.MAX_X;
    Y_multiplier = d.height / (double) Constants.MAX_Y;
    System.out.println("ShapePanel X_mul: " + X_multiplier + " Y_mul: " + Y_multiplier);
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
		Tween.to(ShapePanel.this, Accessor.BORDER_THICKNESS, 0.4f)
			.target(10)
			.start(tweenManager);
	}

	private void hideBorder() {
		tweenManager.killTarget(borderThickness);
		Tween.to(ShapePanel.this, Accessor.BORDER_THICKNESS, 0.4f)
			.target(2)
			.start(tweenManager);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
    try{  
      Graphics2D gg = (Graphics2D) g;

      int w = getWidth();
      int h = getHeight();
      //initial multipliers
      X_multiplier = w / (double) Constants.MAX_X;
      Y_multiplier = h / (double) Constants.MAX_Y;
      // System.out.println("ShapePanel: PaintCom: X_mul: " + X_multiplier + " Y_mul: " + Y_multiplier);
      
      double radius = (0.023*w)+(0.023*h);
                
      gg.setStroke(new BasicStroke(3));
      Color color = Color.YELLOW;
      Color colTransparent = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
      gg.setPaint(colTransparent);
                
      int x1=0, x2=0, y1=0, y2=0;
      //draw rectangle here
      if (SunSpotHostApplication.Opt_Window != null) {
        x1 = Math.max(0, SunSpotHostApplication.Opt_Window.l - SunSpotHostApplication.coverage.height/2);
        y1 = Math.max(0, SunSpotHostApplication.Opt_Window.h - SunSpotHostApplication.coverage.width/2);
        x2 = Math.min(SunSpotHostApplication.area.height, SunSpotHostApplication.Opt_Window.l + SunSpotHostApplication.coverage.height/2);
        y2 = Math.min(SunSpotHostApplication.area.width, SunSpotHostApplication.Opt_Window.h + SunSpotHostApplication.coverage.width/2);
                        
        int g_x1 = (int)((x1 * X_multiplier));
        int g_x2 = (int)((x2 * X_multiplier));
        int g_y1 = (int)((y1 * Y_multiplier));
        int g_y2 = (int)((y2 * Y_multiplier));
                        
        gg.fill3DRect(g_x1, g_y1, (g_x2-g_x1), (g_y2-g_y1), true);
                        
        color = Color.BLUE;
        colTransparent = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
        gg.setPaint(colTransparent);
                        
        for(int k = 0; k < Constants.TOTAL_MOTES; k++) {
          int node_id = Constants.getNodeId(k);
          Object obj = SunSpotHostApplication.currentObjects.get(node_id);
          if(Constants.isWithin(new Rectangle((short)x1, (short)y1, (short)x2, (short)y2,(short)40), obj)){
            int x = (short)(obj.x * X_multiplier);
            int y = (short)(obj.y * Y_multiplier);
            if(Constants.isTelos((short) node_id)) {
              gg.fillOval((int)(x-(radius/2)), (int)(y-(radius/2)),(int)(radius), (int)(radius));
            }
            else{
              gg.fillRect((int)(x-(radius/2)), (int)(y-(radius/2)),(int)(radius), (int)(radius));
            }
          }
        }
      }
                
      gg.setPaint(new Color(0,0,0,255));
      color = Color.BLACK; // edge of the objects
      colTransparent = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
      gg.setPaint(colTransparent);
      for(int i=0; i<Constants.TOTAL_MOTES; i++){
        int node_id = Constants.getNodeId(i);
        Point p = Constants.getNodeLocation((short)node_id);
        if(Constants.isWithin(new Rectangle((short)x1, (short)y1, (short)x2, (short)y2,(short)40), p)){
          continue;
        }
        int x = (short)(p.x * X_multiplier);
        int y = (short)(p.y * Y_multiplier);
                    
        if(Constants.isTelos((short)node_id)){
          gg.drawOval((int)(x-(radius/2)), (int)(y-(radius/2)),(int)radius, (int)radius);
        }
        else{
          gg.drawRect((int)(x-(radius/2)), (int)(y-(radius/2)),(int)(radius), (int)(radius));
        }
      }
      gg.setPaint(new Color(128,0,128,255));
      int FontSize = (int)(0.0075*w + 0.0075*h);
      gg.setFont(new Font("SansSerif", Font.BOLD, FontSize));
      gg.drawString("sp HopCount (Ours/Centralized): "+(int)(1.5*SunSpotHostApplication.OurHC)+"/"+(int)(1.5*SunSpotHostApplication.BruteHC),(w/2)-(int)(1.5*radius), (int)(radius/2)); 
            
    }
    catch(Exception ex){
      System.out.println(ex.getMessage());
    }
	}

	// -------------------------------------------------------------------------
	// Tween Accessor
	// -------------------------------------------------------------------------

	public static class Accessor extends SLAnimator.ComponentAccessor {
		public static final int BORDER_THICKNESS = 100;

		@Override
		public int getValues(Component target, int tweenType, float[] returnValues) {
			ShapePanel tp = (ShapePanel) target;

			int ret = super.getValues(target, tweenType, returnValues);
			if (ret >= 0) return ret;

			switch (tweenType) {
				case BORDER_THICKNESS: returnValues[0] = tp.borderThickness; return 1;
				default: return -1;
			}
		}

		@Override
		public void setValues(Component target, int tweenType, float[] newValues) {
			ShapePanel tp = (ShapePanel) target;

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
