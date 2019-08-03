package blud;

import blud.core.Engine;
import blud.game.entity.entities.Player;
import blud.game.level.Editor;
import blud.game.level.Level;
import blud.game.sprite.Sprite;
import blud.game.tile.Tile;
import blud.util.Version;

public class Blud {
	public static final Version
		VERSION = new Version("Blud", 0, 0, 5);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Sprite.load("bg1", 64, 64);
		Sprite.load("bg2", 64, 64);
		
		Sprite.load("Player", 11, 12);
		Sprite.load("cursor_r", 11, 12);
		Sprite.load("cursor_g", 11, 12);
		Sprite.load("cursor_b", 11, 12);
		Sprite.load("cursor_w", 11, 12);
		Sprite.load("debug", 11, 12);
		Sprite.load("tile", 11, 12);
		Sprite.load("wall", 11, 12);
	
//		Player player1 = new Player();
//		player1.setLocal(0,0);
		Level level1 = new Level();
//		level1.load("LevelSave.txt");
//		level1.add(player1);
//	
		Engine.setScene(level1);
//		Engine.setScene(new Editor());
		Engine.init();
		

	}
}
