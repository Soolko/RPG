package rpg.world.tiles;

import java.awt.Image;

import rpg.rendering.SpriteRenderer;

public class Tile extends SpriteRenderer
{
	public Tile(Image texture) { super(texture); }
	
	public boolean collideable = false;
	public boolean water = false;
}
