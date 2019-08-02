package blud.game.entity;

import blud.game.sprite.Sprite;
import blud.geom.Vector;

public abstract class Entity extends blud.game.level.Object {
	protected float
		transparency;
	
	public Entity(Sprite... sprites) {
		super(sprites);
	}
	
	public Entity(Vector local, Sprite... sprites) {
		super(local, sprites);
	}
	
	public Entity(float i, float j, Sprite... sprites) {
		super(i, j, sprites);
	}
		
}
