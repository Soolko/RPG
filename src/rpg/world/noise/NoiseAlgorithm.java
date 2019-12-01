package rpg.world.noise;

public abstract class NoiseAlgorithm
{
	// Seeds
	public static final long DefaultSeed = 0L;
	public long seed;
	
	// Constructors
	public NoiseAlgorithm() { this(DefaultSeed); }
	public NoiseAlgorithm(long seed) { this.seed = seed; }
	
	// Sample methods
	public abstract double sample(double x, double y);
	public abstract double sample(double x, double y, double z);
	public abstract double sample(double x, double y, double z, double w);
}
