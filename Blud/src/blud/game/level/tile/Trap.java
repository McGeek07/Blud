package blud.game.level.tile;

import blud.game.sprite.Sprite;
import blud.geom.Vector;

public abstract class Trap extends Tile {

	public Trap(Sprite... sprites) {
		super(sprites);
	}
	
	public Trap(Vector local, Sprite... sprites) {
		super(local, sprites);
	}
	
	public Trap(float i, float j, Sprite... sprites) {
		super(i, j, sprites);
	}

}
