package rpg.maths;

import java.awt.*;

public class RectangleDouble extends Rectangle
{
	public double x, y, width, height;
	
	public RectangleDouble(double x, double y, double width, double height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		super.x = (int) x;
		super.y = (int) y;
		super.width = (int) width;
		super.height = (int) height;
	}
}
