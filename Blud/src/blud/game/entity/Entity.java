package blud.game.entity;

import blud.game.sprite.Sprite;
import blud.geom.Vector;
import blud.geom.Vector2f;

public abstract class Entity extends blud.game.level.Object {
	
	//vision attributes
	public float
		playerVisionLevel,
		playerVisionRange,
		entityVisionLevel,
		entityVisionRange;
	public boolean
		blocksPlayerVision,
		blocksEntityVision;
	public int
		playerVisionDirection = -1,
		entityVisionDirection = -1;
	
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
	
	public abstract String getName();
	
	public String toString() {
		return ""+this.local.x()+","+this.local.y()+","+this.getName();
	}
}
