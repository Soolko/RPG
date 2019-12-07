package rpg.world.biomes;

import static rpg.Resources.load;

import rpg.world.tiles.Tile;

public class OceanBiome extends Biome
{
	// Assets
	public static final Tile OceanTile = new Tile(load("textures/ocean/water.png"));
	
	// Placement generation
	public OceanBiome() { super(BlendMode.CONSTANT, 0); }
	
	@Override
	public Tile tileAt(double val) { return OceanTile; }
}
