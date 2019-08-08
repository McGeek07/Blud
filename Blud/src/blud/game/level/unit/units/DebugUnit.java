package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class DebugUnit extends Unit {
	
	public DebugUnit() {
		super();
		sprites.add(Sprites.get("DebugUnit"));
		this.lightLevel =  1;
		this.lightRange =  4;
	}
}
