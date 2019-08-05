package blud.game.level.tile;

import blud.geom.Vector;

public abstract class Tile extends blud.game.level.entity.Entity {	
	
	public Tile() {
		super();
	}
	
	public Tile(Vector local) {
		super(local);
	}
	
	public Tile(float i, float j) {
		super(i, j);
	}
	
}
