package blud.game.level.tile.tiles;

import blud.game.level.tile.Tile;
import blud.game.sprite.sprites.Sprites;

public class DebugTile extends Tile {
	
	public DebugTile() {
		super(Sprites.get("DebugTile"));
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
	}
}
