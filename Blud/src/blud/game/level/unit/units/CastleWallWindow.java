package blud.game.level.unit.units;


import blud.game.level.unit.Wall;
import blud.game.sprite.sprites.Sprites;

public class CastleWallWindow extends Wall {
	
	public CastleWallWindow() {
		super();
		sprites.add(Sprites.get("CastleWallWindow"));
		this.lightLevel = 1f;
		this.lightRange =  3;
	}
	
}
