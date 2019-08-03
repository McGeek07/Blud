package blud.game.level.unit.units;

import java.util.List;

import blud.game.level.unit.Unit;
import blud.util.Loader;

public class Units extends Loader<Unit> {
	public static final String
		PACKAGE_NAME = "blud.game.level.unit.units.";
	protected final static Units
		INSTANCE = new Units();
	
	protected Units() {
		//do nothing
	}
	
	public static Unit load(String class_name) {
		return INSTANCE.load(PACKAGE_NAME, class_name);
	}
	
	public static List<Unit> load(List<Unit> list) {
		return INSTANCE.load(PACKAGE_NAME, list);
	}	
}
