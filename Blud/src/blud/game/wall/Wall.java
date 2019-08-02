package blud.game.wall;

import blud.game.entity.Entity;
import blud.game.sprite.Sprite;
import blud.geom.Vector;

public abstract class Wall extends Entity {
	
	public Wall(Sprite... sprites) {
		super(sprites);
	}
	
	public Wall(Vector local, Sprite... sprites) {
		super(local, sprites);
	}
	
	public Wall(float i, float j, Sprite... sprites) {
		super(i, j, sprites);
	}
}
