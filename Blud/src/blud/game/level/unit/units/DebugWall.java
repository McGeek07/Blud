package blud.game.level.unit.units;

import blud.game.level.unit.Wall;
import blud.game.sprite.sprites.Sprites;

public class DebugWall extends Wall {
	
	public DebugWall() {
		super();
		sprites.add(Sprites.get("DebugWall"));
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
	}

}
