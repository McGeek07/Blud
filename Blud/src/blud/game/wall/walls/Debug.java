package blud.game.wall.walls;

import blud.game.sprite.Sprite;
import blud.game.wall.Wall;

public class Debug extends Wall {
	
	public Debug() {
		super(Sprite.get("wall"));
	}
	
	public Debug(float i, float j) {
		super(i, j, Sprite.get("wall"));
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Wall";
	}
}
