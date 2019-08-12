package blud;

import blud.core.Engine;
import blud.game.level.Editor;
import blud.game.sound.sounds.Sounds;
import blud.game.sprite.sprites.Sprites;
import blud.util.Version;

public class Blud {
	public static final Version
		VERSION = new Version("Blud", 0, 0, 8);
	
	public static void main(String[] args) {
		System.out.println(VERSION);		
		
		Sprites.load();
		Sounds .load();
		
		Engine.setScene(new Editor("Level3-1"));
		Engine.init();
	}
}
