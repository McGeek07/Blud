package blud.game.level.unit.units;

import blud.game.level.unit.Wall;
import blud.game.sprite.sprites.Sprites;

public class CastleWall extends Wall{
	
	public CastleWall() {
		super();
		sprites.add(Sprites.get("CastleWall"));
	}
}
