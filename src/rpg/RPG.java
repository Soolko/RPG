package rpg;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

import rpg.maths.Vector2;
import rpg.world.ProceduralWorld;
import rpg.world.noise.SimplexNoise;

public final class RPG implements Runnable
{
	// Thread
	public AtomicBoolean running = new AtomicBoolean(true);
	
	// Viewport
	public Component scene;
	
	public Vector2 viewportPosition = Vector2.Zero;
	public Vector2 viewportScale = Vector2.One;
	
	public Input input = new Input();
	
	// Gameloop
	private static final long FixedFrequency = 15;
	public static final long FixedInterval = FixedFrequency * 1000;
	public static final double FixedDelta = FixedInterval / 1000000;
	
	public double delta = 1.0;
	
	public RPG()
	{
		// Window
		boolean fullscreen = config.get("fullscreen");
		
		frame.setResizable(false);
		frame.setSize(config.get("width"), config.get("height"));
		frame.setUndecorated(fullscreen);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowListener()
		{
			@Override public void windowClosing(WindowEvent e) { running.set(false); }
			
			@Override public void windowActivated(WindowEvent e) { }
			@Override public void windowClosed(WindowEvent e) { }
			@Override public void windowDeactivated(WindowEvent e) { }
			@Override public void windowDeiconified(WindowEvent e) { }
			@Override public void windowIconified(WindowEvent e) { }
			@Override public void windowOpened(WindowEvent e) { }
		});
		frame.addKeyListener(input);
	}
	
	@Override
	public void run()
	{
		// Init frame
		frame.setVisible(true);
		
		int width = config.get("width");
		int height = config.get("height");
		
		BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
		Graphics frameGraphics = frame.getGraphics();
		
		// Test World
		ProceduralWorld world = new ProceduralWorld
		(
			new SimplexNoise(),
			ProceduralWorld.DefaultBiomes
		);
		
		long last = System.nanoTime();
		long lag = 0;
		
		while(running.get())
		{
			long current = System.nanoTime();
			long delta = current - last;
			
			last = current;
			last += delta;
			
			while(lag >= FixedInterval)
			{
				fixedUpdate();
				lag -= FixedInterval;
			}
			
			this.delta = (double) lag / 1000000.0;
			update();
			
			render(frameGraphics, canvasGraphics);
		}
		
		// Dispose graphics
		canvasGraphics.dispose();
		frameGraphics.dispose();
		
		// Dispose frame
		frame.setVisible(false);
		frame.dispose();
	}
	
	private synchronized void fixedUpdate()
	{
		
	}
	
	private synchronized void update()
	{
		
	}
	
	private synchronized void render(Graphics frameGraphics, Graphics2D canvasGraphics)
	{
		final int width = config.get("width");
		final int height = config.get("height");
		
		// Clear buffer
		canvasGraphics.clearRect(0, 0, width, height);
		
		// Draw here
		world.render(canvasGraphics, viewportPosition, viewportScale);
		input.update();
		
		// Draw buffer
		frameGraphics.drawImage(canvas, 0, 0, width, height, null);
	}
	
	// Static
	public static final RPG instance;
	
	private static final String name = "RPG";
	private static final String title = name;
	public static final JFrame frame = new JFrame(title);
	
	public static final File configFile = new File(name + ".yml");
	public static final Config config;
	
	// Render static
	public static final double BaseScale = 64;
	
	static
	{
		// Default settings
		Config.Callbacks callbacks = new Config.Callbacks()
		{
			@Override
			public Map<String, Object> defaultState()
			{
				Map<String, Object> map = new HashMap<String, Object>();
				
				// Window Size
				map.put("fullscreen", false);
				map.put("width", 1280);
				map.put("height", 720);
				
				return map;
			}
		};
		
		// Config
		Config cfg = null;
		try						{ cfg = new Config(configFile, callbacks); }
		catch(IOException e)	{ e.printStackTrace(); }
		finally					{ config = cfg; }
		
		// Launch instance
		instance = new RPG();
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		// Start game in new thread
		Thread mainThread = new Thread(instance);
		mainThread.setName("Main Game Thread");
		mainThread.start();
		
		// Join that thread
		mainThread.join();
		
		// Save stuff & exit
		try { config.save(); }
		catch(IOException e)
		{
			System.err.println("Failed to save config.\nReason:");
			e.printStackTrace();
		}
	}
}
