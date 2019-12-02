package rpg.rendering;

import java.awt.Graphics2D;

import rpg.maths.Vector2;

public interface Renderable
{
	public void render(Graphics2D g2d, Vector2 position, Vector2 scale);
}
