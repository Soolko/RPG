package rpg.rendering;

import org.jetbrains.annotations.NotNull;
import java.awt.Color;

public class SerializedColour
{
	public int r, g, b, a;
	
	public SerializedColour() { this(255, 255, 255, 255); }
	public SerializedColour(@NotNull Color colour)
	{
		this
		(
			colour.getRed(),
			colour.getGreen(),
			colour.getBlue(),
			colour.getAlpha()
		);
	}
	public SerializedColour(int r, int g, int b, int a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color getColour() { return new Color(r, g, b, a); }
}