package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class GoldBrazier extends Unit {
	
	public GoldBrazier() {
		super();
		sprites.add(Sprites.get("GoldBrazier"));
		lightLevel = 1;
		lightRange = 4;
		sprites.loop(0, 3f);
		
		this.defendFrames = 12;
		this.maxHP = 1;
		this.curHP = 1;
	}
	
}
