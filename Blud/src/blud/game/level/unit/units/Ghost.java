package blud.game.level.unit.units;

import blud.game.Game;
import blud.game.level.node.Node;
import blud.game.level.unit.Undead;
import blud.game.sprite.sprites.Sprites;

public class Ghost extends Undead {
	int moveCounter = 0;
	int maxMove = 8;
	int failedMoveAttempts = 0;
	
	public Ghost() {
		super();
		sprites.add(
				Sprites.get("GhostIdleDn"),
				Sprites.get("GhostIdleUp"),
				Sprites.get("GhostWalkDn"),
				Sprites.get("GhostWalkUp")
				);
		
		this.moveFrames   = 15;
		this.attackFrames = 15;
		this.defendFrames = 12;
		
		this.alertFrames = 30;
		
		this.moveCooldown = 3;
		this.attackCooldown = 14;
		this.defendCooldown = 0;
		
		this.maxHP = Integer.MAX_VALUE;
		this.curHP = Integer.MAX_VALUE;
		
		this.lightLevel = 1f;
		this.lightRange = 7;
		this.detectionRange = 0;
		this.entityVisionRange = 7;	
		this.entityVisionDirection = -1;
		
		this.damage   = 0;
		this.priority = 5;
		
		sprites.setSpriteTransparency(.5f);
	}

	@Override
	public void whileRelaxed() {
		//Walk around patrolling
				if(!move(facing))
					failedMoveAttempts++;
				if(moveCounter == maxMove || failedMoveAttempts > this.moveFrames+this.moveCooldown) {
					switch(this.facing) {
						case 0:
							this.facing = 1;
							moveCounter = 0;
							break;
						case 1:
							this.facing = 2;
							moveCounter = 0;
							break;
						case 2:
							this.facing = 3;
							moveCounter = 0;
							break;
						case 3:
							this.facing = 0;
							moveCounter = 0;
							break;
					}
					
				}
		
	}
	
	@Override
	public void whileAlerted() {
		
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
		this.moveCounter++;
		this.failedMoveAttempts = 0;
		switch(this.facing) {
			case Game.EAST:
				this.sprites.flop();
				sprites.loop(2,2);
				break;
			case Game.NORTH:
				this.sprites.flip();
				sprites.loop(2,2);
				break;
			case Game.WEST:
				this.sprites.flip();
				sprites.loop(3,2);
				break;
			case Game.SOUTH:
				this.sprites.flop();
				sprites.loop(3, 2);
				break;
		}
	}
	
	@Override
	public void onIdle() {
		switch(this.facing) {
			case Game.EAST:
				this.sprites.flop();
				sprites.loop(0, 10f);
				break;
			case Game.NORTH:
				this.sprites.flip();
				sprites.loop(0, 10f);
				break;
			case Game.WEST:
				this.sprites.flop();
				sprites.loop(1, 10f);
				break;
			case Game.SOUTH:
				this.sprites.flip();
				sprites.loop(1, 10f);
				break;
		}
	}

}
