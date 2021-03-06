package rpg.world;

import static java.lang.Math.floor;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.NotNull;
import rpg.RPG;
import rpg.Component;
import rpg.Resources;
import rpg.entities.Entity;
import rpg.entities.enemies.Enemy;
import rpg.maths.Vector2;
import rpg.rendering.TileRenderer;
import rpg.world.biomes.Biome;
import rpg.world.biomes.Biome.BlendMode;
import rpg.world.biomes.GenericBiome;
import rpg.world.noise.SimplexNoise;
import rpg.world.tiles.Tile;

public class ProceduralWorld extends Component
{
	// Default biome creation
	public static final Generator DefaultGenerator = new SimplexNoise();
	public static ProceduralWorld DefaultWorld;
	static
	{
		// Ocean
		Tile oceanTile = new Tile(Resources.loadTile("textures/ocean/water.yml"));
		Biome ocean = new GenericBiome(oceanTile, BlendMode.CONSTANT, 0.0);
		
		// Beach
		Tile beachTile = new Tile(Resources.loadTile("textures/beach/sand.yml"));
		Biome beach = new GenericBiome(beachTile, BlendMode.SIMPLEX, 0.45);
		
		// Plains
		Tile plainsTile = new Tile(Resources.loadTile("textures/plains/grass.yml"));
		GenericBiome plains = new GenericBiome(plainsTile, BlendMode.SIMPLEX, 0.6);
		
		// Construct array
		final Biome[] DefaultBiomes = new Biome[] { ocean, beach, plains };
		
		// Construct default world
		DefaultWorld = new ProceduralWorld(DefaultGenerator, DefaultBiomes);
	}
	
	// Values
	public final Generator generator;
	public Vector2 scale = new Vector2(0.05, 0.07);
	public long seed = 0L;
	
	public final Biome[] biomes;
	
	// Tilemap
	public static class PositionedTile
	{
		public int x, y;
		public TileRenderer renderer;
	}
	
	public List<PositionedTile> tiles = new ArrayList<PositionedTile>();
	public Rectangle generatedBounds = null;
	
	public ProceduralWorld(Generator generator, Biome[] biomes)
	{
		super(true);
		this.generator = generator;
		this.biomes = biomes;
	}
	
	@Override
	public void render(Graphics2D g2d, Vector2 position)
	{
		Rectangle currentBounds = getBounds(position);
		if(!currentBounds.equals(generatedBounds)) generate(currentBounds);
		
		for(PositionedTile tile : tiles)
		{
			int x = (int) (position.x + (tile.x * RPG.BaseScale));
			int y = (int) (position.y + (tile.y * RPG.BaseScale));
			tile.renderer.render(g2d, new Vector2(x, y));
		}
	}
	
	/**
	 * Not the most efficient way to flush then rebuild,
	 * but time limits require it.
	 */
	public void generate(@NotNull Rectangle currentBounds)
	{
		// Flush tiles
		tiles.clear();
		
		// Rebuild
		for(int x = currentBounds.x; x < currentBounds.width; x++)
		for(int y = currentBounds.y; y < currentBounds.height; y++)
		{
			PositionedTile tile = new PositionedTile();
			tile.x = x;
			tile.y = y;
			tile.renderer = generateAt(x, y);
			tiles.add(tile);
		}
		
		generatedBounds = currentBounds;
	}
	
	public TileRenderer generateAt(double x, double y)
	{
		TileRenderer tile = null;
		Random r = new Random(seed);
		
		// Get base tile
		double worldMap = generator.sample(x * scale.x, y * scale.y);
		for(Biome biome : biomes)
		{
			switch(biome.blendMode)
			{
				case CONSTANT:
					tile = biome.tileAt(worldMap);
					break;
				case SIMPLEX:
					if(worldMap > biome.noiseThreashold) tile = biome.tileAt(worldMap);
					break;
			}
		}
		
		return tile;
	}
	
	public Rectangle getBounds(@NotNull Vector2 topLeft)
	{
		int x = (int) floor(topLeft.x() / RPG.BaseScale) + 1;
		int y = (int) floor(topLeft.y() / RPG.BaseScale) + 1;
		int x2 = -x + (int) (RPG.frame.getWidth() / RPG.BaseScale) + 2;
		int y2 = -y + (int) (RPG.frame.getHeight() / RPG.BaseScale) + 2;
		
		return new Rectangle(-x, -y, x2, y2);
	}
	
	@Override public void fixedUpdate() { }
	@Override public void update(double delta) { }
	@Override public void renderOverlay(Graphics2D g2d) { }
	@Override public void randomTick() { }
}
