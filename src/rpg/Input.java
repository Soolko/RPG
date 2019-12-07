package rpg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener, Updateable
{
	public double speed = 1.0;
	
	protected boolean[] keys = new boolean[65535];
	public boolean isPressed(int code) { return keys[code]; }
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
	}
	
	@Override
	public void keyTyped(KeyEvent e) { }
	
	public void update()
	{
		if(isPressed(KeyEvent.VK_W)) RPG.instance.viewportPosition.y += speed;
		if(isPressed(KeyEvent.VK_S)) RPG.instance.viewportPosition.y -= speed;
		if(isPressed(KeyEvent.VK_A)) RPG.instance.viewportPosition.x += speed;
		if(isPressed(KeyEvent.VK_D)) RPG.instance.viewportPosition.x -= speed;
	}
}
