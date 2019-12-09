package rpg;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import rpg.maths.Vector2;

public abstract class Component
{
	public static List<Component> components = new ArrayList<Component>();
	
	public Component() { components.add(this); }
	
	protected abstract void fixedUpdate();
	protected abstract void update();
	protected abstract void render(Graphics2D g2d, Vector2 position, Vector2 scale);
	
	public static void onFixedUpdate()
	{
		System.out.println("ASDF");
		for(Component component : components) component.fixedUpdate();
	}
	
	public static void onUpdate()
	{
		for(Component component : components) component.update();
	}
	
	public static void onRender(Graphics2D g2d, Vector2 position, Vector2 scale)
	{
		for(Component component : components) component.render(g2d, position, scale);
	}
}
