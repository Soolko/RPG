package rpg.world.noise;

import rpg.world.Generator;

public abstract class NoiseAlgorithm implements Generator
{
	// Seeds
	public static final long DefaultSeed = 0L;
	public long seed;
	
	// Constructors
	public NoiseAlgorithm() { this(DefaultSeed); }
	public NoiseAlgorithm(long seed) { this.seed = seed; }
}
