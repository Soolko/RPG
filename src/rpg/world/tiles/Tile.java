package rpg.world.tiles;

import java.awt.Color;
import java.awt.image.BufferedImage;

import rpg.Resources;

public class Tile extends TileDefinition
{
	private static final long serialVersionUID = 663599313743574368L;
	
	public BufferedImage texture;
	public Color colour;
	
	public Tile(TileDefinition definition)
	{
		key = definition.key;
		
		texturePath = definition.texturePath;
		textureScale = definition.textureScale;
		
		colour = new Color
		(
			definition.colour.get("R"),
			definition.colour.get("G"),
			definition.colour.get("B"),
			definition.colour.get("A")
		);
		
		animated = definition.animated;
		animationFrames = definition.animationFrames;
		
		collideable = definition.collideable;
		
		texture = Resources.setColour(Resources.load(texturePath), this.colour);
	}
}
