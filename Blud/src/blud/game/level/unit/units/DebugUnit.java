package blud.game.level.unit.units;

import java.util.LinkedList;

import blud.game.level.node.Node;
import blud.game.level.unit.Unit;
import blud.game.sprite.sprites.Sprites;

public class DebugUnit extends Unit {
	
	public DebugUnit() {
		super();
		sprites.add(Sprites.get("DebugUnit"));
		this.entityVisionLevel = 1f;
		this.entityVisionRange = 4f;
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
		}, 3, -1);
		if(list.size() > 0)
			for(Node node: list)
				System.out.println(node.unit);
	}
}
