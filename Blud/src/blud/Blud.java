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

		Sprites.load("TileCursor1", 15, 16);
		Sprites.load("TileCursor2", 15, 16);
		Sprites.load("UnitCursor1", 15, 16);
		Sprites.load("UnitCursor2", 15, 16);
		Sprites.load("Debug", 15, 16);
		Sprites.load("DebugTile", 15, 16);
		Sprites.load("DebugTrap", 15, 16);
		Sprites.load("DebugUnit", 15, 16);
		Sprites.load("DebugWall", 15, 16);
		Sprites.load("Player", 15, 16);
		Sprites.load("StoneWall", 15, 16);
		Sprites.load("StoneTile", 15, 16);
		Sprites.load("HellSprite", 64, 64);
		Sprites.load("DirectionSprites", 15, 16);
		Sprites.load("Brazier", 15, 16);
		Sprites.load("TorchWall", 15, 16);
		Sprites.load("CastleWall", 15, 16);
		Sprites.load("CastleTile", 15, 16);
		Sprites.load("CastleTorch", 15, 16);
		Sprites.load("Heart", 9, 9);
		
		Engine.setScene(new Editor("level.txt"));
		Engine.init();
	}
}
