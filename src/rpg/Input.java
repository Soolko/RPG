package rpg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener
{
	protected boolean[] keys = new boolean[65535];
	public boolean isPressed(int code) { return keys[code]; }
	
	// Key Listener overrides
	@Override public void keyPressed(KeyEvent e) { keys[e.getKeyCode()] = true; }
	@Override public void keyReleased(KeyEvent e) { keys[e.getKeyCode()] = false; }
	@Override public void keyTyped(KeyEvent e) { }
}
