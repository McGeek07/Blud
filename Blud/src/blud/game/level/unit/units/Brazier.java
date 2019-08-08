package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class Brazier extends Unit {
	
	public Brazier() {
		super();
		sprites.add(Sprites.get("Brazier"));
		lightLevel = 1;
		lightRange = 4;
		sprites.loop(0, 3f);
		
		this.defendFrames = 12;
		this.maxHP = 1;
		this.curHP = 1;
	}
	
}
