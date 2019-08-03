package blud.game.level.unit;

import blud.game.sprite.Sprite;
import blud.geom.Vector;
import blud.geom.Vector2f;

public abstract class Unit extends blud.game.level.entity.Entity {
	
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
	
	public Unit(Sprite... sprites) {
		super(sprites);
	}
	
	public Unit(Vector local, Sprite... sprites) {
		super(local, sprites);
	}
	
	public Unit(float i, float j, Sprite... sprites) {
		super(i, j, sprites);
	}		
	
	public void move(Unit character) {
		if(grid.unit instanceof Wall) {
			
		}else if(grid.unit != null) {
			fight(character, grid.unit);
		}else {
			character.setLocal(Vector.add(local, orientation));
		}
	}
	
	public void fight(Unit attacker, Unit defender ) {
		Vector2f battle = Vector2f.mul(attacker.orientation, defender.orientation);
		if(battle.X() < 0 || battle.Y() < 0) {
			takeDamage(defender, attacker.strength);
			takeDamage(attacker, defender.strength);
		}else {
			takeDamage(defender, attacker.strength);
		}
	}
	
	public void takeDamage(Unit character, int damageAmount) {
		character.currentHealth -= damageAmount;
	}
}
