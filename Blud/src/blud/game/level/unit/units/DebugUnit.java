package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class DebugUnit extends Unit {
	
	public DebugUnit() {
		super(Sprites.get("DebugUnit"));
//		this.playerVisionLevel = 1f;
//		this.playerVisionRange = 6f;
		this.entityVisionLevel = 1f;
		this.entityVisionRange = 4f;
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
	}
	
}
