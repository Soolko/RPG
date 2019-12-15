package rpg.entities.player;

import rpg.Component;
import rpg.entities.Entity;
import rpg.rendering.ui.DeathScreen;

import java.io.Serializable;

public class Player extends Entity
{
	public final PlayerMovement movement = new PlayerMovement(this);
	
	public Player()
	{
		super("entities/player.yml");
	}
	
	public class Data implements Serializable
	{
		public Data() { }
		public double xp = 0.0;
	}
	
	@Override
	public void onCollide(Entity other)
	{
	
	}
	
	@Override
	protected void onKilled(double amount)
	{
		Entity.entities.clear();
		Component.components.clear();
		
		Component.components.add(new DeathScreen());
	}
}
