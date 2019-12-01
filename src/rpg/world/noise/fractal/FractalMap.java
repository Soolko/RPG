package rpg.world.noise.fractal;

import rpg.world.noise.NoiseAlgorithm;

public class FractalMap
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
}
