package blud.game.entity.entities;

import blud.game.entity.Entity;
import blud.util.Loader;

public class Entities extends Loader<Entity> {
	public static final String[]
			NAMES = {
				"Player"
			};
	public static final String
		PACKAGE_NAME = "blud.game.entity.entities.";

	public Entities() {
		super(PACKAGE_NAME);
	}
	
	public void load() {
		for(String name: NAMES)
			this.load(name);
	}
}
