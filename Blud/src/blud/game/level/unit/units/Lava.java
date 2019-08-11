package blud.game.level.unit.units;

import blud.game.level.unit.Wall;
import blud.game.sprite.sprites.Sprites;

public class Lava extends Wall {
	
	public Lava() {
		super();
		sprites.add(Sprites.get("Lava"));
		this.lightLevel = 1f;
		this.lightRange = 4;
	}

	@Override
	public void onRender2(RenderContext context) {
		if(node != null) {
			int
				i = node.local.x(),
				j = node.local.y();
			sprites.setFrame((int)(1.5f * context.t + ((i + j) & 1)) % 2);
		}
	}

	@Override
	public void onUpdate2(UpdateContext context) {
	}

}
