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
import rpg.world.biomes.Biome;
import rpg.world.biomes.PlainsBiome;
import rpg.world.biomes.OceanBiome;
import rpg.world.noise.SimplexNoise;

public final class RPG implements Runnable
{
	// Thread
	public AtomicBoolean running = new AtomicBoolean(true);
	
	// Viewport
	public Vector2 viewportPosition = Vector2.Zero;
	public Vector2 viewportScale = Vector2.One;
	
	public Input input = new Input();
	
	public RPG()
	{
		// Window
		frame.setResizable(false);
		frame.setSize(config.<Integer>get("width"), config.<Integer>get("height"));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowListener()
		{
			@Override
			public void windowClosing(WindowEvent e) { running.set(false); }
			
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
		
		PlainsBiome plains = new PlainsBiome();
		plains.noiseThreashold = 0.2;
		
		ProceduralWorld world = new ProceduralWorld
		(
			new SimplexNoise(),
			new Biome[]
			{
				new OceanBiome(),
				plains
			}
		);
		
		while(running.get())
		{
			// Clear buffer
			canvasGraphics.clearRect(0, 0, width, height);
			
			// Draw here
			world.render(canvasGraphics, viewportPosition, viewportScale);
			input.update();
			
			// Draw buffer
			frameGraphics.drawImage(canvas, 0, 0, width, height, null);
		}
		
		frame.setVisible(false);
		frame.dispose();
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
