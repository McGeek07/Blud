package blud.game.level.unit;

import blud.game.sprite.Sprite;
import blud.geom.Vector;

public abstract class Wall extends Unit {
	
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
