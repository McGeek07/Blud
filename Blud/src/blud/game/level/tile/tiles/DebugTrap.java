package blud.game.level.tile.tiles;

import blud.game.level.tile.Trap;
import blud.game.sprite.sprites.Sprites;

public class DebugTrap extends Trap {
	
	public DebugTrap() {
		super();
		sprites.add(Sprites.get("DebugTrap"));
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
	}
	
}
