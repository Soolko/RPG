package rpg.rendering.ui;

import rpg.Component;
import rpg.RPG;
import rpg.maths.Vector2;

import java.awt.*;

public final class DeathScreen extends Component
{
	public DeathScreen() { super(true); }
	
	@Override
	public void renderOverlay(Graphics2D g2d)
	{
		final String deadString = "You Died";
		
		g2d.setColor(Color.darkGray);
		g2d.fillRect(0, 0, RPG.frame.getWidth(), RPG.frame.getHeight());
		
		g2d.setColor(Color.white);
		g2d.setFont(new Font("Noto Sans", Font.BOLD, 100));
		FontMetrics fm = g2d.getFontMetrics();
		int x = RPG.frame.getContentPane().getWidth() / 2 - fm.stringWidth(deadString) / 2;
		int y = RPG.frame.getContentPane().getHeight() / 2 - fm.getHeight() / 2;
		g2d.drawString(deadString, x, y);
	}
	
	@Override public void fixedUpdate() { }
	@Override public void update(double delta) { }
	@Override public void render(Graphics2D g2d, Vector2 position) { }
	@Override public void randomTick() { }
}
