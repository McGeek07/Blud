package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class CastleTorch extends Unit {
	
	public CastleTorch() {
		super();
		sprites.add(Sprites.get("CastleTorch"));
		this.lightLevel = 1;
		this.lightRange = 6;
		this.sprites.loop(0, 3f);
		
		this.defendFrames = 12;
	}	
}
