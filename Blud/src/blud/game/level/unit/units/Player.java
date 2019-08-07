package blud.game.level.unit.units;

import blud.core.input.Input;
import blud.game.Game;
import blud.game.level.unit.Unit;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector;
import blud.util.Util;

public class Player extends Unit {
	protected final Sprite
		heart = Sprites.get("Heart");	
	public float
		cameraSpeedX = 1,
		cameraSpeedY = 2;
	
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
		this.moveFrames   = 5;
		this.attackFrames = 8;
		this.defendFrames = 12;
		
		this.moveCooldown = 3;
		this.attackCooldown = 6;
		this.defendCooldown = 0;
		
		this.maxHP = 3;
		this.curHP = 3;
		
		this.damage   = 1;
		this.priority = 2;
	}
	
	public void take(int facing) {
		if(!move(facing))
			engage(facing);
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
			dx = pixel.x() - node.level.camera.x(),
			dy = pixel.y() - node.level.camera.y();
		dx = Util.box(dx, -cameraSpeedX, cameraSpeedX);
		dy = Util.box(dy, -cameraSpeedY, cameraSpeedY);
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
				take(Game.SOUTH);
				break;
			case  2: //a
			case  3: //w + a
			case  7: //w + a + s
				take(Game.WEST );
				break;
			case  4: //s
			case  6: //a + s
			case 14: //a + s + d
				take(Game.NORTH);
				break;
			case  8: //d
			case 12: //s + d
			case 13: //w + s + d
				take(Game.EAST );
				break;			
		}		
	}
	
	@Override 
	public void onMove() {
		switch(this.facing) {
			case Game.EAST:
				this.sprites.flop();
				sprites.loop(2,10);
				break;
			case Game.NORTH:
				this.sprites.flip();
				sprites.loop(2,10);
				break;
			case Game.WEST:
				this.sprites.flip();
				sprites.loop(3,10);
				break;
			case Game.SOUTH:
				this.sprites.flop();
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
