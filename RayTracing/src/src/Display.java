package src;


import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.event.ActionEvent;

import javax.swing.*;

import java.util.*;
import java.util.Timer;

public class Display extends Canvas implements Runnable {
	public JPanel panel;
	public JFrame frame;
	public static int shot=0;
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static final String Title = "Regnux";
	private Boolean g = true;
	private Thread thread;
	private Boolean running = false;
	private Game game;
	private int[] pixels;
	private BufferedImage img;
	private static World world;
	private int time;
	private static int toRight;
	private static int forward;
	private static int toTilt;
	private static int toUp;
	private static int sideways;
	private static int upward;
	private static boolean pushing;
	private Timer timer;
	private TimerRunner timerRunner;
	private Robot robot;
	void start() {

		if (running)
			return;
		else
			running = true;
		thread = new Thread(this);
		thread.start();

	}

	public Display(JPanel dummy, JFrame framee) {
		try {
			robot= new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Screen.white();
		panel=dummy;
		frame=framee;
		timerRunner = new TimerRunner();
		timer = new Timer();
		timer.scheduleAtFixedRate(timerRunner, 0, 10);
		time = 0;
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		game = new Game();
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		world = new World();
		pixels = new int[WIDTH * HEIGHT];
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		SAction sAction = new SAction();
		WAction wAction = new WAction();
		AAction aAction = new AAction();
		DAction dAction = new DAction();
		LAction lAction = new LAction();
		RAction rAction = new RAction();
		QAction qAction = new QAction();
		ZAction zAction = new ZAction();
		TAction tAction = new TAction();
		YAction yAction = new YAction();
		DownAction downAction= new DownAction();
		SpaceAction spaceAction= new SpaceAction();
		UpAction upAction = new UpAction();
		
		SReleasedAction sReleasedAction = new SReleasedAction();
		WReleasedAction wReleasedAction = new WReleasedAction();
		AReleasedAction aReleasedAction = new AReleasedAction();
		DReleasedAction dReleasedAction = new DReleasedAction();
		LReleasedAction lReleasedAction = new LReleasedAction();
		RReleasedAction rReleasedAction = new RReleasedAction();
		QReleasedAction qReleasedAction = new QReleasedAction();
		ZReleasedAction zReleasedAction = new ZReleasedAction();
		SpaceReleasedAction spaceReleasedAction = new SpaceReleasedAction();
		DownReleasedAction downReleasedAction = new DownReleasedAction();
		UpReleasedAction upReleasedAction = new UpReleasedAction();
	
		///////////////////////////////////////////////////////////////////////
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed S"),
				"doSAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed W"),
				"doWAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed A"),
				"doAAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed D"),
				"doDAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed LEFT"), "doLAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed RIGHT"), "doRAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed DOWN"), "doDownAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed UP"), "doUpAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed SPACE"),
				"doSpaceAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed Q"), "doQAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed Z"), "doZAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed T"), "doTAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("pressed Y"), "doYAction");
	/////////////////////////////////////////////////////////////////////////////////
		dummy.getInputMap().put(KeyStroke.getKeyStroke("released S"),
				"doSReleasedAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("released W"),
				"doWReleasedAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("released A"),
				"doAReleasedAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("released D"),
				"doDReleasedAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "doLReleasedAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "doRReleasedAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "doDownReleasedAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "doUpReleasedAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"),
				"doSpaceReleasedAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("released Q"), "doQReleasedAction");
		dummy.getInputMap().put(KeyStroke.getKeyStroke("released Z"), "doZReleasedAction");
		////////////////////////////////////////////////////////////////////////////
		
		dummy.getActionMap().put("doSAction", sAction);
		dummy.getActionMap().put("doYAction", yAction);
		dummy.getActionMap().put("doWAction", wAction);
		dummy.getActionMap().put("doAAction", aAction);
		dummy.getActionMap().put("doDAction", dAction);
		dummy.getActionMap().put("doLAction", lAction);
		dummy.getActionMap().put("doRAction", rAction);
		dummy.getActionMap().put("doDownAction", downAction);
		dummy.getActionMap().put("doUpAction", upAction);
		dummy.getActionMap().put("doSpaceAction", spaceAction);
		dummy.getActionMap().put("doQAction", qAction);
		dummy.getActionMap().put("doZAction", zAction);
		dummy.getActionMap().put("doTAction", tAction);
		
		/////////////////////////////////////////////////////////////////////////////////
		dummy.getActionMap().put("doSReleasedAction", sReleasedAction);
		dummy.getActionMap().put("doWReleasedAction", wReleasedAction);
		dummy.getActionMap().put("doAReleasedAction", aReleasedAction);
		dummy.getActionMap().put("doDReleasedAction", dReleasedAction);
		dummy.getActionMap().put("doLReleasedAction", lReleasedAction);
		dummy.getActionMap().put("doRReleasedAction", rReleasedAction);
		dummy.getActionMap().put("doDownReleasedAction", downReleasedAction);
		dummy.getActionMap().put("doUpReleasedAction", upReleasedAction);
		dummy.getActionMap().put("doSpaceReleasedAction", spaceReleasedAction);
		dummy.getActionMap().put("doQReleasedAction", qReleasedAction);
		dummy.getActionMap().put("doZReleasedAction", zReleasedAction);
		
		/////////////////////////////////////////////////////////////////////////////////
		
	}
	
	public void run() {
		while (running) {
			tick();
			if (g)
				render();
		}
	}

	private void tick() {
		game.tick();
	}


	private void render() {
		frame.setAutoRequestFocus(true);
		frame.pack();
		
		Point p= MouseInfo.getPointerInfo().getLocation();
		int x= -(frame.getX()-p.x+WIDTH/2) + 500 - 11;
		int y= 300 - (frame.getY()-p.y+HEIGHT/2) -44;
	
		Screen.render(world);

		panel.requestFocus();
		if (timerRunner.run) {
			time++;
			
			BufferStrategy bs = this.getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(4);
				return;
			}
	for (int i = 0; i < WIDTH * HEIGHT; i++) {
				pixels[i] = Screen.pixels[i];
			}

			Graphics g = bs.getDrawGraphics();
			g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
			g.dispose();
			bs.show();

			timerRunner.run = false;

		}
		

	}



	public static void main(String[] args) {
		
		JPanel dummy = new JPanel();
		JFrame frame = new JFrame();
		Display Game = new Display(dummy,frame);
		
		// Console console = new Console();
		
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//dummy.setPreferredSize(new Dimension(1, 1));
		
		
		
		frame.add(dummy);
		frame.add(Game);
		dummy.requestFocusInWindow();
		frame.setAlwaysOnTop(false);
		frame.setVisible(true);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setTitle(Title);
		Game.start();
		// console.start();
	}

	static class SAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			forward = -10;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class TAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			//System.out.println("trying");
		} // end method actionPerformed()

	}
	static class WAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			forward = 10;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class AAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			sideways = -10;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class DAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			sideways = 10;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class RAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			toRight = 10;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class LAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			toRight = -10;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class DownAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			toUp = -10;
			// System.out.println("trying");
		} // end method actionPerformed()

	}
	static class YAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
		
		
		} // end method actionPerformed()

	}
	static class UpAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			toUp = 10;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class SpaceAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			pushing = true;
		} // end method actionPerformed()

	}

	static class QAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {

			upward = 10;

		} // end method actionPerformed()

	}

	static class ZAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			// world.shoot(world.camera);
			upward = -10;
			// System.out.println("trying");
		} // end method actionPerformed()

	}
	static class SReleasedAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			forward = 0;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class WReleasedAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			forward = 0;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class AReleasedAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			sideways = 0;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class DReleasedAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			sideways = 0;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class RReleasedAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			toRight = 0;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class LReleasedAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			toRight = 0;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class DownReleasedAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			toUp = 0;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class UpReleasedAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			toUp = 0;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class SpaceReleasedAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			pushing = false;
		} // end method actionPerformed()

	}

	static class QReleasedAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			// world.shoot(world.camera);
			upward = 0;
			// System.out.println("trying");
		} // end method actionPerformed()

	}

	static class ZReleasedAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			// world.shoot(world.camera);
			upward = 0;
			// System.out.println("trying");
		} // end method actionPerformed()

	}
}
