package rpg.world.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import rpg.Resources;

public class Tile extends TileDefinition
{
	private static final long serialVersionUID = 663599313743574368L;
	
	public BufferedImage texture;
	
	public Tile(TileDefinition definition)
	{
		key = definition.key;
		
		texturePath = definition.texturePath;
		colour = definition.colour;
		textureScale = definition.textureScale;
		
		animated = definition.animated;
		animationFrames = definition.animationFrames;
		
		collideable = definition.collideable;
		
		final BufferedImage baseTexture;
		try
		{
			baseTexture = ImageIO.read(texturePath);
		}
		catch(IOException e)
		{
			System.err.println("Couldn't read texture \"" + texturePath.getPath() + "\".");
			texture = Resources.MissingTexture;
			return;
		}
		
		texture = Resources.setColour(baseTexture, this.colour);
	}
}
