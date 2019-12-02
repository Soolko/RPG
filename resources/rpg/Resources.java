package rpg;
import java.io.File;
import java.io.IOException;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.imageio.ImageIO;

public final class Resources
{
	// Grass Textures
	public static final BufferedImage GrassBase;
	
	static
	{
		GrassBase = load("Textures/Grass.png");
	}
	
	private static BufferedImage load(String path)
	{
		BufferedImage image = null;
		try { image = getImage(path); }
		catch(IOException e)
		{
			System.err.println("Failed to load texture: " + path);
		}
		return image;
	}
	
	public static BufferedImage getImage(String path) throws IOException
	{
		File file = new File(Resources.class.getResource(path).getFile());
		return ImageIO.read(file);
	}
	
	public static BufferedImage setColour(BufferedImage image, Color colour)
	{
		float r = colour.getRed();
		float g = colour.getGreen();
		float b = colour.getBlue();
		
		if(r > 0) r = 255.0f / r * 100.0f;
		if(g > 0) g = 255.0f / g * 100.0f;
		if(b > 0) b = 255.0f / b * 100.0f;
		
		final float[] factors = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		float[] offsets = new float[] { r, g, b, 0.0f };
		
		RescaleOp op = new RescaleOp(factors, offsets, null);
		return op.filter(image, null);
	}
}
