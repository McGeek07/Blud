package blud.game.entity;

import blud.game.sprite.Sprite;
import blud.geom.Vector;
import blud.geom.Vector2f;

public abstract class Entity extends blud.game.level.Object {
	
	//lighting attributes
	public float
		player_vision_value,
		player_vision_dropoff,
		entity_vision_value,
		entity_vision_dropoff;
	public boolean
		player_vision_transparency = true,
		entity_vision_transparency = true;
	public Vector2f
		player_vision_direction,
		entity_vision_direction;
	
	protected int currentHealth;
	protected int maxHealth;
	protected int visibility;
	protected boolean needLight;
	protected boolean visibleThroughWalls;
	protected Vector2f orientation;
	protected int strength;
	
	public Entity(Sprite... sprites) {
		super(sprites);
	}
	
	public Entity(Vector local, Sprite... sprites) {
		super(local, sprites);
	}
	
	public Entity(float i, float j, Sprite... sprites) {
		super(i, j, sprites);
	}		
	
	public void move(Entity character) {
		if(grid.entity instanceof blud.game.wall.Wall) {
			
		}else if(grid.entity != null) {
			fight(character, grid.entity);
		}else {
			character.setLocal(Vector.add(local, orientation));
		}
	}
	
	public void fight(Entity attacker, Entity defender ) {
		Vector2f battle = Vector2f.mul(attacker.orientation, defender.orientation);
		if(battle.X() < 0 || battle.Y() < 0) {
			takeDamage(defender, attacker.strength);
			takeDamage(attacker, defender.strength);
		}else {
			takeDamage(defender, attacker.strength);
		}
	}
	
	public void takeDamage(Entity character, int damageAmount) {
		character.currentHealth -= damageAmount;
	}
	
}
