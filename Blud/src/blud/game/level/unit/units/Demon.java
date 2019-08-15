package blud.game.level.unit.units;

import blud.game.Game;
import blud.game.level.node.Node;
import blud.game.level.unit.Undead;
import blud.game.sprite.sprites.Sprites;

public class Demon extends Undead {
	
	int moveCounter = 0;
	int maxMove = 3;
	int failedMoveAttempts = 0;
	
	public Demon() {
		super();
		sprites.add(
				Sprites.get("DemonIdleDn"),
				Sprites.get("DemonIdleUp"),
				Sprites.get("DemonWalkDn"),
				Sprites.get("DemonWalkUp")
				);
		sprites.loop(0, 3f);
		
		this.moveFrames   = 15;
		this.attackFrames = 8;
		this.defendFrames = 12;
		
		this.alertFrames = 2;
		this.relaxFrames = 5;
		
		this.aoeDetection = true;
		this.detectThroughUnits = true;
		
		this.moveCooldown = 3;
		this.attackCooldown = 24;
		this.defendCooldown = 0;
		
		this.maxHP = 1;
		this.curHP = 1;
		
		this.lightLevel = 0f;
		this.lightRange = 0;
		
		this.entityVisionRange = 4;	
		this.detectionRange    = 4;
		this.entityVisionDirection = -1;
		
		this.damage   = 1;
		this.priority = 2;
	}

	@Override
	public void whileRelaxed() {
		//Walk around patrolling
		if(!move(facing))
			failedMoveAttempts++;
		if(moveCounter == maxMove || failedMoveAttempts > this.moveFrames+this.moveCooldown) {
			this.facing = (this.facing+2 )%4;
			this.failedMoveAttempts = 0;
			moveCounter = 0;
		}
	}
	
	@Override
	public void whileAlerted() {
		if(this.target.curHP > 0) {
			//makes the enemy look toward the player
			if(this.node.local.y() == this.target.node.local.y()) {
				if(this.node.local.x() < this.target.node.local.x()) {
					this.facing = Game.EAST;
				}else {
					this.facing = Game.WEST;
				}
			}else if(this.node.local.x() == this.target.node.local.x()) {
				if(this.node.local.y() < this.target.node.local.y()) {
					this.facing = Game.NORTH;
				}else {
					this.facing = Game.SOUTH;
				}
			}else if(this.node.local.x() < this.target.node.local.x()) {
				this.facing = Game.EAST;
			}else if(this.node.local.x() > this.target.node.local.x()) {
				this.facing = Game.WEST;
			}

			if(!move(facing))
				engage(facing);
		}
	}
	
	@Override
	public void onTurn() {
		this.node.level.updateLighting = true;
		this.node.level.updateEntityVision = true;
		this.node.level.updatePlayerVision = true;
	}
	
	@Override 
	public void onMove(Node node) {
		this.moveCounter++;
		this.failedMoveAttempts = 0;
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
			case Game.EAST:
				this.sprites.flop();
				sprites.loop(0, .4f);
				break;
			case Game.NORTH:
				this.sprites.flip();
				sprites.loop(0, .4f);
				break;
			case Game.WEST:
				this.sprites.flop();
				sprites.loop(1, .4f);
				break;
			case Game.SOUTH:
				this.sprites.flip();
				sprites.loop(1, .4f);
				break;
		}
	}
	
}
