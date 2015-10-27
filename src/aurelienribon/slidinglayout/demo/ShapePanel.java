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
                X_multiplier = d.width / Constants.MAX_X;
                Y_multiplier = d.height / Constants.MAX_Y;
                
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
                X_multiplier = w / Constants.MAX_X;
                Y_multiplier = h  / Constants.MAX_Y;
                double radius = (0.035*w)+(0.035*h);
                boolean pointStats[] = new boolean[Constants.TOTAL_MOTES];
                
                gg.setStroke(new BasicStroke(3));
                
                for(int i=0; i<SunSpotHostApplication.positiveShapes.size(); i++){
                    ShapeOccurrence occur = SunSpotHostApplication.positiveShapes.get(i);
                    int j=0;
                        for(;j<SunSpotHostApplication.currentPredicates.size();j++)
                        {
                                           
                            if(SunSpotHostApplication.currentPredicates.get(j).threshold== occur.predicate.threshold
                                                        &&SunSpotHostApplication.currentPredicates.get(j).area== occur.predicate.area
                                                        &&SunSpotHostApplication.currentPredicates.get(j).phenomenonLayer== occur.predicate.phenomenonLayer)
                            {
                                    break;
                            }
                        }
                        
                    Color color = SunSpotHostApplication.colorlib[j];
                    Color colTransparent = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
                    gg.setPaint(colTransparent);
                        
                    Vector<Point> points = occur.points;
                    int x[]=new int[points.size()-1];
                    int y[]=new int[points.size()-1];
                    for(int k=0; k<points.size()-1;k++){
                        x[k]=(int)((points.get(k).x * X_multiplier));
                        y[k]=(int)((points.get(k).y * Y_multiplier));
                        int id = Constants.locationToID(new Point(points.get(k).x, points.get(k).y));
                        pointStats[id-1]=true;
                        if(id<=Constants.TOTAL_TELOS){
                            gg.fillOval((int)(x[k]-(radius/2)), (int)(y[k]-(radius/2)),(int)(radius), (int)(radius));
                        }
                        else{
                            gg.fillRect((int)(x[k]-(radius/2)), (int)(y[k]-(radius/2)),(int)(radius), (int)(radius));
                        }
                    }
                    
                    gg.fillPolygon(x, y, x.length);
                }
                 gg.setPaint(new Color(0,0,0,255));
                for(int i=0; i<Constants.TOTAL_MOTES; i++){
                    if(pointStats[i]==true){
                        continue;
                    }
                    Point p = Constants.idToLocation(i+1);
                    //System.out.println(p.x+" "+p.y);
                    int x = (int)(p.x * X_multiplier);
                    int y = (int)(p.y * Y_multiplier);
                    //System.out.println(p.x+" "+p.y);
                    
                    if(i<Constants.TOTAL_TELOS){
                        gg.drawOval((int)(x-(radius/2)), (int)(y-(radius/2)),(int)radius, (int)radius);
                    }
                    else{
                        gg.drawRect((int)(x-(radius/2)), (int)(y-(radius/2)),(int)(radius), (int)(radius));
                    }
                }
                gg.setPaint(new Color(128,0,128,255));
                int FontSize = (int)(0.0075*w + 0.0075*h);
                gg.setFont(new Font("SansSerif", Font.BOLD, FontSize));
                gg.drawString("HopCount (Ours/Centralized): "+(int)(1.5*SunSpotHostApplication.OurHC)+"/"+(int)(1.5*SunSpotHostApplication.BruteHC),(w/2)-(int)(1.5*radius), (int)(radius/2)); 
            
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
