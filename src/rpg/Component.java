package rpg;

import java.awt.Graphics2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jetbrains.annotations.NotNull;
import rpg.maths.Vector2;

public abstract class Component
{
	public Component() { this(false); }
	public Component(boolean unlisted) { if(!unlisted) components.add(this); }
	
	public boolean enabled = true; 
	
	public abstract void fixedUpdate();
	public abstract void update(double delta);
	public abstract void render(Graphics2D g2d, Vector2 position);
	public abstract void renderOverlay(Graphics2D g2d);
	public abstract void randomTick();
	
	// Static
	public static List<Component> components = new CopyOnWriteArrayList<Component>();
	
	public static void onFixedUpdate()
	{
		for(Component component : components)
		{
			try
			{
				if(component.enabled || isRunAlways(component, "fixedUpdate")) component.fixedUpdate();
			}
			catch(Exception e) { throw new RuntimeException(e); }
		}
	}
	
	public static void onUpdate(double delta)
	{
		for(Component component : components)
		{
			try
			{
				if(component.enabled || isRunAlways(component, "update", double.class)) component.update(delta);
			}
			catch(Exception e) { throw new RuntimeException(e); }
		}
	}
	
	public static void onRender(Graphics2D g2d, Vector2 position)
	{
		for(Component component : components)
		{
			try
			{
				if(component.enabled || isRunAlways(component, "render", new Class[] { Graphics2D.class, Vector2.class }))
				{
					component.render(g2d, position);
				}
			}
			catch(Exception e) { throw new RuntimeException(e); }
		}
	}
	
	public static void onRenderOverlay(Graphics2D g2d)
	{
		for(Component component : components)
		{
			try
			{
				if(component.enabled || isRunAlways(component, "renderOverlay", Graphics2D.class))
				{
					component.renderOverlay(g2d);
				}
			}
			catch(Exception e) { throw new RuntimeException(e); }
		}
	}
	
	public static void onRandomTick()
	{
		for(Component component : components)
		{
			try
			{
				if(component.enabled || isRunAlways(component, "randomTick"))
				{
					component.randomTick();
				}
			}
			catch(Exception e) { throw new RuntimeException(e); }
		}
	}
	
	private static boolean isRunAlways(@NotNull Object object, String method) throws NoSuchMethodException, SecurityException
	{
		return object.getClass().getMethod(method).isAnnotationPresent(RunAlways.class);
	}
	
	private static boolean isRunAlways(Object object, String method, Class<?> arg) throws NoSuchMethodException, SecurityException
	{
		return isRunAlways(object, method, new Class[] { arg });
	}
	
	private static boolean isRunAlways(@NotNull Object object, String method, Class<?>[] args) throws NoSuchMethodException, SecurityException
	{
		return object.getClass().getMethod(method, args).isAnnotationPresent(RunAlways.class);
	}
}
