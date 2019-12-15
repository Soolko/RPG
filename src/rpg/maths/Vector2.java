package rpg.maths;

import static java.lang.Math.*;

public class Vector2
{
	// Static helpers
	public static final Vector2 Zero = new Vector2(0.0);
	public static final Vector2 One = new Vector2(1.0);
	
	public static final Vector2 Up = new Vector2(0.0, 1.0);
	public static final Vector2 Down = new Vector2(0.0, -1.0);
	public static final Vector2 Left = new Vector2(-1.0, 0.0);
	public static final Vector2 Right = new Vector2(1.0, 0.0);
	
	// Data
	public double x, y;
	
	// Shader style syntax
	public double x() { return x; }
	public double y() { return y; }
	public Vector2 xx() { return new Vector2(x, x); }
	public Vector2 xy() { return new Vector2(x, y); }
	public Vector2 yx() { return new Vector2(y, x); }
	public Vector2 yy() { return new Vector2(y, y); }
	
	// Constructors
	public Vector2() { this(0.0); }
	public Vector2(double x) { this(x, x); }
	public Vector2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	// Helper methods
	public double magnitude()
	{
		double x2 = x * x;
		double y2 = y * y;
		
		double h2 = x2 + y2;
		return sqrt(h2);
	}
	
	public Vector2 normalize()
	{
		double magnitude = magnitude();
		return new Vector2(x / magnitude, y / magnitude);
	}
	
	// ToString
	@Override
	public String toString()
	{
		return "(" + Maths.round(x, 2) + ", " + Maths.round(y, 2) + ")";
	}
}
