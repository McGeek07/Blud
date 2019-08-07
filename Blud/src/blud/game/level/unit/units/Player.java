package blud.game.level.unit.units;

import blud.core.input.Input;
import blud.game.Game;
import blud.game.level.unit.Unit;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector;
import blud.geom.Vector2f;

public class Player extends Unit {
	protected final Sprite
		heart = Sprites.get("Heart");
	
	public Player() {
		super();
		sprites.add(
				Sprites.get("PlayerIdleDn"),
				Sprites.get("PlayerIdleUp"),
				Sprites.get("PlayerWalkDn"),
				Sprites.get("PlayerWalkUp")
				);
		this.playerVisionLevel = 1f;
		this.playerVisionRange = 8f;
		this.moveFrames   = 8;
		this.attackFrames = 8;
		this.defendFrames = 12;
		
		this.cooldownFrames = 2;
		
		this.maxHP = 3;
		this.curHP = 3;
	}
	
	@Override
	public void onRender(RenderContext context) {
		if(node != null) {
			context = context.push();
			context.g2D.translate(
				node.level.camera.x() - context.canvas_w / 2,
				node.level.camera.y() - context.canvas_h / 2
				);
			for(int i = 0; i < curHP; i ++) {
				heart.pixel.set(5 + i * 9, 5);
				heart.render(context);
			}
			context = context.pull();
		}
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
			case  1: //w
			case  9: //w + d
			case 11: //w + a + d
				this.sprites.loop(2, 30);
				move(Game.SOUTH);
				this.sprites.loop(0, 1);
				break;
			case  2: //a
			case  3: //w + a
			case  7: //w + a + s
				move(Game.WEST );
				this.sprites.loop(0, 1);
				break;
			case  4: //s
			case  6: //a + s
			case 14: //a + s + d
				move(Game.NORTH);
				this.sprites.loop(1, 1);
				break;
			case  8: //d
			case 12: //s + d
			case 13: //w + s + d
				move(Game.EAST);
				this.sprites.loop(1, 1);
				break;			
		}	
		
		if(Input.isKeyDnAction(Input.KEY_UP_ARROW))
			defend(node.neighbor[Game.SOUTH]);
		if(Input.isKeyDnAction(Input.KEY_L_ARROW))
			attack(node.neighbor[Game.WEST]);
		if(Input.isKeyDnAction(Input.KEY_DN_ARROW))
			attack(node.neighbor[Game.NORTH]);
		if(Input.isKeyDnAction(Input.KEY_R_ARROW))
			attack(node.neighbor[Game.EAST]);	
		
		switch(this.facing) {
			case Game.NORTH:
			case Game.EAST:
				sprites.set(0);
				break;
			case Game.SOUTH:
			case Game.WEST:
				sprites.set(1);
				break;
		}
		
		this.entityVisionRange = 2f;
	}

}
