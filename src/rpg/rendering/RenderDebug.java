package rpg.rendering;

import static rpg.rendering.ui.StringTools.drawLine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.security.InvalidParameterException;

import rpg.RPG;
import rpg.Component;
import rpg.RunAlways;
import rpg.maths.Vector2;

public class RenderDebug extends Component
{
	// Default enabled to false
	public RenderDebug() { enabled = false; }
	
	// Drawing
	public int offsetX = 0;
	public int offsetY = 0;
	
	public Color textColour = Color.white;
	protected int backgroundOpacity = 50;
	
	public int getOpacity() { return backgroundOpacity; }
	public void setOpacity(int opacity)
	{
		if(opacity < 0 || opacity > 255)
		{
			throw new InvalidParameterException("The opacity given to " + this + ", \"" + opacity + "\", is out of bounds.\nOpacity must be 0-255.");
		}
		this.backgroundOpacity = opacity;
	}
	
	@Override
	public void renderOverlay(Graphics2D g2d)
	{
		Point offset = new Point(offsetX, offsetY);
		drawLine(g2d, "Frame Updates (FPS): " + RPG.instance.rendersPerSecond.get(), textColour, backgroundOpacity, offset, 0);
		drawLine(g2d, "Physics Updates (TPS): " + RPG.instance.fixedPerSecond.get(), textColour, backgroundOpacity, offset, 1);
	}
	
	// Timeout
	protected double timer = -1;
	protected static final double timeout = 0.2;
	
	@RunAlways @Override
	public synchronized void update(double delta)
	{
		// Timeout between presses
		if(timer >= 0)
		{
			timer += delta;
			if(timer < timeout) return;
			else timer = -1;
		}
		
		toggleEnabled();
	}
	
	protected void toggleEnabled()
	{
		if(RPG.instance.input.isPressed(KeyEvent.VK_F3))
		{
			enabled = !enabled;
			timer = 0;
		}
	}
	
	@Override public void fixedUpdate() { }
	@Override public void render(Graphics2D g2d, Vector2 pos) { }
	@Override public void randomTick() { }
}
