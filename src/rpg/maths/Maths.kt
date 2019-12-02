package rpg.maths

final object Maths
{
	fun <Type : Comparable<Type>> clamp(input : Type, min : Type, max : Type) : Type
	{
		if(input < min) return min
		if(input > max) return max
		return input
	}
}