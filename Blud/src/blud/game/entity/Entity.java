package blud.game.entity;

import blud.game.sprite.Sprite;
import blud.geom.Vector;

public abstract class Entity extends blud.game.level.Object {
	public float
		player_vision_transparency,
		player_vision_value,
		player_vision_step,
		entity_vision_transparency,
		entity_vision_value,
		entity_vision_step;		
	
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
