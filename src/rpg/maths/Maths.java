package rpg.maths;

public final class Maths
{
	public static double invlerp(double in, double srcMin, double srcMax)
	{
		in -= srcMin;
		in /= srcMax - srcMin;
		return in;
	}
	
	public static double clamp(double in, double min, double max)
	{
		double out = in;
		if(out < min) out = min;
		if(out > max) out = max;
		return out;
	}
}
