package rpg.world.biomes;

import static rpg.Resources.GrassBase;
import static rpg.Resources.setColour;

import java.awt.Color;
import java.awt.image.BufferedImage;

import rpg.world.tiles.Tile;

public class HeavenBiome extends Biome
{
	// Assets
	public static final Color HeavenColour = new Color(33, 255, 106, 255);
	public static final BufferedImage HeavenGrassTexture = setColour(GrassBase, HeavenColour);
	
	// Tiles
	public static final Tile HeavenGrass = new Tile(HeavenGrassTexture);
	
	public HeavenBiome() { super(BlendMode.SIMPLEX, 0.75); }
	
	@Override
	public Tile tileAt(double val)
	{
		// Just return the base tile for now.
		return HeavenGrass;
	}
}
