package rpg.rendering;

import org.jetbrains.annotations.NotNull;
import java.awt.Color;
import java.io.Serializable;

public class SerializedColour implements Serializable
{
	public int r, g, b, a;
	
	public SerializedColour() { this(Color.white); }
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