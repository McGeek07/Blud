package blud.game.level.unit.units;

import blud.core.input.Input;
import blud.game.Game;
import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class Player extends Unit{

	public Player() {
		super(Sprites.get("Player"));
	}
	
	@Override
	public void onRender(RenderContext context) {
		
	}

	@Override
	public void onUpdate(UpdateContext context) {
		grid.level.camera.set(Game.localToPixel(local));
		if(Input.isKeyDnAction(Input.KEY_W))
			actions.play(0);
	}

}
