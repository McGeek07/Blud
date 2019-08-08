package blud.game.level.unit.units;

import blud.game.level.unit.Wall;
import blud.game.sprite.sprites.Sprites;

public class SewerLanternWall extends Wall {
	
	public SewerLanternWall() {
		super();
		sprites.add(Sprites.get("SewerLanternWall"));
		this.lightLevel = 1f;
		this.lightRange = 3;
		sprites.loop(3f);
	}
}
