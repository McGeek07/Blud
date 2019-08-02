package blud;

import blud.core.Engine;
import blud.game.level.Editor;
import blud.game.sprite.Sprite;
import blud.util.Version;

public class Blud {
	public static final Version
		VERSION = new Version("Blud", 0, 0, 4);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Sprite.load("bg1", 64, 64);
		Sprite.load("bg2", 64, 64);
		
		Sprite.load("cursor_r", 13, 14);
		Sprite.load("cursor_g", 13, 14);
		Sprite.load("cursor_b", 13, 14);
		Sprite.load("cursor_w", 13, 14);
		Sprite.load("debug", 11, 12);
		Sprite.load("wall", 11, 12);
		
		Engine.setScene(new Editor());
		Engine.init();
		

	}
}
