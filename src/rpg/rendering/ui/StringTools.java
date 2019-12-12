package rpg.rendering.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public final class StringTools
{
	public static void drawLine(Graphics2D g2d, String line, Color textColour, int bgOpacity, Point offset, int lineNumber)
	{
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(line, g2d);
		
		Color previous = g2d.getColor();
		g2d.setColor(new Color(0, 0, 0, bgOpacity));
		
		int horizontalOffset = offset.y + (int) (lineNumber * bounds.getHeight());
		g2d.fillRect(offset.x, horizontalOffset, (int) bounds.getWidth() + 1, (int) bounds.getHeight() + 1);
		
		g2d.setColor(textColour);
		g2d.drawString(line, offset.x, horizontalOffset + (int) bounds.getHeight());
		
		g2d.setColor(previous);
	}

}
