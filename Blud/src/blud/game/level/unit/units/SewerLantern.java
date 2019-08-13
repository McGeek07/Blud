package blud.game.level.unit.units;

import blud.core.Renderable.RenderContext;
import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class SewerLantern extends Unit {
	
	public SewerLantern() {
		super();
		sprites.add(Sprites.get("SewerLantern"));
		lightLevel = 1;
		lightRange = 4;
		sprites.loop(0, 3f);
		
		this.defendFrames = 12;
		this.maxHP = 1;
		this.curHP = 1;
	}
	
	@Override
	public void onRender1(RenderContext context) {
		if(node != null) {
			int
				i = node.local.x(),
				j = node.local.y();
			if((i+j)%2 == 0)
			sprites.flip();
		}
	}
	
}
