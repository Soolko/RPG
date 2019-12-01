package rpg.maths;

import java.util.Random;

/*
 * Dirty range class that could really be done better.
 * Made this while completely out of it tbh,
 * and could be done a lot better if written in C#.
 * 
 * Wish Java would take primitives as a type argument.
 * 
 * This is mostly implemented just for the fractal maps,
 * as it allows for variance on the maps for extra randomness.
 * 
 * TODO Make this less nasty.
 */
public abstract class Range<T>
{
	private final Random rand;
	
	public Range() { rand = new Random(); }
	public Range(long seed) { rand = new Random(seed); }
	
	public abstract void validate();
	public abstract T difference();
	public abstract T random();
	
	public final class DRange extends Range<Double>
	{
		public double min, max;
		
		@Override public void validate() { if(min > max) min = max; }
		@Override public Double difference() { return max - min; }
		
		@Override
		public Double random()
		{
			double scaled = rand.nextDouble() * difference();
			double offset = scaled + min;
			return offset;
		}
	}
	
	public final class FRange extends Range<Float>
	{
		public float min, max;
		
		@Override public void validate() { if(min > max) min = max; }
		@Override public Float difference() { return max - min; }
		
		@Override
		public Float random()
		{
			float scaled = rand.nextFloat() * difference();
			float offset = scaled + min;
			return offset;
		}
	}
	
	public final class IRange extends Range<Integer>
	{
		public int min, max;
		
		@Override public void validate() { if(min > max) min = max; }
		@Override public Integer difference() { return max - min; }
		
		@Override
		public Integer random()
		{
			int range = rand.nextInt(difference());
			int offset = range + min;
			return offset;
		}
	}
	
	public final class LRange extends Range<Long>
	{
		public long min, max;
		
		@Override public void validate() { if(min > max) min = max; }
		@Override public Long difference() { return max - min; }
		
		@Override
		public Long random()
		{
			long range = rand.nextLong() % difference();
			long offset = range + min;
			return offset;
		}
	}
}
