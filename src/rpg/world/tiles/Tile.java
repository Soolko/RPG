package rpg.world.tiles;

import java.awt.Image;

public class Tile
{
	public Image texture;
	public boolean collideable;
	
	public Tile(Image texture) { this(texture, false); }
	public Tile(Image texture, boolean collideable)
	{
		this.texture = texture;
		this.collideable = collideable;
	}
}
