package rpg.entities.player;

import rpg.RPG;
import rpg.Component;
import rpg.maths.Vector2;

import java.awt.*;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.lang.Math.*;

public class PlayerMovement extends Component
{
	// Settings
	public double acceleration = 12;
	public double deceleration = 6;
	public double maxVelocity = 1;
	
	/** Velocity of the player. */
	public double velocityX = 0.0, velocityY = 0.0;
	
	@Override
	public void update(double delta)
	{
		if(RPG.instance.input.isPressed(VK_W) || RPG.instance.input.isPressed(VK_UP)) velocityY += acceleration * delta;
		if(RPG.instance.input.isPressed(VK_S) || RPG.instance.input.isPressed(VK_DOWN)) velocityY -= acceleration * delta;
		if(RPG.instance.input.isPressed(VK_A) || RPG.instance.input.isPressed(VK_LEFT)) velocityX += acceleration * delta;
		if(RPG.instance.input.isPressed(VK_D) || RPG.instance.input.isPressed(VK_RIGHT)) velocityX -= acceleration * delta;
		
		Vector2 vel = new Vector2(velocityX, velocityY);
		double magnitude = vel.magnitude();
		if(magnitude > maxVelocity)
		{
			double modifier = maxVelocity / magnitude;
			vel.x *= modifier;
			vel.y *= modifier;
		}
		velocityX = vel.x();
		velocityY = vel.y();
		
		double absVelX = abs(velocityX);
		double absVelY = abs(velocityY);
		
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
	@Override public void randomTick() { }
}
