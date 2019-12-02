package rpg.world;

import java.awt.Graphics2D;

import rpg.world.biomes.Biome;

public class ProceduralWorld
{
	public final Generator generator;
	public Biome[] biomes;
	
	public ProceduralWorld(Generator generator)
	{
		this.generator = generator;
	}
	
	public void render(Graphics2D g2d)
	{
		
	}
}
