package blud.game.level.unit.units;


import blud.game.level.unit.Wall;
import blud.game.sprite.sprites.Sprites;

public class CastleWallWindow extends Wall {
	
	public CastleWallWindow() {
		super();
		sprites.add(Sprites.get("CastleWallWindow"));
		this.entityVisionLevel = .6f;
		this.entityVisionRange = 3f;
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
		
	}
	
}
