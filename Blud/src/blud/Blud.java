package blud;

import blud.core.Engine;
import blud.game.level.Editor;
import blud.game.sprite.sprites.Sprites;
import blud.util.Version;

public class Blud {
	public static final Version
		VERSION = new Version("Blud", 0, 0, 7);
	
	public static void main(String[] args) {
		System.out.println(VERSION);		
		
		Sprites.load("cursor_r", 11, 12);
		Sprites.load("cursor_g", 11, 12);
		Sprites.load("cursor_b", 11, 12);
		Sprites.load("cursor_w", 11, 12);
		Sprites.load("debug", 11, 12);
		Sprites.load("DebugTile", 11, 12);
		Sprites.load("DebugTrap", 11, 12);
		Sprites.load("DebugUnit", 11, 12);
		Sprites.load("DebugWall", 11, 12);
		Sprites.load("Player", 11, 12);
		Sprites.load("StoneWall", 11, 12);
		Sprites.load("StoneTile", 11, 12);
	
		Engine.setScene(new Editor("level.txt"));
		Engine.init();
	}
}
