package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class CastleTorch extends Unit {
	
	public CastleTorch() {
		super();
		sprites.add(Sprites.get("CastleTorch"));
		this.entityVisionLevel = 1f;
		this.entityVisionRange = 6f;
		this.sprites.loop(0, 3f);
		
		this.defendFrames = 12;
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
		
	}
	
}
