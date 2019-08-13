package blud.game.level.unit.units;

import blud.game.Game;
import blud.game.level.node.Node;
import blud.game.level.unit.Undead;
import blud.game.sprite.sprites.Sprites;

public class Zombie extends Undead{
	
	int moveCounter = 0;
	int maxMove = 3;
	int failedMoveAttempts = 0;
	
	public Zombie() {
		super();
		sprites.add(
				Sprites.get("ZombieIdleDn"),
				Sprites.get("ZombieIdleUp"),
				Sprites.get("ZombieWalkDn"),
				Sprites.get("ZombieWalkUp")
				);
		sprites.loop(0, 3f);
		
		this.moveFrames   = 25;
		this.attackFrames = 8;
		this.defendFrames = 12;
		
		this.alertFrames = 15;
		
		this.moveCooldown = 10;
		this.attackCooldown = 14;
		this.defendCooldown = 0;
		
		this.maxHP = 1;
		this.curHP = 1;
		
		this.entityVisionRange = 4;	
		this.detectionRange    = 4;
		
		this.damage   = 1;
		this.priority = 1;
	}

	@Override
	public void whileRelaxed() {
		this.moveFrames = 25;
		//patrol
		if(!move(facing))
			failedMoveAttempts++;
		if(moveCounter >= maxMove || failedMoveAttempts > this.moveFrames+this.moveCooldown) {
			this.facing = (this.facing+2)%4;
			this.failedMoveAttempts = 0;
			moveCounter = 0;
		}
		
	}
	
	@Override
	public void whileAlerted() {
		this.moveFrames = 10;
		if(!move(facing))
			engage(facing);
	}
	
	@Override
	public void onTurn() {
		this.entityVisionDirection = facing;
		this.node.level.updateLighting = true;
		this.node.level.updateEntityVision = true;
		this.node.level.updatePlayerVision = true;
	}
	
	@Override 
	public void onMove(Node node) {
		this.failedMoveAttempts = 0;
		this.moveCounter++;
		switch(this.facing) {
			case Game.EAST:
				this.sprites.flop();
				sprites.loop(2,.8f);
				break;
			case Game.NORTH:
				this.sprites.flip();
				sprites.loop(2,.8f);
				break;
			case Game.WEST:
				this.sprites.flip();
				sprites.loop(3,.8f);
				break;
			case Game.SOUTH:
				this.sprites.flop();
				sprites.loop(3,.8f);
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
