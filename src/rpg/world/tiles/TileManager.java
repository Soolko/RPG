package rpg.world.tiles;

import java.util.HashMap;

import rpg.Resources;

public final class TileManager
{
	public HashMap<String, Tile> definitions = new HashMap<String, Tile>();
	
	public TileDefinition load(String path)
	{
		TileDefinition def = Resources.loadDefinition(path);
		
		Tile tile = new Tile(def);
		definitions.put(tile.key, tile);
		return tile;
	}
}
