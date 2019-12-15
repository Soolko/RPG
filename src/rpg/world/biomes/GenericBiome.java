package rpg.world.biomes;

import rpg.entities.Entity;
import rpg.rendering.TileRenderer;
import rpg.world.tiles.Tile;

public class GenericBiome extends Biome
{
	public TileRenderer tile;
	
	public GenericBiome(Tile tile, BlendMode blendMode, double noiseThreashold)
	{
		super(blendMode, noiseThreashold);
		this.tile = new TileRenderer(tile);
	}
	
	@Override public TileRenderer tileAt(double val) { return tile; }
}
