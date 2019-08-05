package blud.game.level.unit.units;

import blud.core.input.Input;
import blud.game.Game;
import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector;

public class Player extends Unit{	
	public Player() {
		super();
		sprites.add(Sprites.get("Player"));
		this.playerVisionLevel = 1f;
		this.playerVisionRange = 4f;
		this.moveFrames   = 4;
		this.attackFrames = 8;
	}
	
	@Override
	public void onRender(RenderContext context) {
		
	}

	@Override
	public void onUpdate(UpdateContext context) {
		float
			dx = (pixel.X() - node.level.camera.X()) * .5f,
			dy = (pixel.Y() - node.level.camera.Y()) * .5f;		
		Vector.add(node.level.camera, dx, dy);
		
		int
			w = Input.isKeyDn(Input.KEY_W) ? 1 : 0,
			a = Input.isKeyDn(Input.KEY_A) ? 2 : 0,
			s = Input.isKeyDn(Input.KEY_S) ? 4 : 0,
			d = Input.isKeyDn(Input.KEY_D) ? 8 : 0;
		switch(w | a | s | d) {
			//case 0:
			//case 5:
			//case 10:
			//case 15:
		
			case 1: move(Game.SOUTH); break;
			case 2: move(Game.WEST); break;
			case 4: move(Game.NORTH); break;
			case 8: move(Game.EAST); break;
		}	
		
		if(Input.isKeyDnAction(Input.KEY_UP_ARROW))
			attack(node.neighbor[Game.SOUTH]);
		if(Input.isKeyDnAction(Input.KEY_L_ARROW))
			attack(node.neighbor[Game.WEST]);
		if(Input.isKeyDnAction(Input.KEY_DN_ARROW))
			attack(node.neighbor[Game.NORTH]);
		if(Input.isKeyDnAction(Input.KEY_R_ARROW))
			attack(node.neighbor[Game.EAST]);	
	}

}
