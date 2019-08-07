package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class Brazier extends Unit {
	
	public Brazier() {
		super();
		sprites.add(Sprites.get("Brazier"));
		entityVisionLevel = 1f;
		entityVisionRange = 4f;
		sprites.loop(0, 3f);
		
		this.defendFrames = 12;
		this.maxHP = 1;
		this.curHP = 1;
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
		
	}
	
}
