package rpg.world.noise.fractal;

import rpg.world.Generator;
import rpg.world.noise.NoiseAlgorithm;

/**
 * Incomplete
 */
public class FractalMap implements Generator
{
	public final long seed;
	public final NoiseAlgorithm algorithm;
	
	public FractalMap(NoiseAlgorithm algorithm) { this(algorithm, NoiseAlgorithm.DefaultSeed); }
	public FractalMap(NoiseAlgorithm algorithm, long seed)
	{
		this.algorithm = algorithm;
		this.seed = seed;
		
		algorithm.seed = seed;
	}
	
	@Override
	public double sample(double x, double y)
	{
		return 0;
	}
	
	@Override
	public double sample(double x, double y, double z)
	{
		return 0;
	}
	
	@Override
	public double sample(double x, double y, double z, double w)
	{
		return 0;
	}
}
