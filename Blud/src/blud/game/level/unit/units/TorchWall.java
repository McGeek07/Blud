package blud.game.level.unit.units;

import blud.game.level.unit.Wall;
import blud.game.sprite.sprites.Sprites;

public class TorchWall extends Wall {
	
	public TorchWall() {
		super();
		sprites.add(Sprites.get("TorchWall"));
		this.lightLevel = 1;
		this.lightRange = 4;
		this.sprites.loop(0, 2.5f);
	}
	
}
