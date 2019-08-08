package blud.game.level.unit;

import java.util.LinkedList;

import blud.game.level.node.Node;
import blud.game.level.node.Node.Check;
import blud.geom.Vector;

public abstract class Enemy extends Unit {	
	public final LinkedList<Node>
		list = new LinkedList<>();
	public Check
		check1,
		check2;
	
	public Enemy() {
		super();
	}
	
	public Enemy(Vector local) {
		super(local);
	}
	
	public Enemy(float i, float j) {
		super(i, j);
	}
	
	
	
	
	
	
	public void detect() {
		
	}
}
