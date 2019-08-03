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
		
		Sprites.load("CursorR", 15, 16);
		Sprites.load("CursorG", 15, 16);
		Sprites.load("CursorB", 15, 16);
		Sprites.load("CursorW", 15, 16);
		Sprites.load("Debug", 15, 16);
		Sprites.load("DebugTile", 15, 16);
		Sprites.load("DebugTrap", 15, 16);
		Sprites.load("DebugUnit", 15, 16);
		Sprites.load("DebugWall", 15, 16);
		Sprites.load("Player", 11, 12);
		Sprites.load("StoneWall", 15, 16);
		Sprites.load("StoneTile", 11, 12);
		Sprites.load("HellSprite", 64, 64);
	
		Engine.setScene(new Editor("level.txt"));
		Engine.init();
	}
}
