package blud.game.level.unit.units;

import blud.game.Game;
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
		entityVisionLevel = 1f;
		entityVisionRange = 4f;
		sprites.loop(0, 3f);
		
		this.defendFrames = 12;
		this.maxHP = 1;
		this.curHP = 1;
	}

	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
		
	}
	
	@Override 
	public void onMove() {
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
			sprites.loop(0,10);
			break;
		case Game.NORTH:
			this.sprites.flip();
			sprites.loop(0,10);
			break;
		case Game.WEST:
			this.sprites.flip();
			sprites.loop(1,10);
			break;
		case Game.SOUTH:
			this.sprites.flop();
			sprites.loop(1, 10);
			break;
	}
	}
	
}
