package blud.game.level.unit.units;

import blud.game.Game;
import blud.game.level.node.Node;
import blud.game.level.unit.Living;
import blud.game.sprite.sprites.Sprites;

public class Slime extends Living {
	
	public Slime() {
		super();
		sprites.add(
				Sprites.get("SlimeIdleDn"),
				Sprites.get("SlimeIdleUp"),
				Sprites.get("SlimeWalkDn"),
				Sprites.get("SlimeWalkUp")
				);
		
		this.moveFrames   = 15;
		this.attackFrames = 15;
		this.defendFrames = 12;
		
		this.alertFrames = 30;
		
		this.moveCooldown = 3;
		this.attackCooldown = 14;
		this.defendCooldown = 0;
		
		this.maxHP = 1;
		this.curHP = 1;
		
		this.entityVisionRange = 4;	
		this.detectionRange    = 4;
		
		this.damage   = 0;
		this.priority = 1;
	}

	@Override
	public void whileRelaxed() {
		//do nothing
	}
	
	@Override
	public void whileAlerted() {
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
				sprites.loop(3,10);
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
		this.entityVisionDirection = facing;
	}
	
}
