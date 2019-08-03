package blud.game.tile.tiles;

import blud.game.tile.Tile;
import blud.util.Loader;

public class Tiles extends Loader<Tile> {
	public static final String[]
		NAMES = {
			"Debug",
			"StoneTile"
		};
	protected final static String
		PACKAGE_NAME = "blud.game.tile.tiles.";	
	
	public Tiles() {
		super(PACKAGE_NAME);			
	}
	
	public void load() {
		for(String name: NAMES)
			this.load(name);
	}
}
