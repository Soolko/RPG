package rpg.world.biomes;

import rpg.world.noise.NoiseAlgorithm;
import rpg.world.noise.SimplexNoise;
import rpg.world.tiles.Tile;

public abstract class Biome
{
	public final BlendMode blendMode;
	public double noiseThreashold = 0.75;
	
	public Biome(BlendMode blendMode, double noiseThreashold)
	{
		this.blendMode = blendMode;
		this.noiseThreashold = noiseThreashold;
	}
	
	public static enum BlendMode
	{
		CONSTANT(null),
		SIMPLEX(new SimplexNoise());
		
		final NoiseAlgorithm algorithm;
		BlendMode(NoiseAlgorithm algorithm) { this.algorithm = algorithm; }
	}
	
	public abstract Tile tileAt(double val);
}
