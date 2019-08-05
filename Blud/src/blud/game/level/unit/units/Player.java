package blud.game.level.unit.units;

import blud.core.input.Input;
import blud.game.Game;
import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector;

public class Player extends Unit{


	
	public Player() {
		super(Sprites.get("Player"));
		this.moveFrames   = 3;
		this.attackFrames = 6;
		this.playerVisionLevel = 1f;
		this.playerVisionRange = 6f;
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
		
		if(Input.isKeyDnAction(Input.KEY_W))
			move(node.neighbor[Game.SOUTH]);
		if(Input.isKeyDnAction(Input.KEY_A))
			move(node.neighbor[Game.WEST]);
		if(Input.isKeyDnAction(Input.KEY_S))
			move(node.neighbor[Game.NORTH]);
		if(Input.isKeyDnAction(Input.KEY_D))
			move(node.neighbor[Game.EAST]);		
		
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
