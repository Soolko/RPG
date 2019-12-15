package rpg.world.tiles;

import org.jetbrains.annotations.NotNull;
import rpg.Resources;
import rpg.rendering.SerializedColour;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends TileDefinition
{
	private static final long serialVersionUID = 663599313743574368L;
	
	public final BufferedImage texture;
	public Color colour;
	
	public Tile(@NotNull TileDefinition definition)
	{
		key = definition.key;
		
		texturePath = definition.texturePath;
		textureScale = definition.textureScale;
		
		colour = definition.colour.getColour();
		
		animated = definition.animated;
		animationFrames = definition.animationFrames;
		
		collideable = definition.collideable;
		
		texture = Resources.load(texturePath, colour);
	}
}
