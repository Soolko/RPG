package rpg.entities.enemies;

import rpg.entities.Entity;

public class Enemy extends Entity
{
	public double detectionRange = 512;
	
	public Enemy(String definitionPath) { super(definitionPath); }
	
	@Override
	public void onCollide(Entity other)
	{
		
	}
}
