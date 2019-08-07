package blud.game.level.unit;

import blud.game.level.entity.Entity;
import blud.game.level.node.Node;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector;

public abstract class Unit extends Entity {
	protected final Sprite
	danger = Sprites.get("Danger");	
	public static final int
		IDLE	= 0,
		ATTACK 	= 1,
		MOVE 	= 2,
		DEFEND 	= 3,
		KILL  	= 4;
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
		deathFrames,
		moveCooldown,
		attackCooldown,
		defendCooldown,
		state,
		frame;
	public Node
		srcNode,
		dstNode;		
	
	public int
		maxHP = 1,
		curHP = 1,
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
	
	public boolean move(int facing) {
		if(state < MOVE) {
			Node node = this.node.neighbor[this.facing = facing];
			if(node != null && !node.isReserved()) {
				move(node);
				return true;
			}
		}
		return false;
	}
	
	public boolean attack(int facing) {
		if(state < ATTACK) {
			Node node = this.node.neighbor[this.facing = facing];
			if(node != null && node.unit != null && !(node.unit instanceof Wall)) {
				attack(node);
				return true ;
			}
		}
		return false;
	}
	
	public boolean defend(int facing) {
		if(state < DEFEND) {
			Node node = this.node.neighbor[this.facing = facing];
			if(node != null && node.unit != null && !(node.unit instanceof Wall)) {
				defend(node);
				return true;
			}
		}
		return false;
	}
	
	public boolean engage(int facing) {
		if(state < ATTACK) {
			Node node = this.node.neighbor[this.facing = facing];
			Unit unit = node != null ? node.unit : null;
			if(node.unit != null && !(node.unit instanceof Wall)) {
				if(((this.facing + unit.facing) & 1) == 0) {
					switch(Integer.compare(this.priority, unit.priority)) {
						case -1:
							unit.attack(this.node);
							this.defend(unit.node);
							this.curHP -= unit.damage;
							break;
						case  1: 
							this.attack(unit.node);
							unit.defend(this.node);
							unit.curHP -= this.damage;
							break;
						case 0:
							this.defend(unit.node);
							unit.defend(this.node);
							this.curHP -= unit.damage;
							unit.curHP -= this.damage;
							break;
					}
				} else {
					this.attack(unit.node);
					unit.defend(this.node);
					unit.curHP -= this.damage;
				}
				return true;
			}
		}
		return false;
	}
	
	public void idle() {
		if(state != IDLE) {			
			frame = 0;
			state = IDLE;
			srcNode = null;
			dstNode = null;
			onIdle();
		}
	}
	
	public void move(Node node) {			
		if(state < MOVE) {
			exit();
			
			frame = 0;
			state = MOVE;
			srcNode = this.node;
			dstNode =      node;
			dstNode.reserved = true; 
			onMove(node);
		}
	}
	
	public void attack(Node node) {
		if(state < ATTACK) {
			exit();
			
			frame = 0;
			state = ATTACK;			
			srcNode = this.node;
			dstNode =      node;
			onAttack(node);
		}
	}
	
	public void defend(Node node) {
		if(state < DEFEND) {
			exit();
			
			frame = 0;
			state = DEFEND;			
			srcNode = this.node;
			dstNode =      node;
			onDefend(node);
		}
	}
	
	public void kill() {
		if(state < KILL) {			
			frame = 0;
			state = KILL;
			srcNode = null;
			dstNode = null;
			onKill();
		}
	}
	
	protected void exit() {
		pixel.set(node.pixel);
		switch(state) {
			case IDLE: onIdleExit(); break;
			case MOVE: onMoveExit(); break;
			case ATTACK: onAttackExit(); break;
			case DEFEND: onDefendExit(); break;
			case KILL: onKillExit(); break;
		}
	}
	
	public void onIdle() { }
	public void onMove(Node node) { }
	public void onAttack(Node node) { }
	public void onDefend(Node node) { }
	public void onKill() { }
	
	public void onIdleExit() { 
		//do nothing
	}
	public void onMoveExit() { 
		dstNode.reserved = false; idle();
	}
	public void onAttackExit() {
		if(curHP <= 0) kill(); else idle();
	}
	public void onDefendExit() { 
		if(curHP <= 0) kill(); else idle();
	}
	public void onKillExit() {
		node.setUnit(null);
	}
	
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
			case MOVE:
				if(frame <= moveFrames) {
					float
						t = (float)frame / moveFrames,
						x = t * (dstNode.pixel.X() - srcNode.pixel.X()) + srcNode.pixel.X(),
						y = t * (dstNode.pixel.Y() - srcNode.pixel.Y()) + srcNode.pixel.Y();
					pixel.set(x, y);
					if(frame == moveFrame2) {
						srcNode.setUnit(null);
						dstNode.setUnit(this);
					}
				} else if(frame - moveFrames >= moveCooldown)
					exit();
				break;
			case ATTACK:
				if(frame <= attackFrames) {
					float
						t = (float)frame / attackFrame2,
						x,
						y;
					if(t <= 1f) {
						x = t * .75f * (dstNode.pixel.X() - srcNode.pixel.X()) + srcNode.pixel.X();
						y = t * .75f * (dstNode.pixel.Y() - srcNode.pixel.Y()) + srcNode.pixel.Y();
					} else {
						x = (2f - t) * .75f * (dstNode.pixel.X() - srcNode.pixel.X()) + srcNode.pixel.X();
						y = (2f - t) * .75f * (dstNode.pixel.Y() - srcNode.pixel.Y()) + srcNode.pixel.Y();
					}
					pixel.set(x, y);
				} else if(frame - attackFrames >= attackCooldown)
					exit();
				break;
			case DEFEND:
				if(frame <= defendFrames) {
					float
						t = (float)frame / defendFrame4,
						x,
						y;
					if(t <= 1f) {					
						x = srcNode.pixel.X() + (2f * t);
						y = srcNode.pixel.Y();
					} else if(t <= 2){
						t = 2f - t;
						x = srcNode.pixel.X() + (2f * t);
						y = srcNode.pixel.Y();
					} else if(t <= 3) {
						t = t - 3f;
						x = srcNode.pixel.X() - (2f * t);
						y = srcNode.pixel.Y();
					} else {
						t = 4f - t;;
						x = srcNode.pixel.X() - (2f * t);
						y = srcNode.pixel.Y();
					}
					pixel.set(x, y);
					if((frame + 0) % 4 == 0)
						sprites.setWhiteTransparency(0f);
					if((frame + 2) % 4 == 0)
						sprites.setWhiteTransparency(1f);
					if(frame >= defendFrames)
						sprites.setWhiteTransparency(1f);
				} else if(frame - defendFrames >= defendCooldown)
					exit();		
				break;
			case KILL:
				if(frame >= deathFrames)
					exit();
		}
		sprites.update(context);
		effects.update(context);
	}
}
