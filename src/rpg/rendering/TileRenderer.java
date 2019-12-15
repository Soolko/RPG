package rpg.rendering;

import java.awt.Graphics2D;

import rpg.RPG;
import rpg.Component;
import rpg.maths.Vector2;
import rpg.world.tiles.Tile;

public class TileRenderer extends Component
{
	/**
	 * The texture to draw.
	 */
	public final Tile tile;
	public TileRenderer(Tile tile) { this.tile = tile; }
	
	@Override
	public void render(Graphics2D g2d, Vector2 position)
	{
		g2d.drawImage
		(
			tile.texture,
			(int) position.x(),
			(int) position.y(),
			(int) (position.x() + RPG.BaseScale),
			(int) (position.y() + RPG.BaseScale),
			0, 0, tile.texture.getWidth(), tile.texture.getHeight(),
			null
		);
	}
	
	@Override public void fixedUpdate() { }
	@Override public void update(double delta) { }
	@Override public void renderOverlay(Graphics2D g2d) { }
	@Override public void randomTick() { }
}
