package blud.game.tile;

import blud.game.sprite.Sprite;
import blud.geom.Vector;

public abstract class Tile extends blud.game.level.Object {	
	
	public Tile(Sprite... sprites) {
		super(sprites);
	}
	
	public Tile(Vector local, Sprite... sprites) {
		super(local, sprites);
	}
	
	public Tile(float i, float j, Sprite... sprites) {
		super(i, j, sprites);
	}
	
	public String toString() {
		return ""+this.local.x()+","+this.local.y()+",Tile";
	}
}
