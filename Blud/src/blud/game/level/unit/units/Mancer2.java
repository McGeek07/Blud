package blud.game.level.unit.units;

import blud.game.Game;
import blud.game.level.node.Node;
import blud.game.level.unit.Living;
import blud.game.sprite.sprites.Sprites;

public class Mancer2 extends Living {
	
	int moveCounter = 0;
	int maxMove = 3;
	
	public Mancer2() {
		super();
		sprites.add(
				Sprites.get("Mancer2IdleDn"),
				Sprites.get("Mancer2IdleUp"),
				Sprites.get("Mancer2WalkDn"),
				Sprites.get("Mancer2WalkUp")
				);
		sprites.loop(0, 3f);
		
		this.moveFrames   = 15;
		this.attackFrames = 8;
		this.defendFrames = 12;
		
		this.alertFrames = 5;
		this.relaxFrames = 5;
		
		this.aoeDetection = true;
		this.detectThroughUnits = true;
		
		this.moveCooldown = 3;
		this.attackCooldown = 14;
		this.defendCooldown = 0;
		
		this.maxHP = 1;
		this.curHP = 1;
		
		this.lightLevel = 1f;
		this.lightRange = 3;
		
		this.entityVisionRange = 4;	
		this.detectionRange    = 4;
		this.entityVisionDirection = -1;
		
		this.damage   = 1;
		this.priority = 2;
	}

	@Override
	public void whileRelaxed() {
		//Walk around patrolling
		move(facing);
		if(moveCounter == maxMove) {
			switch(this.facing) {
				case 0:
					this.facing = 2;
					moveCounter = 0;
					break;
				case 1:
					this.facing = 3;
					moveCounter = 0;
					break;
				case 2:
					this.facing = 0;
					moveCounter = 0;
					break;
				case 3:
					this.facing = 1;
					moveCounter = 0;
					break;
			}
			
		}
	}
	
	@Override
	public void whileAlerted() {
		
		//I want it to face toward the player here
		
		
		if(!move(facing))
			engage(facing);
	}
	
	@Override
	public void onTurn() {
		this.node.level.updateLighting = true;
		this.node.level.updateEntityVision = true;
		this.node.level.updatePlayerVision = true;
	}
	
	@Override 
	public void onMove(Node node) {
		moveCounter++;
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
