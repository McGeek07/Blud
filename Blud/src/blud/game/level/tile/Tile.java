package blud.game.level.tile;

import blud.game.sprite.Sprite;
import blud.geom.Vector;

public abstract class Tile extends blud.game.level.entity.Entity {	
	
	public Tile(Sprite... sprites) {
		super(sprites);
	}
	
	public Tile(Vector local, Sprite... sprites) {
		super(local, sprites);
	}
	
	public Tile(float i, float j, Sprite... sprites) {
		super(i, j, sprites);
	}
	
}
