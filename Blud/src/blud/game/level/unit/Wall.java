package blud.game.level.unit;

import blud.geom.Vector;

public abstract class Wall extends Unit {
	
	public Wall() {
		this(0, 0);
	}
	
	public Wall(Vector local) {
		this(local.X(), local.Y());
	}
	
	public Wall(float i, float j) {
		super(i, j);
		this.blocksLight = true;
		this.blocksPlayerVision = true;
		this.blocksEntityVision = true;
	}
	
}
