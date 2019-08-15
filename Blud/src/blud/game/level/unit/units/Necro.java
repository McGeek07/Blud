package blud.game.level.unit.units;

import blud.core.Engine;
import blud.game.Game;
import blud.game.level.levels.Levels;
import blud.game.level.node.Node;
import blud.game.level.unit.Boss;
import blud.game.menu.menus.Menus;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;

public class Necro extends Boss {
	int moveCounter = 0;
	int maxMove = 10;
	int failedMoveAttempts = 0;

	public Necro() {
		super();

		sprites.add(
				Sprites.get("NecroIdleDn"),
				Sprites.get("NecroIdleUp"),
				Sprites.get("NecroWalkDn"),
				Sprites.get("NecroWalkUp")
				);
		this.sprites.set(0);

		this.moveFrames   = 15;
		this.attackFrames = 15;
		this.defendFrames = 150;

		this.alertFrames = 10;
		this.relaxFrames = 30;

		this.moveCooldown = 3;
		this.attackCooldown = 14;
		this.defendCooldown = 0;

		this.maxHP = 5;
		this.curHP = 5;

		this.lightLevel = 1f;
		this.lightRange = 10;	

		this.entityVisionRange = 10;	
		this.detectionRange    = 10;

		this.damage   = 1;
		this.priority = 5;
	}

	public void onAlert() {
		drawFacing = true;
	}
	
	@Override
	public void whileRelaxed() {
		this.moveFrames = 15;
		if(!move(facing))
			failedMoveAttempts++;

		if(moveCounter >= maxMove || failedMoveAttempts > this.moveFrames+this.moveCooldown) {
			this.facing = (this.facing+3)%4;
			this.failedMoveAttempts = 0;
			moveCounter = 0;
		}

		if(this.state == 3) {
			this.aoeDetection = true;
		}else {
			this.aoeDetection = false;
		}

	}


	@Override
	public void whileAlerted() {
		this.moveFrames = 4;
		if(this.state != 3) {
			this.aoeDetection = false;
			if(!move(facing)) {
				if(node.neighbor[facing] != null && !(node.neighbor[facing].unit instanceof FireGhost))
					engage(facing);
			}
		}else{
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
	
	@Override
	public void onKillExit() {
		super.onKillExit();
		Engine.setScene(Menus.CREDITS);
		Levels.level = 0;
		Levels.save();
	}
}
