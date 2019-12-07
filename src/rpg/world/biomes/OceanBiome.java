package rpg.world.biomes;

import static java.lang.Math.*;

import static rpg.Resources.*;
import rpg.world.tiles.Tile;

public class OceanBiome extends Biome
{
	// Assets
	public static final Tile OceanTile = new Tile(load("textures/ocean/water.png"));
	public static final Tile OceanRockTile = new Tile(load("textures/ocean/oceanrock.png"));
	
	// Rock generation
	public double threashold = 0.8;
	public double power = 5.0;
	
	// Placement generation
	
	public OceanBiome() { super(BlendMode.CONSTANT, 0); }
	
	@Override
	public Tile tileAt(double val)
	{
		Tile tile = OceanTile;
		val = pow(val, power);
		
		if(val > threashold) tile = OceanRockTile;
		
		return tile;
	}
}
