package rpg.world.tiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.yaml.snakeyaml.Yaml;

public final class TileManager
{
	public HashMap<String, Tile> definitions = new HashMap<String, Tile>();
	
	public TileDefinition load(File definition) throws FileNotFoundException
	{
		final Yaml yaml = new Yaml();
		TileDefinition tileDefinition = yaml.load(new FileInputStream(definition));
		Tile tile = new Tile(tileDefinition);
		
		definitions.put(tile.key, tile);
		return tile;
	}
}
