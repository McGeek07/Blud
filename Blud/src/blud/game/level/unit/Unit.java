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
		COOLDOWN = 3,
		DEFEND = 4;
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
		cooldownFrames,
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
		if(state < MOVE) {
			Node node = this.node.neighbor[this.facing = facing];
			if(node != null && !node.isReserved())
				move(node);
		}
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
	
	public void cooldown() {
		if(state < COOLDOWN) {
			stop();
			frame = 0;
			state = COOLDOWN;
			
			onCooldown();
		}
	}
	
	public void stop() {
		pixel.set(node.pixel);
		switch(state) {
			case MOVE:   onStopMove();   break;
			case ATTACK: onStopAttack(); break;
			case DEFEND: onStopDefend(); break;
			case COOLDOWN: onStopCooldown(); break;
		}
	}
	
	public void onIdle() { }
	public void onMove() { }   
	public void onAttack() { }
	public void onDefend() { } 
	public void onCooldown() { }
	
	public void onStopMove() { dstNode.reserved = false; }
	public void onStopAttack() { }
	public void onStopDefend() { }
	public void onStopCooldown() { }
	
	@Override
	public void update(UpdateContext context) {
		onUpdate(context);
		int
			moveFrame2 = moveFrames / 2,
			attackFrame2 = attackFrames / 2,
			defendFrame4 = defendFrames / 4;
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
					cooldown();
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
				if(frame >= attackFrames)
					cooldown();
			} break;
			case DEFEND:
				float
					t = (float)frame / defendFrame4,
					x,
					y;
				if(t <= 1f) {					
					x = srcPixel.X() + (2f * t);
					y = srcPixel.Y();
				} else if(t <= 2){
					t = 2f - t;
					x = srcPixel.X() + (2f * t);
					y = srcPixel.Y();
				} else if(t <= 3) {
					t = t - 3f;
					x = srcPixel.X() - (2f * t);
					y = srcPixel.Y();
				} else {
					t = 4f - t;;
					x = srcPixel.X() - (2f * t);
					y = srcPixel.Y();
				}
				pixel.set(x, y);
				if((frame + 0) % 4 == 0) {
					sprites.setWhiteTransparency(0f);
				}
				if((frame + 2) % 4 == 0) {
					sprites.setWhiteTransparency(1f);			
				}
				if(frame >= defendFrames) {
					sprites.setWhiteTransparency(1f);
					idle();
				}
				break;
			case COOLDOWN:
				if(frame >= cooldownFrames)
					idle();
				break;
		}
		sprites.update(context);
		effects.update(context);
	}
}
