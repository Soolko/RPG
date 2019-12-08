package rpg.maths;

public final class Maths
{
	public static double invlerp(double in, double srcMin, double srcMax)
	{
		in -= srcMin;
		in /= srcMax - srcMin;
		return in;
	}
}
