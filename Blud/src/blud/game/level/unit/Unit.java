package blud.game.level.unit;

import blud.game.level.entity.Entity;
import blud.game.sprite.Sprite;
import blud.geom.Vector;

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
	private int
		steps;
		
	
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
		super.update(context);			
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
