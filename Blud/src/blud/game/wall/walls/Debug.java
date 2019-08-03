package blud.game.wall.walls;

import blud.game.Game;
import blud.game.sprite.Sprite;
import blud.game.wall.Wall;

public class Debug extends Wall {
	
	public Debug() {
		this(0, 0);
	}
	
	public Debug(float i, float j) {
		super(i, j, Sprite.get("wall"));
		this.playerVisionLevel = 1f;
		this.playerVisionRange = 6f;
		this.entityVisionLevel = 1f;
		this.entityVisionRange = 8f;
		this.entityVisionDirection = Game.NORTH;
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
