package blud.game.level.unit;

import blud.game.level.entity.Entity;
import blud.game.level.node.Node;
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
	
	
	public Unit() {
		super();
	}
	
	public Unit(Vector local) {
		super(local);
	}
	
	public Unit(float i, float j) {
		super(i, j);
	}
	
	public void move(int facing) {
		Node node = this.node.neighbor[this.facing = facing];
		if(node != null && !node.isReserved())
			move(node);
	}
	
	public void attack(int facing) {
		
	}
	
	public void defend(int facing) {
		
	}
	
	public void cancel() {
		switch(state) {
			case MOVE:
				dstNode.reserved = false;
				srcNode = null;
				dstNode = null;
				onCancelMove(); 
				break;
			case ATTACK: onCancelAttack(); break;
			case DEFEND: onCancelDefend(); break;
		}
	}
	
	public void idle() {
		pixel.set(node.pixel);
		state = IDLE;
		frame = 0;
		
		srcNode = null;
		dstNode = null;
		srcPixel.set(0, 0);
		dstPixel.set(0, 0);
		onIdle();
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
			dstNode.reserved = true;
			onMove();
		}
	}
	
	public void attack(Node node) {
		if(state < ATTACK) {
			cancel();
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
			cancel();
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
	
	public void onCancelMove() { }
	public void onCancelAttack() { }
	public void onCancelDefend() { }
	
	@Override
	public void update(UpdateContext context) {
		onUpdate(context);
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
					dstNode.reserved = false;
					pixel.set(dstPixel);
					idle();
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
					idle();
				}
				frame ++;
			} break;
			case DEFEND: break;
		}
		sprites.update(context);
		effects.update(context);
	}
}
