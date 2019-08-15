package blud.game.level.unit.units;

import blud.core.Engine;
import blud.game.level.levels.Levels;
import blud.game.level.node.Node;
import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class Stairs extends Unit {
	
	public Stairs() {
		super();
		sprites.add(Sprites.get("Stairs"));
		
		this.blocksLight = false;
		this.blocksPlayerVision = false;
		this.blocksEntityVision = false;
		this.curHP = Integer.MAX_VALUE;
		this.maxHP = Integer.MAX_VALUE;
	}
	
	@Override
	public void onDefend(Node node) {
		if(node.unit instanceof Player)
			Engine.setScene(Levels.next());
	}
	
}
