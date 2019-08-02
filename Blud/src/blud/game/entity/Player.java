package blud.game.entity;

import blud.game.Game;
import blud.game.sprite.Sprite;

public class Player extends Entity{

	public Player() {
		super(Sprite.get("Player"));
		this.currentHealth = 3;
		this.maxHealth = 3;
		this.orientation = Game.SOUTH;
		this.sprites.setSprite(0);
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
