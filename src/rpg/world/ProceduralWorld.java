package rpg.world;

import static java.lang.Math.floor;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import rpg.RPG;
import rpg.architecture.Component;
import rpg.maths.Vector2;
import rpg.rendering.TileRenderer;
import rpg.world.biomes.Biome;
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
		
		
		// Construct array
		final Biome[] DefaultBiomes = new Biome[] { };
		
		// Construct default world
		DefaultWorld = new ProceduralWorld(DefaultGenerator, DefaultBiomes);
	}
	
	// Values
	public final Generator generator;
	public Vector2 scale = new Vector2(0.05, 0.07);
	
	public final Biome[] biomes;
	
	// Tilemap
	public class PositionedTile
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
		Vector2 bottomRight = new Vector2
		(
			position.x() + RPG.frame.getWidth(),
			position.y() + RPG.frame.getHeight()
		);
		
		Rectangle currentBounds = getBounds(position, bottomRight);
		if(generatedBounds != currentBounds) generate(currentBounds);
		
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
	public void generate(Rectangle currentBounds)
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
			tile.renderer = new TileRenderer(generateAt(x, y));
			tiles.add(tile);
		}
		
		generatedBounds = currentBounds;
	}
	
	public Tile generateAt(double x, double y)
	{
		Tile tile = null;
		
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
	
	public Rectangle getBounds(Vector2 topLeft, Vector2 bottomRight)
	{
		int x = (int) floor(topLeft.x() / RPG.BaseScale) + 1;
		int y = (int) floor(topLeft.y() / RPG.BaseScale) + 1;
		int x2 = -x + (int) (RPG.frame.getWidth() / RPG.BaseScale) + 2;
		int y2 = -y + (int) (RPG.frame.getHeight() / RPG.BaseScale) + 2;
		
		return new Rectangle(-x, -y, x2, y2);
	}
	
	@Override public void fixedUpdate() { }
	@Override public void update() { }
	@Override public void renderOverlay(Graphics2D g2d) { }
}
