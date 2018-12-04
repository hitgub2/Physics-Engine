package com.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.engine.rigidbody.Circle;
import com.engine.rigidbody.Polygon;
import com.engine.rigidbody.RigidBody;
import com.engine.simulation.*;

public class Display extends Canvas implements Runnable, Config {
	// Threads
	private Thread mainThread;
	private Thread engineThread;
	public static boolean running = false;

	// Graphics 
	private static AffineTransform at;
	private static BufferStrategy bs;
	private static BufferedImage buffer;
	private static Graphics g;
	private static Graphics2D g2d;
	private static GraphicsConfiguration gc;
	private static GraphicsDevice gd;
	private static GraphicsEnvironment ge;

	// Things
	public static Manager manager;

	private void start() {
		if (running)
			return;
		running = true;
		mainThread = new Thread(this);
		mainThread.start();

		engineThread = new EngineThread();
		engineThread.start();

		System.out.println("Engine Thread started");
	}

	private void stop() {
		if (!running)
			return;
		running = false;
		try {
			mainThread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println("Engine Thread stopped");
	}


	private static void initialize() {
		System.out.println(TITLE);
		manager = Manager.getInstance();
		Display display = new Display();
		JFrame frame = new JFrame();

		//key listener
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
					case KeyEvent.VK_0 :
						if(running)
							display.stop();
						else
							display.start();
						break;
					case KeyEvent.VK_1 :
						if(!running)
							return;
						manager.applyInitalConditions();
						System.out.println("restart from initial conditions");
						break;
				}
			}
		});
		frame.setFocusTraversalKeysEnabled(false);
		frame.setFocusable(true);

		frame.add(display);
		frame.pack();
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(DP_WIDTH, DP_HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);

		System.out.println("Display Running...");

		// Get graphics configuration...
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice();
		gc = gd.getDefaultConfiguration();
		// Create off-screen drawing surface
		buffer = gc.createCompatibleImage(DP_WIDTH, DP_HEIGHT);
		// Objects needed for rendering...
		g = null;
		g2d = null;

		System.out.println("Objects Initialized");
		Vec2d gravitionalAcc = new Vec2d(0, Config.GRAVITY);

		manager.addRigidBody(new Circle(	"A", new Vec2d(200, 50), new Vec2d(30, 0), gravitionalAcc, 0, 1, 1, 20));
		manager.addRigidBody(new Circle(	"B", new Vec2d(400, 50), new Vec2d(-30, 0), gravitionalAcc, 0, 1, 1, 20));

		manager.addRigidBody(new Polygon(	"C", new int[] {100, 140, 140, 100},	new int[] {100, 100, 140, 140},	new Vec2d(10, 0),	gravitionalAcc, Math.toRadians(30), 0, 1));
		manager.addRigidBody(new Polygon(	"D", new int[] {240, 280, 280, 240},	new int[] {100, 100, 140, 140},	new Vec2d(-10, 0),	gravitionalAcc, Math.toRadians(30), 0, 1));

		manager.addRigidBody(new Circle(	"E", new Vec2d(400, 100), new Vec2d(30, 0), gravitionalAcc, 0, 0, 1, 20));
		manager.addRigidBody(new Polygon(	"F", new int[] {500, 540, 540, 500},	new int[] {100, 100, 140, 140},	new Vec2d(-30, 0),	gravitionalAcc, Math.toRadians(30), 0, 1));


		//manager.addRigidBody(new Polygon(	"C", new int[] {0, 30, 30, 0}, 		new int[] {50, 50, 70, 70}, 	new Vec2d(60, -20),gravitionalAcc, 0, 1, 1));
		manager.setInitialConditions();
//		manager.printInitialConditions();



		display.start();
	}

	public void run() {
		int fps = 0;
		int frames = 0;

		createBufferStrategy(2);
		bs = this.getBufferStrategy();

		long totalTime = 0;
		long curTime = System.currentTimeMillis();
		long lastTime = curTime;
		while(running) {
			try {
				lastTime = curTime;
				curTime = System.currentTimeMillis();
				totalTime += curTime - lastTime;

				if(totalTime > 100000) {
					totalTime -= 1000;
					fps = frames;
					frames = 0;
				}

				frames++;

				g2d = buffer.createGraphics();

				create_graphics();

				if (!bs.contentsLost()) bs.show();
				// Let the OS have a little time...
				Thread.sleep(15);
			} catch (InterruptedException e) {
			} finally {
				// release resources
				if (g != null) g.dispose();
				if (g2d != null) g2d.dispose();
			}

		}
	}

	private void create_graphics() {
		at = new AffineTransform();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, DP_WIDTH, DP_HEIGHT);
		g2d.setColor(Color.BLACK);
		// Draw things
		int numOfRigidBodies = manager.getNumOfRigidBodies();
		RigidBody rb;
		g2d.setColor(Color.BLACK);
		for (int i = 0; i < numOfRigidBodies; i++) {
			rb = manager.getRigidBody(i);
			if (rb.getName().equals("D")) g2d.setColor(Color.RED);
			rb.draw(g2d);
		}

		g = bs.getDrawGraphics();
		g.drawImage(buffer, 0, 0, null);
	}

	public static void main (String[] args) {
		initialize();

		int[] xA = new int[] {100, 120, 120, 100};
		int[] yA = new int[] {100, 100, 120, 120};

//		g2d.drawPolygon(xA, yA, 4);
		int[] xB = new int[] {0, 30, 30, 0};
		int[] yB = new int[] {50, 50, 70, 70};
//		g2d.drawPolygon(xB, yB, 4);
		Gjk.Output output = new Gjk().distance(xA, yA, xB, yB);

//		g2d.drawLine((int)output.point1.getX(), (int)output.point1.getY(), (int)output.point2.getX(), (int)output.point2.getY());

	}
}