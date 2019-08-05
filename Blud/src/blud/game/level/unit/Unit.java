package blud.game.level.unit;

import blud.game.level.entity.Entity;
import blud.game.level.node.Node;
import blud.game.sprite.Sprite;
import blud.geom.Vector;
import blud.geom.Vector2f;

public abstract class Unit extends Entity {
	public static final int
		IDLE = 0,
		MOVE = 1,
		ATTACK = 2,
		DEFEND = 3;
	//vision attributes
	public float
		playerVisionLevel,
		playerVisionRange,
		entityVisionLevel,
		entityVisionRange;
	public boolean
		blocksPlayerVision,
		blocksEntityVision;
	public int
		playerVisionDirection = -1,
		entityVisionDirection = -1;
	
	public int
		moveFrames,
		attackFrames,
		defendFrames,
		state,
		frame;
	public final Vector2f.Mutable
		srcPixel = new Vector2f.Mutable(),
		dstPixel = new Vector2f.Mutable();
	public Node
		srcNode,
		dstNode;
	
		
	
	public int
		maxHP,
		curHP,
		facing,
		damage,
		priority;
	
	
	public Unit(Sprite... sprites) {
		super(sprites);
	}
	
	public Unit(Vector local, Sprite... sprites) {
		super(local, sprites);
	}
	
	public Unit(float i, float j, Sprite... sprites) {
		super(i, j, sprites);
	}
	
	public void idle() {
		pixel.set(node.pixel);
		state = IDLE;
		frame = 0;
		
		srcNode = null;
		dstNode = null;
		srcPixel.set(0, 0);
		dstPixel.set(0, 0);
		
	}
	
	public void move(Node node) {
		if(state < MOVE) {
			pixel.set(this.node.pixel);
			state = MOVE;
			frame = 0;
			
			srcNode = this.node;
			dstNode =      node;
			srcPixel.set(srcNode.pixel);
			dstPixel.set(dstNode.pixel);
			onMove();
		}
	}
	
	public void attack(Node node) {
		if(state < ATTACK) {
			pixel.set(this.node.pixel);
			state = ATTACK;
			frame = 0;
			
			srcNode = this.node;
			dstNode =      node;
			srcPixel.set(srcNode.pixel);
			dstPixel.set(dstNode.pixel);
			onAttack();
		}
	}
	
	public void defend(Node node) {
		if(state < DEFEND) {
			pixel.set(this.node.pixel);
			state = DEFEND;
			frame = 0;
			
			srcNode = this.node;
			dstNode =      node;
			srcPixel.set(srcNode.pixel);
			dstPixel.set(dstNode.pixel);
			onDefend();
		}
	}	
	
	public void onIdle() { }
	public void onMove() { }
	public void onAttack() { }
	public void onDefend() { }
	
	@Override
	public void update(UpdateContext context) {
		int
			moveFrame2 = moveFrames / 2,
			attackFrame2 = attackFrames / 2,
			defendFrame2 = defendFrames / 2;
		switch(state) {
			case IDLE: break; //do nothing
			case MOVE: {
				float
					t = (float)frame / moveFrames,
					x = t * (dstPixel.X() - srcPixel.X()) + srcPixel.X(),
					y = t * (dstPixel.Y() - srcPixel.Y()) + srcPixel.Y();
				pixel.set(x, y);
				if(frame == moveFrame2) {
					srcNode.setUnit(null);
					dstNode.setUnit(this);
				}
				if(frame == moveFrames) {
					pixel.set(dstPixel);
					state = IDLE;
				}
				frame ++;
			} break;
			case ATTACK: {
				float
					t = (float)frame / attackFrames,				
					x,
					y;
				if(t <= .5f) {
					x = t * (dstPixel.X() - srcPixel.X()) + srcPixel.X();
					y = t * (dstPixel.Y() - srcPixel.Y()) + srcPixel.Y();
				} else {
					x = t * (srcPixel.X() - dstPixel.X()) + dstPixel.X();
					y = t * (srcPixel.Y() - dstPixel.Y()) + dstPixel.Y();
				}
				pixel.set(x, y);
				if(frame == attackFrames) {
					pixel.set(srcPixel);
					state = IDLE;
				}
				frame ++;
			} break;
			case DEFEND: break;
		}
		super.update(context);
	}
}
