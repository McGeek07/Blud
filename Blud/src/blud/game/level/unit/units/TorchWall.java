package blud.game.level.unit.units;

import blud.core.Renderable.RenderContext;
import blud.core.Updateable.UpdateContext;
import blud.game.level.unit.Wall;
import blud.game.sprite.sprites.Sprites;

public class TorchWall extends Wall {
	
	public TorchWall() {
		super();
		sprites.add(Sprites.get("TorchWall"));
		this.entityVisionLevel = 1f;
		this.entityVisionRange = 4f;
		this.sprites.loop(0, 2.5f);
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
		
	}
	
}
