package rpg.rendering;

import java.awt.Graphics2D;
import java.awt.Image;

import rpg.RPG;
import rpg.maths.Vector2;

public class SpriteRenderer implements Renderable
{
	/**
	 * The texture to draw.
	 */
	public Image texture;
	
	/**
	 * Sets whether to use RPG.BaseScale or the scale value.
	 */
	public boolean globalScale = true;
	/**
	 * Ineffective if globalScale is true.
	 */
	public Vector2 scale = Vector2.One;
	
	public SpriteRenderer(Image texture)
	{
		this.texture = texture;
	}
	
	public Vector2 getScale()
	{
		double x = globalScale ? RPG.BaseScale : texture.getWidth(null) * this.scale.x();
		double y = globalScale ? RPG.BaseScale : texture.getWidth(null) * this.scale.y();
		
		return new Vector2(x, y);
	}
	
	@Override
	public void render(Graphics2D g2d, Vector2 position, Vector2 scale)
	{
		Vector2 thisScale = getScale();
		
		thisScale.x *= scale.x();
		thisScale.y *= scale.y();
		
		g2d.drawImage
		(
			texture,
			(int) position.x(),
			(int) position.y(),
			(int) (position.x() + thisScale.x()),
			(int) (position.y() + thisScale.y()),
			0, 0, texture.getWidth(null), texture.getHeight(null),
			null
		);
	}
}
