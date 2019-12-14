package rpg;

import static java.lang.Math.sqrt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class Resources
{
	// Textures
	public static final BufferedImage MissingTexture;
	
	static
	{
		// Missing Texture creation
		MissingTexture = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = MissingTexture.createGraphics();
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, 2, 2);
		g2d.setColor(Color.MAGENTA);
		g2d.fillRect(0, 0, 1, 1);
		g2d.fillRect(1, 1, 2, 2);
		g2d.dispose();
	}
	
	public static BufferedImage setColour(BufferedImage image, Color colour)
	{
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for(int x = 0; x < image.getWidth(); x++)
		for(int y = 0; y < image.getHeight(); y++)
		{
			// Get and split pixel into components
			int[] rgba = splitPixel(image.getRGB(x, y));
			
			// Multiply by the colour
			rgba[0] *= colour.getRed();
			rgba[1] *= colour.getGreen();
			rgba[2] *= colour.getBlue();
			rgba[3] *= colour.getAlpha();
			for(int i = 0; i < 4; i++) rgba[i] = (int) sqrt(rgba[i]);
			
			// Recombine into pixel to set
			int pixel = combinePixel(rgba);
			output.setRGB(x, y, pixel);
		}
		
		return output;
	}
	
	private static int[] splitPixel(int pixel)
	{
		return new int[]
		{
			(pixel >> 16) & 0xFF,	// R
			(pixel >> 8) & 0xFF,	// G
			pixel & 0xFF,			// B
			(pixel >> 24) & 0xFF	// A
		};
	}
	
	private static int combinePixel(int[] pixel) { return combinePixel(pixel[0], pixel[1], pixel[2], pixel[3]); }
	private static int combinePixel(int r, int g, int b, int a)
	{
		int argb = 0;
		
		argb |= a << 24;
		argb |= r << 16;
		argb |= g << 8;
		argb |= b;
		
		return argb;
	}
}
