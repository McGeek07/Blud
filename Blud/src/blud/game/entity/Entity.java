package blud.game.entity;

import blud.game.sprite.Sprite;
import blud.geom.Vector;
import blud.geom.Vector2f;

public abstract class Entity extends blud.game.level.Object {
	
	int currentHealth;
	int maxHealth;
	int visibility;
	boolean needLight;
	boolean visibleThroughWalls;
	Vector2f orientation;
	
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
		setLocal(Vector.add(local, blud.game.Game.EAST));
	}
	
	public void fight(Entity attacker, Entity defender ) {
		
	}
	
	public void takeDamage(Entity character, int damageAmount) {
		character.currentHealth -= damageAmount;
	}
	
}
