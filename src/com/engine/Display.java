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
import com.engine.simulation.Config;
import com.engine.simulation.EngineThread;
import com.engine.simulation.Manager;
import com.engine.simulation.Vec2d;

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

//		manager.addThing(new Circle("A", new Vec2d(400, 200), new Vec2d(30, 5), gravitionalAcc, 100, 20));
//		manager.addThing(new Circle("B", new Vec2d(300, 200), new Vec2d(-20, 0), gravitionalAcc, 100, 20));
//		manager.addThing(new Circle("C", new Vec2d(600, 300), new Vec2d(10, -5), gravitionalAcc, 100, 20));
//		manager.addThing(new Circle("D", new Vec2d(500, 300), new Vec2d(-10, -10), gravitionalAcc, 100, 20));
//
//		manager.addThing(new Circle("A", new Vec2d(150, 200), new Vec2d(20, 5), gravitionalAcc, 50, 5));
//		manager.addThing(new Circle("B", new Vec2d(100, 200), new Vec2d(-50, 0), gravitionalAcc, 100, 13));
//		manager.addThing(new Circle("C", new Vec2d(400, 300), new Vec2d(60, -5), gravitionalAcc, 50, 15));
//		manager.addThing(new Circle("D", new Vec2d(150, 300), new Vec2d(-90, -10), gravitionalAcc, 100, 20));
//		manager.addThing(new Circle("E", new Vec2d(10, 20), new Vec2d(90, 30), gravitionalAcc, 70, 10));
//
//		manager.addThing(new Circle		("A", new Vec2d(100, 20), 	new Vec2d(50, 10), 		gravitionalAcc, 1, 35, 1.0f));
//		manager.addThing(new Rectangle	("B", new Vec2d(200, 30), 	new Vec2d(0, -10), 		gravitionalAcc, 2, 10, 20, 0.5f));
//		manager.addThing(new Rectangle	("C", new Vec2d(300, 20), 	new Vec2d(120,  10), 	gravitionalAcc, 1, 20, 15, 0.5f));
//		manager.addThing(new Circle		("D", new Vec2d(400, 40), 	new Vec2d(140, -10), 	gravitionalAcc, 3, 30, 0.6f));
//		manager.addThing(new Rectangle	("E", new Vec2d(200, 400), 	new Vec2d(20,  -80), 	gravitionalAcc, 2, 40, 10, 0.2f));
//
//		//create things
//		manager.addThing(new Circle		("A", new Vec2d(100, 20), 	new Vec2d(50, 10), 		gravitionalAcc, 70, 15, 0.1f));
//		manager.addThing(new Rectangle	("B", new Vec2d(300, 20), 	new Vec2d( 120,  10), 	gravitionalAcc, 70, 55, 60, 1f));
//		manager.addThing(new Circle		("C", new Vec2d(400, 40), 	new Vec2d( 140, -10), 	gravitionalAcc, 70, 25, 0.2f));
//		manager.addThing(new Rectangle	("D", new Vec2d(500, 400), 	new Vec2d(-160,  -80),	gravitionalAcc, 70, 30, 25, 2f));
//		manager.addThing(new Triangle	("G", new Vec2d(100, 100), new Vec2d(120, 100), new Vec2d(100, 120), new Vec2d(0, 1), new Vec2d(0, 1), 70, 0.2f));
//
//		//create walls
//		manager.addWall(new HorizontalWall("y = " + DP_BOTTOM, DP_BOTTOM));
//		manager.addWall(new VerticalWall("x = 0", 0));
//		manager.addWall(new VerticalWall("x = " + DP_RIGHT, DP_RIGHT));


		manager.addRigidBody(new Circle(	"A", new Vec2d(400, 50), new Vec2d(-30, -30), gravitionalAcc, 0, 1, 1, 20));
//		manager.addRigidBody(new Polygon(	"B", new int[] {100, 120, 120, 100},	new int[] {100, 100, 120, 120},	new Vec2d(20, 0),	gravitionalAcc, 0, 1, 1));
//		manager.addRigidBody(new Polygon(	"C", new int[] {0, 30, 30, 0}, 			new int[] {50, 50, 70, 70}, 	new Vec2d(60, -20),	gravitionalAcc, 0, 1, 1));
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
			rb.draw(g2d);
		}

		g = bs.getDrawGraphics();
		g.drawImage(buffer, 0, 0, null);
	}

	public static void main (String[] args) {
		initialize();
	}
}