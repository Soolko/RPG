package rpg.player;

import static java.lang.Math.*;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import rpg.RPG;
import rpg.architecture.Component;
import rpg.maths.Vector2;

public class Input extends Component implements KeyListener
{
	public double acceleration = 12;
	public double deceleration = 6;
	public double maxVelocity = 1;
	
	protected boolean[] keys = new boolean[65535];
	public boolean isPressed(int code) { return keys[code]; }
	
	// Key Listener overrides
	@Override public void keyPressed(KeyEvent e) { keys[e.getKeyCode()] = true; }
	@Override public void keyReleased(KeyEvent e) { keys[e.getKeyCode()] = false; }
	@Override public void keyTyped(KeyEvent e) { }
	
	private double velocityX = 0.0, velocityY = 0.0;
	private double stopThreashold = 0.05;
	
	@Override
	public void update(double delta)
	{
		if(isPressed(KeyEvent.VK_W) || isPressed(KeyEvent.VK_UP)) velocityY += acceleration * delta;
		if(isPressed(KeyEvent.VK_S) || isPressed(KeyEvent.VK_DOWN)) velocityY -= acceleration * delta;
		if(isPressed(KeyEvent.VK_A) || isPressed(KeyEvent.VK_LEFT)) velocityX += acceleration * delta;
		if(isPressed(KeyEvent.VK_D) || isPressed(KeyEvent.VK_RIGHT)) velocityX -= acceleration * delta;
		
		double absVelX = abs(velocityX);
		double absVelY = abs(velocityY);
		
		if(absVelX > maxVelocity) velocityX = velocityX > 0 ? maxVelocity : -maxVelocity;
		if(absVelY > maxVelocity) velocityY = velocityY > 0 ? maxVelocity : -maxVelocity;
		
		absVelX = abs(velocityX);
		absVelY = abs(velocityY);
		
		absVelX -= deceleration * delta;
		absVelY -= deceleration * delta;
		
		if(absVelX <= 0) { absVelX = 0; }
		if(absVelY <= 0) { absVelY = 0; }
		
		velocityX = velocityX > 0 ? absVelX : -absVelX;
		velocityY = velocityY > 0 ? absVelY : -absVelY;
	}
	
	@Override
	public void fixedUpdate()
	{
		double x = RPG.instance.viewportPosition.x();
		double y = RPG.instance.viewportPosition.y();
		
		x += velocityX * RPG.FixedDelta;
		y += velocityY * RPG.FixedDelta;
		
		RPG.instance.viewportPosition.x = x;
		RPG.instance.viewportPosition.y = y;
	}
	
	// Unused
	
	@Override public void render(Graphics2D g2d, Vector2 position) { }
	@Override public void renderOverlay(Graphics2D g2d) { }
}
