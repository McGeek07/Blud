package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class CastleTorch extends Unit {
	
	public CastleTorch() {
		super(Sprites.get("CastleTorch"));
		this.playerVisionLevel = 1f;
		this.playerVisionRange = 4f;
		this.entityVisionLevel = 1f;
		this.entityVisionRange = 4f;
		this.sprites.loop(0, 3f);
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
		
	}
	
}
