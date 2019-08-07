package blud.game.level.unit.units;

import java.util.LinkedList;

import blud.game.Game;
import blud.game.level.node.Node;
import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class Mancer1 extends Unit {
	
	public Mancer1() {
		super();
		sprites.add(
				Sprites.get("Mancer1IdleDn"),
				Sprites.get("Mancer1IdleUp"),
				Sprites.get("Mancer1WalkDn"),
				Sprites.get("Mancer1WalkUp")
				);
		sprites.loop(0, 3f);
		
		this.moveFrames   = 5;
		this.attackFrames = 8;
		this.defendFrames = 12;
		
		this.moveCooldown = 3;
		this.attackCooldown = 14;
		this.defendCooldown = 0;
		
		this.maxHP = 1;
		this.curHP = 1;
		
		this.damage   = 1;
		this.priority = 1;
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
		LinkedList<Node> list = new LinkedList<>();
		node.walk(list, (node) -> {
		 if(node.unit instanceof Player && node.entityVision > node.level.entityVisionFloor)
				return true;
			return false;
		}, 3, this.facing);
		if(list.size() > 0)
			if(!move(facing))
				engage(facing);
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
