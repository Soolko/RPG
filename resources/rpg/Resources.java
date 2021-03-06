package rpg;

import static java.lang.Math.sqrt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import org.yaml.snakeyaml.constructor.Constructor;
import rpg.entities.Entity;
import rpg.world.tiles.TileDefinition;

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
	
	public static BufferedImage load(String path, Color colour)
	{
		BufferedImage image;
		try { image = getImage(path); }
		catch(IOException e)
		{
			System.err.println("Failed to load texture: " + path);
			image = MissingTexture;
		}
		if(colour.equals(Color.white)) return image;
		else return setColour(image, colour);
	}
	
	public static Entity.Definition loadEntity(String yamlPath)
	{
		final Yaml yaml = new Yaml(new Constructor(Entity.Definition.class));
		return yaml.load(Resources.class.getResourceAsStream(yamlPath));
	}
	
	public static TileDefinition loadTile(String yamlPath)
	{
		final Yaml yaml = new Yaml(new Constructor(TileDefinition.class));
		return yaml.load(Resources.class.getResourceAsStream(yamlPath));
	}
	
	private static BufferedImage getImage(String path) throws IOException
	{
		InputStream resource = Resources.class.getResourceAsStream(path);
		
		if(resource == null) return MissingTexture;
		else return ImageIO.read(resource);
	}
	
	@NotNull
	public static BufferedImage setColour(@NotNull BufferedImage image, Color colour)
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
	
	@NotNull
	@Contract(value = "_ -> new", pure = true)
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
	
	@Contract(pure = true)
	private static int combinePixel(@NotNull int[] pixel) { return combinePixel(pixel[0], pixel[1], pixel[2], pixel[3]); }
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
