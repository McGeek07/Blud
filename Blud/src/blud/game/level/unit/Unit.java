package blud.game.level.unit;

import blud.core.Updateable;
import blud.game.Game;
import blud.game.level.entity.Entity;
import blud.game.level.grid.Grid;
import blud.game.sprite.Sprite;
import blud.geom.Vector;
import blud.geom.Vector2f;

public abstract class Unit extends Entity {	
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
	
	public boolean
		canMove,
		canHurt,
		canHeal,
		canAttack,
		canDefend;
	public int
		maxHP,
		curHP,
		facing,
		damage,
		priority;
	public float
		speed = .1f;
	private int
		steps;
	
	protected Action.Group
		actions = new Action.Group(
				new MoveAction(this)
				);
		
	
	public Unit(Sprite... sprites) {
		super(sprites);
	}
	
	public Unit(Vector local, Sprite... sprites) {
		super(local, sprites);
	}
	
	public Unit(float i, float j, Sprite... sprites) {
		super(i, j, sprites);
	}
	
	public void face(int direction) {
		if(facing != direction) {
			facing = direction;
			this.onFace();
		}
	}
	
	public void turn(int steps) {
		facing += steps;
		if(this.facing < 0)
			facing = 7;
		if(this.facing > 7)
			facing = 0;
		this.onTurn();
	}
	
	public void move(int direction) {
		if(canMove) {
			
		}
	}
	
	public void enage(Unit unit) {
		if(unit != null) {
			if(this.priority - unit.priority >= 0) {
				this.attack(unit);
				unit.defend(this);
			} else {
				unit.attack(this);
				this.defend(unit);
			}
		}
	}
	
	public void attack(Unit unit) {
		if(canAttack)
			onAttack(unit);
	}
	
	public void defend(Unit unit) {
		if(canDefend)
			onDefend(unit);		
	}
	
	public void hurt(int hp) {
		if(canHurt) {
			curHP -= hp;
			if(curHP < 0)
				curHP = 0;
			onHurt();
		}
	}
	
	public void heal(int hp) {
		if(canHeal) {
			curHP += hp;
			if(curHP > maxHP)
				curHP = maxHP;
			onHeal();
		}
	}
	
	public void onFace() { }
	public void onTurn() { }
	public void onMove() { }
	public void onAttack(Unit unit) { }
	public void onDefend(Unit unit) { }
	public void onHurt() { }
	public void onHeal() { }
	
	@Override
	public void render(RenderContext context) {
		super.render(context);
	}
	
	@Override
	public void update(UpdateContext context) {
		actions.update(context);
		super.update(context);		
	}
	
	public static class MoveAction extends Action<Unit> {
		public MoveAction(Unit unit) {
			super(unit);
		}
		
		@Override
		public void onPlay() {
			Vector2f move = Vector.add(Game.DIRECTION[unit.facing], unit.local);			
			Grid grid = unit.grid.level.at(move);
			if(grid == null || grid.unit != null)
				stop();
		}
		
		@Override
		public void onStop() {			
			Grid grid = unit.grid.level.at(unit.local);
			unit.setLocal(grid.local);
			unit.grid.setUnit(null);
			grid.setUnit(unit);
		}

		@Override
		public void update(UpdateContext context) {
			Vector2f move = new Vector2f (
					Game.DIRECTION[unit.facing].X() * unit.speed,
					Game.DIRECTION[unit.facing].Y() * unit.speed
						);
			Vector.add(unit.local, move);
			unit.setLocal(unit.local);
			if(
					Math.abs(unit.local.X() - unit.grid.local.X()) >= 1 ||
					Math.abs(unit.local.Y() - unit.grid.local.Y()) >= 1
					)
			stop();
		}		
	}
	
	public static abstract class Action<T extends Unit> implements Updateable {
		public static final int
			STOP = 0,
			PLAY = 1;
		protected T
			unit;
		protected int
			mode;
		
		public Action(T unit) {
			this.unit = unit;
		}
		
		public void play() {
			mode = PLAY;
			this.onPlay();
		}
		
		public void stop() {
			mode = STOP;
			this.onStop();
		}
		
		public void onPlay() { }
		public void onStop() { }
		
		public static class Group implements Updateable {
			protected Action<?>[]
				actions;
			protected int
				action;
			
			public Group(
					Action<?>... actions
					) {
				this.actions = actions;
			}
			
			public Action<?> getAction() {
				return actions[action];
			}
			
			public void play() {
				if(getAction().mode == STOP)
					getAction().play();
			}
			
			public void stop() {
				if(getAction().mode == PLAY)
					getAction().stop();
			}
			
			public Action<?> setAction(int action) {
				return actions[this.action = action];
			}
			
			public void play(int action) {
				if(getAction().mode == STOP)
					setAction(action).play();
			}
			
			public void stop(int action) {
				if(getAction().mode == STOP)
					setAction(action).stop();
			}

			@Override
			public void update(UpdateContext context) {
				if(getAction().mode == PLAY)
					getAction().update(context);
			}			
		}
	}
	
	
//	public void move(Unit character) {
//		if(grid.unit instanceof Wall) {
//			
//		}else if(grid.unit != null) {
//			fight(character, grid.unit);
//		}else {
//			character.setLocal(Vector.add(local, orientation));
//		}
//	}
//	
//	public void fight(Unit attacker, Unit defender ) {
//		Vector2f battle = Vector2f.mul(attacker.orientation, defender.orientation);
//		if(battle.X() < 0 || battle.Y() < 0) {
//			takeDamage(defender, attacker.strength);
//			takeDamage(attacker, defender.strength);
//		}else {
//			takeDamage(defender, attacker.strength);
//		}
//	}
//	
//	public void takeDamage(Unit character, int damageAmount) {
//		character.currentHealth -= damageAmount;
//	}
}
