package rpg.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.security.InvalidParameterException;

import rpg.RPG;
import rpg.architecture.Component;
import rpg.architecture.RunAlways;
import rpg.maths.Vector2;

public final class RenderDebug extends Component
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
	public synchronized void renderOverlay(Graphics2D g2d)
	{
		drawLine(g2d, "Frame Updates (FPS): " + RPG.instance.rendersPerSecond.get(), 0);
		drawLine(g2d, "Physics Updates (TPS): " + RPG.instance.fixedPerSecond.get(), 1);
		drawLine(g2d, "Delta Time: " + RPG.instance.delta, 2);
	}
	
	private void drawLine(Graphics2D g2d, String line, int lineNumber)
	{
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(line, g2d);
		
		Color previous = g2d.getColor();
		g2d.setColor(new Color(0, 0, 0, backgroundOpacity));
		
		int horizontalOffset = offsetY + (int) (lineNumber * bounds.getHeight());
		g2d.fillRect(offsetX, horizontalOffset, (int) bounds.getWidth() + 1, (int) bounds.getHeight() + 1);
		
		g2d.setColor(textColour);
		g2d.drawString(line, offsetX, horizontalOffset + (int) bounds.getHeight());
		
		g2d.setColor(previous);
	}
	
	protected double timer = -1;
	protected static final double timeout = 0.2;
	
	@RunAlways @Override
	public synchronized void update()
	{
		// Timeout between presses
		if(timer >= 0)
		{
			timer += RPG.instance.delta;
			if(timer < timeout) return;
			else timer = -1;
		}
		
		if(RPG.instance.input.isPressed(KeyEvent.VK_F3))
		{
			enabled = !enabled;
			timer = 0;
		}
	}
	
	@Override
	public void fixedUpdate() { }
	@Override
	public void render(Graphics2D g2d, Vector2 pos, Vector2 scale) { }
}
