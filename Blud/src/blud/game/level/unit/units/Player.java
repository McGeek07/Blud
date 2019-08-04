package blud.game.level.unit.units;

import blud.core.input.Input;
import blud.game.Game;
import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class Player extends Unit{


	
	public Player() {
		super(Sprites.get("DirectionSprites"));
		this.moveFrameTime = 32;
		this.movementFrameCount = 4;
	}
	
	@Override
	public void onRender(RenderContext context) {
		
	}

	@Override
	public void onUpdate(UpdateContext context) {
		
		grid.level.camera.set(Game.localToPixel(local));
		
		if(this.isMoving == true) {
			this.move(this.facing);
		}
		
		else if(this.isMoving == false) {
			if(Input.isKeyDnAction(Input.KEY_W)) {
				this.facing = 4;
				this.sprites.setFrame(0);
				this.isMoving = true;
				this.move(this.facing);
			}else if(Input.isKeyDnAction(Input.KEY_D)) {
				this.facing = 2;
				this.isMoving = true;
				this.sprites.setFrame(1);
				this.move(this.facing);
			}else if(Input.isKeyDnAction(Input.KEY_S)) {
				this.facing = 0;
				this.isMoving = true;
				this.sprites.setFrame(2);
				this.move(this.facing);
			}else if(Input.isKeyDnAction(Input.KEY_A)) {
				this.facing = 6;
				this.isMoving = true;
				this.sprites.setFrame(3);
				this.move(this.facing);
			}
			
		}
			
	}

}
