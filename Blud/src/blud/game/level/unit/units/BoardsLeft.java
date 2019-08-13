package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class BoardsLeft extends Unit{

	public BoardsLeft() {
		super();
		sprites.add(Sprites.get("BoardsLeft"));
		this.curHP = 1;
		this.maxHP = 1;
		this.defendFrames = 12;
	}
	
	@Override
	public void onRender2(RenderContext context) {
	}

	@Override
	public void onUpdate2(UpdateContext context) {
	}

}
