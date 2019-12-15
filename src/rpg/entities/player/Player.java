package rpg.entities.player;

import rpg.entities.Entity;

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
}
