package rpg;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import rpg.maths.Vector2;

public class Input extends Component implements KeyListener
{
	public double speed = 16.0;
	
	protected boolean[] keys = new boolean[65535];
	public boolean isPressed(int code) { return keys[code]; }
	
	@Override public void keyPressed(KeyEvent e) { keys[e.getKeyCode()] = true; }
	@Override public void keyReleased(KeyEvent e) { keys[e.getKeyCode()] = false; }
	@Override public void keyTyped(KeyEvent e) { }
	
	@Override
	public void fixedUpdate()
	{
		double x = RPG.instance.viewportPosition.x();
		double y = RPG.instance.viewportPosition.y();
		
		if(isPressed(KeyEvent.VK_W) || isPressed(KeyEvent.VK_UP)) y += speed;
		if(isPressed(KeyEvent.VK_S) || isPressed(KeyEvent.VK_DOWN)) y -= speed;
		if(isPressed(KeyEvent.VK_A) || isPressed(KeyEvent.VK_LEFT)) x += speed;
		if(isPressed(KeyEvent.VK_D) || isPressed(KeyEvent.VK_RIGHT)) x -= speed;
		
		RPG.instance.viewportPosition.x = x;
		RPG.instance.viewportPosition.y = y;
	}
	
	@Override public void update() { }
	@Override public void render(Graphics2D g2d, Vector2 position, Vector2 scale) { }
}
