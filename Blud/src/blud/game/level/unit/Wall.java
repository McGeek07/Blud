package blud.game.level.unit;

import blud.game.sprite.Sprite;
import blud.geom.Vector;

public abstract class Wall extends Unit {
	
	public Wall(Sprite... sprites) {
		super(sprites);
		this.blocksPlayerVision = true;
		this.blocksEntityVision = true;
	}
	
	public Wall(Vector local, Sprite... sprites) {
		super(local, sprites);
		this.blocksPlayerVision = true;
		this.blocksEntityVision = true;
	}
	
	public Wall(float i, float j, Sprite... sprites) {
		super(i, j, sprites);
		this.blocksPlayerVision = true;
		this.blocksEntityVision = true;
	}
	
}
