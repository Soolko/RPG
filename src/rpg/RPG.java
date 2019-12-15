package rpg;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;

import org.jetbrains.annotations.NotNull;
import rpg.entities.enemies.Enemy;
import rpg.maths.Vector2;
import rpg.entities.player.Player;
import rpg.rendering.ui.DeathScreen;
import rpg.rendering.ui.RenderDebug;
import rpg.world.ProceduralWorld;

public final class RPG implements Runnable
{
	// Thread
	public AtomicBoolean running = new AtomicBoolean(true);
	
	// Viewport
	public Vector2 viewportPosition = Vector2.Zero;
	
	public final Input input = new Input();
	
	// Gameloop
	private static final long FixedFrequency = 16666;
	public static final long FixedInterval = FixedFrequency * 1000;
	public static final double FixedDelta = FixedInterval / 1000000.0;
	
	public double delta = 1.0;
	
	public RPG()
	{
		// Window
		boolean fullscreen = config.get("fullscreen");
		
		frame.setResizable(false);
		frame.setSize(config.get("width"), config.get("height"));
		frame.setUndecorated(fullscreen);
		frame.setLocationRelativeTo(null);
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
	
	private AtomicInteger fixeds = new AtomicInteger(0);
	private AtomicInteger renders = new AtomicInteger(0);
	
	public AtomicInteger fixedPerSecond = new AtomicInteger();
	public AtomicInteger rendersPerSecond = new AtomicInteger();
	
	private final TimerTask clearValuesTask = new TimerTask()
	{
		@Override
		public void run()
		{
			fixedPerSecond.set(fixeds.get());
			rendersPerSecond.set(renders.get());
			
			fixeds.set(0);
			renders.set(0);
		}
	};
	
	@Override
	public synchronized void run()
	{
		// Add the default world as it's unlisted by default
		Component.components.add(ProceduralWorld.DefaultWorld);
		
		// Init frame
		frame.setVisible(true);
		
		// Init timer task
		Timer clearTaskTimer = new Timer();
		clearTaskTimer.scheduleAtFixedRate(clearValuesTask, 1000, 1000);
		
		int width = config.get("width");
		int height = config.get("height");
		Dimension canvasSize = new Dimension(width, height);
		
		// Render setup
		BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D canvasGraphics = canvas.createGraphics();
		Graphics frameGraphics = frame.getContentPane().getGraphics();
		
		// Debug stuff
		RenderDebug renderDebug = new RenderDebug();
		renderDebug.enabled = true;
		
		// Game loop setup
		long last = System.nanoTime();
		long lag = 0;
		
		Enemy enemy = new Enemy("entities/enemy.yml");
		Player player = new Player();
		
		while(running.get())
		{
			long current = System.nanoTime();
			long delta = current - last;
			
			last = current;
			lag += delta;
			
			while(lag >= FixedInterval)
			{
				fixedUpdate();
				fixeds.incrementAndGet();
				lag -= FixedInterval;
			}
			
			this.delta = (double) lag / 1000000000.0;
			update();
			
			render(canvasGraphics, canvasSize);
			
			// Draw buffer
			frameGraphics.drawImage(canvas, 0, 0, null);
			
			// Profiling
			renders.incrementAndGet();
		}
		
		// Dispose timer
		clearTaskTimer.cancel();
		
		// Dispose graphics
		canvasGraphics.dispose();
		frameGraphics.dispose();
		
		// Dispose frame
		frame.setVisible(false);
		frame.dispose();
	}
	
	private void fixedUpdate() { Component.onFixedUpdate(); }
	private void update() { Component.onUpdate(delta); }
	
	private void render(@NotNull Graphics2D g2d, @NotNull Dimension canvas)
	{
		// Clear buffer
		g2d.clearRect(0, 0, canvas.width, canvas.height);
		
		// Draw here
		Component.onRender(g2d, viewportPosition);
		Component.onRenderOverlay(g2d);
	}
	
	// Static
	public static final RPG instance;
	
	private static final String name = "RPG";
	public static final String title = name;
	public static final JFrame frame = new JFrame(title);
	
	public static final File configFile = new File(name + ".yml");
	public static final Config config;
	
	// Render static
	public static final double BaseScale = 96;
	
	static
	{
		// Default settings
		Config.Callbacks callbacks = () ->
		{
			Map<String, Object> map = new HashMap<>();
			
			// Window Size
			map.put("fullscreen", false);
			map.put("width", 1280);
			map.put("height", 720);
			
			return map;
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
		final Thread mainThread = new Thread(instance);
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
