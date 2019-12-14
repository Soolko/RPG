package rpg.world.biomes;

import rpg.rendering.TileRenderer;
import rpg.world.noise.NoiseAlgorithm;
import rpg.world.noise.SimplexNoise;
import rpg.world.tiles.Tile;

public abstract class Biome
{
	public final BlendMode blendMode;
	public double noiseThreashold = 0.5;
	
	public Biome(double noiseThreashold) { this(BlendMode.SIMPLEX, noiseThreashold); }
	public Biome(BlendMode blendMode, double noiseThreashold)
	{
		this.blendMode = blendMode;
		this.noiseThreashold = noiseThreashold;
	}
	
	public enum BlendMode
	{
		CONSTANT(null),
		SIMPLEX(new SimplexNoise());
		
		final NoiseAlgorithm algorithm;
		BlendMode(NoiseAlgorithm algorithm) { this.algorithm = algorithm; }
	}
	
	public abstract TileRenderer tileAt(double val);
}
