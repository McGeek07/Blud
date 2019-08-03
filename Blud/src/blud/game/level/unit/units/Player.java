package blud.game.level.unit.units;

import blud.game.Game;
import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class Player extends Unit{

	public Player() {
		super(Sprites.get("Player"));
		this.currentHealth = 3;
		this.maxHealth = 3;
		this.orientation = Game.DIRECTION[Game.SOUTH];
		this.sprites.setSprite(0);
		this.entityVisionDirection = 0;
		this.entityVisionRange = 0f;
		this.blocksEntityVision = false;
		this.entityVisionLevel = 1.0f;
		sprites.play(0, 4f);
	}
	
	@Override
	public void onRender(RenderContext context) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onUpdate(UpdateContext context) {
		// TODO Auto-generated method stub
		
	}

}
