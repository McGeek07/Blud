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
	
	public void idle() {
		if(state > IDLE) {
			stop();
			frame = 0;
			state = IDLE;
			
			srcNode = null;
			dstNode = null;
			srcPixel.set(pixel);
			dstPixel.set(pixel);			
			onIdle();
		}
	}
	
	public void move(Node node) {			
		if(state < MOVE) {
			stop();
			frame = 0;
			state = MOVE;
			
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
			stop();
			frame = 0;
			state = ATTACK;
			
			srcNode = this.node;
			dstNode =      node;
			srcPixel.set(srcNode.pixel);
			dstPixel.set(dstNode.pixel);
			onAttack();
		}
	}
	
	public void defend(Node node) {
		if(state < DEFEND) {
			stop();
			frame = 0;
			state = DEFEND;
			
			srcNode = this.node;
			dstNode =      node;
			srcPixel.set(srcNode.pixel);
			dstPixel.set(dstNode.pixel);
			onDefend();
		}
	}	
	
	public void stop() {
		pixel.set(node.pixel);
		switch(state) {
			case MOVE:   onStopMove();   break;
			case ATTACK: onStopAttack(); break;
			case DEFEND: onStopDefend(); break;
		}
	}
	
	public void onIdle() { }
	public void onMove() { }   
	public void onAttack() { }
	public void onDefend() { } 
	
	public void onStopMove() { dstNode.reserved = false; }
	public void onStopAttack() { }
	public void onStopDefend() { }
	
	@Override
	public void update(UpdateContext context) {
		onUpdate(context);
		int
			moveFrame2 = moveFrames / 2,
			attackFrame2 = attackFrames / 2,
			defendFrame2 = defendFrames / 2;
		if(state > 0)
			frame ++;
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
				if(frame == moveFrames)
					idle();
			} break;
			case ATTACK: {
				float
					t = (float)frame / attackFrame2,
					x,
					y;
				if(t <= 1f) {
					x = t * .75f * (dstPixel.X() - srcPixel.X()) + srcPixel.X();
					y = t * .75f * (dstPixel.Y() - srcPixel.Y()) + srcPixel.Y();
				} else {
					x = (2f - t) * .75f * (dstPixel.X() - srcPixel.X()) + srcPixel.X();
					y = (2f - t) * .75f * (dstPixel.Y() - srcPixel.Y()) + srcPixel.Y();
				}
				pixel.set(x, y);
				if(frame == attackFrames)
					idle();
			} break;
			case DEFEND: break;
		}
		sprites.update(context);
		effects.update(context);
	}
}
