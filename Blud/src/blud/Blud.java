package blud;

import blud.core.Engine;
import blud.game.level.Level;
import blud.game.sprite.Sprite;
import blud.util.Version;

public class Blud {
	public static final Version
		VERSION = new Version("Blud", 0, 0, 6);
	
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
	
		Level level = new Level();
		
		level.add(new blud.game.wall.walls.Debug(0, 0));
		for(int i = 0; i < Level.LEVEL_W; i ++)
			for(int j = 0; j < Level.LEVEL_H; j ++)
				level.add(new blud.game.tile.tiles.Debug(i, j));
	
		Engine.setScene(level);
		Engine.init();
		

	}
}
