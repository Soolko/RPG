package rpg.entities.weapons;

import org.jetbrains.annotations.NotNull;
import rpg.Component;
import rpg.RPG;
import rpg.entities.Entity;
import rpg.maths.Vector2;

public class Projectile extends Entity
{
	public final Vector2 direction;
	public final double lifespan;
	protected double lifetime = 0.0;
	
	public double damage;
	
	public Projectile(String definition, Vector2 direction, double lifespan)
	{
		super(definition);
		this.direction = direction;
		this.lifespan = lifespan;
	}
	
	public Projectile(String definition, Vector2 direction, double lifespan, double damage)
	{
		this(definition, direction, lifespan);
		this.damage = damage;
	}
	
	@Override
	public void fixedUpdate()
	{
		position.x += direction.x() * RPG.FixedDelta;
		position.y += direction.y() * RPG.FixedDelta;
	}
	
	@Override
	public void update(double delta)
	{
		lifetime += delta;
		if(lifetime >= lifespan) destroy();
	}
	
	@Override
	public void onCollide(@NotNull Entity other)
	{
		other.setHealth(other.getHealth() - damage);
		destroy();
	}
	
	private void destroy()
	{
		enabled = false;
		Component.components.remove(this);
	}
}
