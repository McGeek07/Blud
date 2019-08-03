package blud.game.entity.entities;

import blud.game.Game;
import blud.game.entity.Entity;
import blud.game.sprite.Sprite;

public class Player extends Entity{

	public Player() {
		super(Sprite.get("Player"));
		this.currentHealth = 3;
		this.maxHealth = 3;
		this.orientation = Game.SOUTH;
		this.sprites.setSprite(0);
		this.entity_vision_direction = this.orientation;
		this.entity_vision_dropoff = 0f;
		this.entity_vision_transparency = false;
		this.entity_vision_value = 1.0f;
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
