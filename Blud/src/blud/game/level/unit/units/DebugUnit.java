package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class DebugUnit extends Unit {
	
	public DebugUnit() {
		super();
		sprites.add(Sprites.get("DebugUnit"));
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
