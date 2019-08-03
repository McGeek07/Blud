package blud.game.level.unit.units;

import blud.game.level.unit.Wall;
import blud.game.sprite.sprites.Sprites;

public class DebugWall extends Wall {
	
	public DebugWall() {
		super(Sprites.get("DebugWall"));
		this.blocksPlayerVision = true;
		this.blocksEntityVision = true;
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
	}

}
