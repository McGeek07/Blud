package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class BookShelfLeft extends Unit{

	public BookShelfLeft() {
		super();
		sprites.add(Sprites.get("BookShelfLeft"));
		
		this.curHP = 1;
		this.maxHP = 1;
	}
	
	@Override
	public void onRender2(RenderContext context) {
	}

	@Override
	public void onUpdate2(UpdateContext context) {
	}

}
