package blud.game.wall.walls;

import blud.game.wall.Wall;
import blud.util.Loader;

public class Walls extends Loader<Wall> {
	public static final String[]
		NAMES = {
			"Debug"
		};
	public static final String
		PACKAGE_NAME = "blud.game.wall.walls.";
	
	public Walls() {
		super(PACKAGE_NAME);
	}
	
	public void load() {
		for(String name: NAMES)
			this.load(name);
	}
}
