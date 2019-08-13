package blud.game.level.unit.units;

import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class BoardsRight extends Unit{

	public BoardsRight() {
		super();
		sprites.add(Sprites.get("BoardsRight"));
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
