package rpg;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

public final class RPG
{
	public RPG()
	{
		// Window
		frame.setResizable(false);
		frame.setSize(config.<Integer>get("width"), config.<Integer>get("height"));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowListener()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) { }
			@Override
			public void windowClosed(WindowEvent e) { }
			@Override
			public void windowDeactivated(WindowEvent e) { }
			@Override
			public void windowDeiconified(WindowEvent e) { }
			@Override
			public void windowIconified(WindowEvent e) { }
			@Override
			public void windowOpened(WindowEvent e) { }
		});
		frame.setVisible(true);
	}
	
	public static final RPG instance;
	
	// Static
	public static final File configFile = new File("RPG.yml");
	public static final Config config;
	
	private static final String title = "RPG";
	public static final JFrame frame = new JFrame(title);
	
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
	
	public static void main(String[] args)
	{
		System.out.println(config.<Integer>get("width"));
	}
}
