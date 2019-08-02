package blud.game.wall.walls;

import blud.game.sprite.Sprite;
import blud.game.wall.Wall;

public class Debug extends Wall {
	
	public Debug() {
		super(Sprite.get("wall"));
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
	}
}
