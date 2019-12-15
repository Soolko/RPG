package rpg.maths;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
	
	public static double round(double value, int places)
	{
		if (places < 0) throw new IllegalArgumentException();
		
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
