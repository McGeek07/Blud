package blud.game.level.tile.tiles;

import java.util.List;

import blud.game.level.tile.Tile;
import blud.util.Loader;

public class Tiles extends Loader<Tile> {
	public static final String
		PACKAGE_NAME = "blud.game.level.tile.tiles.";
	protected static final Tiles
		INSTANCE = new Tiles();
	
	protected Tiles() {
		//do nothing
	}
	
	public static Tile load(String class_name) {
		return INSTANCE.load(PACKAGE_NAME, class_name);
	}
	
	public static List<Tile> load(List<Tile> list) {
		return INSTANCE.load(PACKAGE_NAME, list);
	}
}
