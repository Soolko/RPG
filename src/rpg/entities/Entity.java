package rpg.entities;

import rpg.Resources;
import rpg.Component;
import rpg.maths.Vector2;
import rpg.rendering.SerializedColour;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;

public class Entity extends Component
{
	public final Definition definition;
	
	public Vector2 position = Vector2.Zero;
	
	public BufferedImage texture;
	public Definition.AnimationFrame frame;
	
	public Entity(String definitionFile)
	{
		definition = Resources.loadEntity(definitionFile);
		texture = Resources.setColour(Resources.load(definition.texturePath), definition.colour.getColour());
		frame = definition.animationFrames.get(Direction.DOWN.frameID);
	}
	
	public Vector2 getScreenSpacePosition(Vector2 viewport)
	{
		double sX = viewport.x();
		double sY = viewport.y();
		
		double x = sX + position.x;
		double y = sY + position.y;
		
		return new Vector2(sX, sY);
	}
	
	public enum Direction
	{
		UP("up"),
		DOWN("down"),
		LEFT("left"),
		RIGHT("right");
		
		final String frameID;
		Direction(String frameID) { this.frameID = frameID; }
	}
	public Direction direction = Direction.DOWN;
	
	private double health = 100.0;
	public double getHealth() { return health; }
	public void setHealth(double value)
	{
		if(value < health) { onDamaged(health - value); }
		if(value > health) { onHealed(value - health); }
		
		health = value;
	}
	
	protected void onDamaged(double amount)
	{
	
	}
	
	protected void onHealed(double amount)
	{
	
	}
	
	private double animationTimer = 0.0;
	private Direction lastDirection = Direction.DOWN;
	
	@Override
	public void update(double delta)
	{
		if(direction != lastDirection)
		{
			frame = definition.animationFrames.get(direction.frameID);
			animationTimer = 0.0;
		}
		else
		{
			if(animationTimer > definition.animationSpeed)
			{
				frame = definition.animationFrames.get(frame.next);
				animationTimer = 0.0;
			}
			else animationTimer += delta;
		}
		
		lastDirection = direction;
	}
	
	@Override
	public void render(Graphics2D g2d, Vector2 position)
	{
		Vector2 pos = getScreenSpacePosition(position);
		g2d.drawImage
		(
			texture,
			(int) pos.x(), (int) pos.y(),
			definition.textureScale, definition.textureScale,
			frame.x * definition.textureScale, frame.y * definition.textureScale,
			definition.textureScale, definition.textureScale,
			null
		);
	}
	
	public static class Definition implements Serializable
	{
		public Definition() {}
		
		public String texturePath = "";
		public int textureScale = 16;
		public SerializedColour colour = new SerializedColour();
		
		public double animationSpeed = 0.25;
		public HashMap<String, AnimationFrame> animationFrames = new HashMap<>();
		
		public static class AnimationFrame implements Serializable
		{
			public int x = 0, y = 0;
			public String next = null;
		}
	}
	
	// Unused
	@Override public void fixedUpdate() { }
	@Override public void renderOverlay(Graphics2D g2d) { }
	@Override public void randomTick() { }
}
