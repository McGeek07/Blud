package blud.game.level.unit.units;

import blud.core.input.Input;
import blud.game.Game;
import blud.game.level.node.Node;
import blud.game.level.unit.Unit;
import blud.game.level.unit.Wall;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector;

public class Player extends Unit {
	protected final Sprite
		heart = Sprites.get("Heart"),
		light = Sprites.get("Light"),
		sight = Sprites.get("Sight"),
		cursor = Sprites.get("TileCursor2");
	public float
		cameraSpeedX = .2f,
		cameraSpeedY = .3f;
	
	public Player() {
		super();
		sprites.add(
				Sprites.get("PlayerIdleDn"),
				Sprites.get("PlayerIdleUp"),
				Sprites.get("PlayerWalkDn"),
				Sprites.get("PlayerWalkUp")
				);
		cursor.loop(4f);
		
		this.playerVisionRange = 6;
		this.blocksPlayerVision = false;
		this.blocksEntityVision = false;
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
	public void onRender1(RenderContext context) {
		if(node != null) {
			cursor.pixel.set(node.pixel);
			cursor.render(context);
		}
	}
	
	@Override
	public void onRender2(RenderContext context) {
		if(node != null) {
			context = context.push();
			context.g2D.translate(
				node.level.camera.x() - context.canvas_w / 2,
				node.level.camera.y() - context.canvas_h / 2
				);
			for(int i = 0; i < curHP; i ++) {
				heart.pixel.set(3 + i * 6, 4);
				heart.render(context);
			}
			
			light.pixel.set( context.canvas_w - 8, 4);
			light.frame = node.lightLevel > 0 ? 1 : 0;				
			light.render(context);
			
			sight.pixel.set( context.canvas_w - 3, 3);
			sight.frame = node.entityVision   ? 1 : 0;
			sight.render(context);
			
			context = context.pull();
		}
	}

	@Override
	public void onUpdate1(UpdateContext context) {
		float
			dx = (pixel.x() - node.level.camera.x()) * cameraSpeedX,
			dy = (pixel.y() - node.level.camera.y()) * cameraSpeedY;
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
		cursor.update(context);
	}
	
	@Override 
	public void onMove(Node node) {
		Node
			src1 = srcNode.level.at(Vector.add(srcNode.local, 0, 1)),
			src2 = srcNode.level.at(Vector.add(srcNode.local, 1, 1)),
			src3 = srcNode.level.at(Vector.add(srcNode.local, 1, 0)),
			dst1 = dstNode.level.at(Vector.add(dstNode.local, 0, 1)),
			dst2 = dstNode.level.at(Vector.add(dstNode.local, 1, 1)),
			dst3 = dstNode.level.at(Vector.add(dstNode.local, 1, 0));
		if(src1 != null && src1.unit instanceof Wall) src1.unit.setSpriteTransparency(0f);
		if(src2 != null && src2.unit instanceof Wall) src2.unit.setSpriteTransparency(0f);
		if(src3 != null && src3.unit instanceof Wall) src3.unit.setSpriteTransparency(0f);
		if(dst1 != null && dst1.unit instanceof Wall) dst1.unit.setSpriteTransparency(.5f);
		if(dst2 != null && dst2.unit instanceof Wall) dst2.unit.setSpriteTransparency(.5f);
		if(dst3 != null && dst3.unit instanceof Wall) dst3.unit.setSpriteTransparency(.5f);
		switch(this.facing) {
			case Game.EAST:
				this.sprites.flop();
				sprites.loop(2,5);
				break;
			case Game.NORTH:
				this.sprites.flip();
				sprites.loop(2,5);
				break;
			case Game.WEST:
				this.sprites.flip();
				sprites.loop(3,5);
				break;
			case Game.SOUTH:
				this.sprites.flop();
				sprites.loop(3, 5);
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
	
	@Override
	public void onKillExit() {
		node.level.reset();
	}
}
