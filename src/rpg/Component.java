package rpg;

import java.awt.Graphics2D;

import rpg.maths.Vector2;

public abstract class Component
{
	protected abstract void fixedUpdate();
	protected abstract void update();
	protected abstract void render(Graphics2D g2d, Vector2 position, Vector2 scale);
}
