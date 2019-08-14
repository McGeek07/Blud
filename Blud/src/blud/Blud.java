package blud;

import blud.core.Engine;
import blud.game.level.Editor;
import blud.game.menu.menus.Menus;
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
		//Levels .load();
		
//		Level level = new Level("Sewer");
//		for(int i = 0; i < Level.LEVEL_W; i ++)
//			for(int j = 0; j < Level.LEVEL_H; j ++)
//				level.grid[i][j].setTile(new SewerStoneTile());
//		level.saveToFile("Sewer");
		
		Engine.setScene(Menus.MAIN);
		Engine.init();
	}
}
