package rpg.entities.enemies;

import rpg.entities.Entity;

public class Enemy extends Entity
{
	public double detectionRange = 4.0;
	
	public Enemy(String definitionPath) { super(definitionPath); }
}
