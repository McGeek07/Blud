package blud.game.tile.tiles;

import blud.game.sprite.Sprite;
import blud.game.tile.Tile;

public class Debug extends Tile {
	
	public Debug() {
		super(Sprite.get("tile"));
	}
	
	public Debug(float i, float j) {
		super(i, j, Sprite.get("tile"));
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
	}
}
