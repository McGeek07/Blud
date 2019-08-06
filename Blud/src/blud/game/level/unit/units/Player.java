package blud.game.level.unit.units;

import java.util.LinkedList;
import java.util.List;

import blud.core.input.Input;
import blud.game.Game;
import blud.game.level.node.Node;
import blud.game.level.unit.Unit;
import blud.game.level.unit.Wall;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector;

public class Player extends Unit {
	protected final Sprite
		heart = Sprites.get("Heart");
	
	public Player() {
		super();
		sprites.add(Sprites.get("Player"));
		this.playerVisionLevel = 1f;
		this.playerVisionRange = 8f;
		this.moveFrames   = 4;
		this.attackFrames = 8;
		this.defendFrames = 12;
		
		this.cooldownFrames = 2;
		
		this.maxHP = 3;
		this.curHP = 3;
	}
	
	@Override
	public void onRender(RenderContext context) {
		if(node != null) {
			context = context.push();
			context.g2D.translate(
				node.level.camera.x() - context.canvas_w / 2,
				node.level.camera.y() - context.canvas_h / 2
				);
			for(int i = 0; i < curHP; i ++) {
				heart.pixel.set(5 + i * 9, 5);
				heart.render(context);
			}
			context = context.pull();
		}
	}

	@Override
	public void onUpdate(UpdateContext context) {
		List<Node> walk = node.walk(new LinkedList<Node>(), (node) -> {
			return (node.local.x() > this.node.local.x()  ||
					node.local.y() > this.node.local.y()) &&
					node.unit instanceof Wall;
		}, 2, -1);
		
		for(Node node: walk)
			node.unit.setSpriteTransparency(.5f);
		
		
		float
			dx = (pixel.X() - node.level.camera.X()) * .5f,
			dy = (pixel.Y() - node.level.camera.Y()) * .5f;		
		Vector.add(node.level.camera, dx, dy);
		
		int
			w = Input.isKeyDn(Input.KEY_W) ? 1 : 0,
			a = Input.isKeyDn(Input.KEY_A) ? 2 : 0,
			s = Input.isKeyDn(Input.KEY_S) ? 4 : 0,
			d = Input.isKeyDn(Input.KEY_D) ? 8 : 0;
		switch(w | a | s | d) {
			//case 0:
			//case 5:
			//case 10:
			//case 15:
		
			case 1: move(Game.SOUTH); break;
			case 2: move(Game.WEST); break;
			case 4: move(Game.NORTH); break;
			case 8: move(Game.EAST); break;
		}	
		
		if(Input.isKeyDnAction(Input.KEY_UP_ARROW))
			defend(node.neighbor[Game.SOUTH]);
		if(Input.isKeyDnAction(Input.KEY_L_ARROW))
			attack(node.neighbor[Game.WEST]);
		if(Input.isKeyDnAction(Input.KEY_DN_ARROW))
			attack(node.neighbor[Game.NORTH]);
		if(Input.isKeyDnAction(Input.KEY_R_ARROW))
			attack(node.neighbor[Game.EAST]);	
	}

}
