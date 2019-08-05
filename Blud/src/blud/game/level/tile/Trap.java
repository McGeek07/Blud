package blud.game.level.tile;

import blud.geom.Vector;

public abstract class Trap extends Tile {

	public Trap() {
		super();
	}
	
	public Trap(Vector local) {
		super(local);
	}
	
	public Trap(float i, float j) {
		super(i, j);
	}

}
