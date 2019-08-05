package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class Brazier extends Unit {
	
	public Brazier() {
		super(Sprites.get("Brazier"));
		this.playerVisionLevel = 1f;
		this.playerVisionRange = 6f;
		this.entityVisionLevel = 1f;
		this.entityVisionRange = 8f;
		this.sprites.loop(0, 3f);
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
		
	}
	
}
