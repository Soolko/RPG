package rpg.world.biomes;

import rpg.world.tiles.Tile;

public class GenericBiome extends Biome
{
	public Tile tile;
	
	public GenericBiome(Tile tile, BlendMode blendMode, double noiseThreashold)
	{
		super(blendMode, noiseThreashold);
		this.tile = tile;
	}
	
	@Override public Tile tileAt(double val) { return tile; }
}
