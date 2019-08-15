package blud.game.menu.menus;

import blud.game.menu.Menu;
import blud.game.sound.Sound;
import blud.game.sound.sounds.Sounds;

public class Menus {
	public static final Sound
		TRACK0 = Sounds.get("Track0");
	public static final Menu
		SPLASH = new Splash(),
		MAIN = new Main(),
		INFO = new Info(),
		CREDITS = new Credits();
}
