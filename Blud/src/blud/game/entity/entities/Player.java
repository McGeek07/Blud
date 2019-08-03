package blud.game.entity.entities;

import blud.game.Game;
import blud.game.entity.Entity;
import blud.game.sprite.Sprite;

public class Player extends Entity{

	public Player() {
		super(Sprite.get("Player"));
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

	@Override
	public String getName() {
		return "Player";
	}

}
