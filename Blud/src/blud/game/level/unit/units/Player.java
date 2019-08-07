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
		this.moveFrames   = 12;
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
		Vector2f camera = Game.pixelToLocal(node.level.camera);
		float
			dx,
			dy;
		if(dstNode != null) {
			dx = (dstNode.local.X() - camera.X()) * .5f;
			dy = (dstNode.local.Y() - camera.Y()) * .5f;
		} else {
			dx = (node.local.X() - camera.X()) * .5f;
			dy = (node.local.Y() - camera.Y()) * .5f;
		}
		Vector.add(node.level.camera, Game.localToPixel(dx, dy));
		
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
				move(Game.SOUTH);
				break;
			case  2: //a
			case  3: //w + a
			case  7: //w + a + s
				move(Game.WEST );
				break;
			case  4: //s
			case  6: //a + s
			case 14: //a + s + d
				move(Game.NORTH);
				break;
			case  8: //d
			case 12: //s + d
			case 13: //w + s + d
				move(Game.EAST);
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
	
	@Override 
	public void onMove() {
		switch(this.facing) {
		case Game.NORTH:
		case Game.EAST:
			sprites.loop(2,10);
			break;
		case Game.SOUTH:
		case Game.WEST:
			sprites.loop(3, 10);
			break;
		}
	}
	
	@Override
	public void onIdle() {
		switch(this.facing) {
		case Game.NORTH:
		case Game.EAST:
			sprites.loop(0,1);
			break;
		case Game.SOUTH:
		case Game.WEST:
			sprites.loop(1, 1);
			break;
	}
	}

}
