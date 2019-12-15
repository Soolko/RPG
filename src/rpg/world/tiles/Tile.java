package rpg.world.tiles;

import rpg.Resources;
import rpg.rendering.SerializedColour;

import java.awt.image.BufferedImage;

public class Tile extends TileDefinition
{
	private static final long serialVersionUID = 663599313743574368L;
	
	public BufferedImage texture;
	public SerializedColour colour;
	
	public Tile(TileDefinition definition)
	{
		key = definition.key;
		
		texturePath = definition.texturePath;
		textureScale = definition.textureScale;
		
		colour = definition.colour;
		
		animated = definition.animated;
		animationFrames = definition.animationFrames;
		
		collideable = definition.collideable;
		
		texture = Resources.setColour(Resources.load(texturePath), this.colour.getColour());
	}
}
