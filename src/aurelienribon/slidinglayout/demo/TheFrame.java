package aurelienribon.slidinglayout.demo;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.SLConfig;
import aurelienribon.slidinglayout.SLKeyframe;
import aurelienribon.slidinglayout.SLPanel;
import aurelienribon.slidinglayout.SLSide;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.CardLayout;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class TheFrame extends JFrame {
        private Dimension size = new Dimension();
	private final SLPanel panel = new SLPanel();
	private final ThePanel headerPanel; 
	public final ShapePanel shapePanel;
	private final UserPanel userPanel;
	public final ValuePanel valuePanel; 
        private final StartPanel startPanel;
	private final SLConfig headerPanelCfg, shapePanelCfg, userPanelCfg, valuePanelCfg, startPanelCfg;
        

	public TheFrame(Dimension initialSize) {
                //initial size calculations
                size=initialSize;
                int shapePanelHeight = (int)(((float)10/11) * initialSize.height);
                int shapePanelWidth = (int)(((float)3/4) * initialSize.width);
                int headerPanelHeight = initialSize.height - shapePanelHeight;
                int headerPanelWidth = initialSize.width;
                int userPanelWidth = initialSize.width - shapePanelWidth;
                int userPanelHeight = (int)(((float)2/3) * shapePanelHeight);
                int valuePanelWidth = userPanelWidth;
                int valuePanelHeight = shapePanelHeight - userPanelHeight;
                
                System.out.println(shapePanelWidth+" "+shapePanelHeight+" "+headerPanelWidth+" "+headerPanelHeight+" "+userPanelWidth+" "+userPanelHeight+" "+valuePanelWidth+" "+valuePanelHeight);
                
                
                headerPanel = new ThePanel("1", "data/img1.jpg");
                shapePanel = new ShapePanel(new Dimension(shapePanelWidth, shapePanelHeight));
                userPanel = new UserPanel(new Dimension(userPanelWidth, userPanelHeight));
                valuePanel = new ValuePanel(new Dimension(valuePanelWidth, valuePanelHeight));
                startPanel = new StartPanel(size);
                
                setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Evolving Shapes in Wireless Sensor Networks - Northwestern University");
		getContentPane().setBackground(new Color(0x540799));
		getContentPane().add(panel, BorderLayout.CENTER);

		headerPanel.setAction(headerPanelAction);
		shapePanel.setAction(shapePanelAction);
		userPanel.setAction(userPanelAction);
		valuePanel.setAction(valuePanelAction);
                startPanel.setAction(startPanelBackAction);

		headerPanelCfg = new SLConfig(panel)
			.gap(10, 10)
			.row(1f).row(10f).col(1f)
			.beginGrid(0, 0)
				.row(1f).col(1f)
				.place(0, 0, headerPanel)
			.endGrid()
			.beginGrid(1, 0)
				.row(1f).col(3f).col(1f)
				.place(0, 0, shapePanel)
                                .beginGrid(0, 1)
                                      .row(2f).row(1f).col(1f)
                                      .place(0, 0, userPanel)
                                      .place(1,0, valuePanel)
                                .endGrid()
			.endGrid();

		shapePanelCfg = new SLConfig(panel)
			.gap(10, 10)
			.row(1f).row(10f).col(1f)
			.beginGrid(0, 0)
				.row(1f).col(1f)
				.place(0, 0, headerPanel)
			.endGrid()
			.beginGrid(1, 0)
				.row(1f).col(3f).col(1f)
				.place(0, 0, shapePanel)
                                .beginGrid(0, 1)
                                      .row(2f).row(1f).col(1f)
                                      .place(0, 0, userPanel)
                                      .place(1,0, valuePanel)
                                .endGrid()
			.endGrid();

		userPanelCfg = new SLConfig(panel)
			.gap(10, 10)
			.row(1f).row(10f).col(1f)
			.beginGrid(0, 0)
				.row(1f).col(1f)
				.place(0, 0, headerPanel)
			.endGrid()
			.beginGrid(1, 0)
				.row(1f).col(3f).col(1f)
				.place(0, 0, userPanel)
                                .beginGrid(0, 1)
                                      .row(2f).row(1f).col(1f)
                                      .place(0, 0, shapePanel)
                                      .place(1,0, valuePanel)
                                .endGrid()
			.endGrid();

		valuePanelCfg = new SLConfig(panel)
			.gap(10, 10)
			.row(1f).row(10f).col(1f)
			.beginGrid(0, 0)
				.row(1f).col(1f)
				.place(0, 0, headerPanel)
			.endGrid()
			.beginGrid(1, 0)
				.row(1f).col(3f).col(1f)
				.place(0, 0, valuePanel)
                                .beginGrid(0, 1)
                                      .row(2f).row(1f).col(1f)
                                      .place(0, 0, userPanel)
                                      .place(1,0, shapePanel)
                                .endGrid()
			.endGrid();
                
                startPanelCfg = new SLConfig(panel)
			.gap(10, 10)
			.row(1f).col(1f)
			.place(0, 0, startPanel);
                                
                                
		panel.setTweenManager(SLAnimator.createTweenManager());
		panel.initialize(startPanelCfg);
                
                //Setting initial options
                this.setSize(size.width, size.height);
	        this.setLocationRelativeTo(null);
	        this.setVisible(true);
                
                //setting up sliding actions enable/disable, initially only panel userPanel is enabled
                disableActions();
                startPanel.enableAction();
	}

	private void disableActions() {
		headerPanel.disableAction();
		shapePanel.disableAction();
		userPanel.disableAction();
		valuePanel.disableAction();
                startPanel.disableAction();
	}

	private void enableActions() {
		headerPanel.enableAction();
		shapePanel.enableAction();
		userPanel.enableAction();
		valuePanel.enableAction();
                startPanel.enableAction();
	}

	private final Runnable headerPanelAction = new Runnable() {@Override public void run() {
		/*disableActions();

		panel.createTransition()
			.push(new SLKeyframe(headerPanelCfg, 0.6f)
				.setEndSide(SLSide.BOTTOM, shapePanel)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					headerPanel.setAction(headerPanelBackAction);
					headerPanel.enableAction();
				}}))
			.play();*/
	}};

	private final Runnable headerPanelBackAction = new Runnable() {@Override public void run() {
		/*disableActions();

		panel.createTransition()
			.push(new SLKeyframe(mainCfg, 0.6f)
				.setStartSide(SLSide.BOTTOM, shapePanel)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					headerPanel.setAction(headerPanelAction);
					enableActions();
				}}))
			.play();*/
	}};

	private final Runnable shapePanelAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(shapePanelCfg, 0.8f)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					shapePanel.setAction(shapePanelBackAction);
                                        valuePanel.setAction(valuePanelAction);
					enableActions();
				}}))
			.play();
	}};

	private final Runnable shapePanelBackAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(valuePanelCfg, 0.8f)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					shapePanel.setAction(shapePanelAction);
                                        valuePanel.setAction(valuePanelBackAction);
					enableActions();
				}}))
			.play();
	}};

	private final Runnable userPanelAction = new Runnable() {@Override public void run() {
		/*disableActions();

		panel.createTransition()
			.push(new SLKeyframe(userPanelCfg, 0.8f)
				.setEndSide(SLSide.LEFT, headerPanel, shapePanel)
				.setEndSide(SLSide.BOTTOM, valuePanel)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					userPanel.setAction(userPanelBackAction);
					userPanel.enableAction();
				}}))
			.play();*/
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(startPanelCfg, 0.8f)
				.setEndSide(SLSide.TOP, headerPanel)
                                .setEndSide(SLSide.LEFT, shapePanel)
                                .setEndSide(SLSide.RIGHT, userPanel, valuePanel)
                                .setStartSide(SLSide.BOTTOM, startPanel)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					startPanel.setAction(startPanelBackAction);
					startPanel.enableAction();
				}}))
			.play();
	}};

	private final Runnable userPanelBackAction = new Runnable() {@Override public void run() {
		/*disableActions();

		panel.createTransition()
			.push(new SLKeyframe(shapePanelCfg, 0.8f)
				.setStartSide(SLSide.LEFT, headerPanel, shapePanel)
				.setStartSide(SLSide.BOTTOM, valuePanel)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					userPanel.setAction(userPanelAction);
					enableActions();
				}}))
			.play();*/
	}};

	private final Runnable valuePanelAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(valuePanelCfg, 0.8f)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					valuePanel.setAction(valuePanelBackAction);
                                        shapePanel.setAction(shapePanelAction);
					enableActions();
				}}))
			.play();
	}};

	private final Runnable valuePanelBackAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(shapePanelCfg, 0.8f)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					valuePanel.setAction(valuePanelAction);
                                        shapePanel.setAction(shapePanelBackAction);
					enableActions();
				}}))
			.play();
	}};
        
	private final Runnable startPanelAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(startPanelCfg, 0.8f)
				.setEndSide(SLSide.TOP, headerPanel)
                                .setEndSide(SLSide.LEFT, shapePanel)
                                .setEndSide(SLSide.RIGHT, userPanel, valuePanel)
                                .setStartSide(SLSide.BOTTOM, startPanel)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					startPanel.setAction(startPanelBackAction);
					startPanel.enableAction();
				}}))
			.play();
	}};

	private final Runnable startPanelBackAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(shapePanelCfg, 0.8f)
				.setStartSide(SLSide.TOP, headerPanel)
                                .setStartSide(SLSide.LEFT, shapePanel)
                                .setStartSide(SLSide.RIGHT, userPanel, valuePanel)
                                .setEndSide(SLSide.BOTTOM, startPanel)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					startPanel.setAction(startPanelAction);
                                        shapePanel.setAction(shapePanelBackAction);
					enableActions();
				}}))
			.play();
	}};
}
