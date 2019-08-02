package blud;

import blud.core.Engine;
import blud.game.level.Level;
import blud.game.sprite.Sprite;
import blud.util.Version;

public class Blud {
	public static final Version
		VERSION = new Version("Blud", 0, 0, 3);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Sprite.load("select_r", 11, 12);
		Sprite.load("select_g", 11, 12);
		Sprite.load("select_b", 11, 12);
		Sprite.load("Debug", 11, 12);
		Sprite.load("Tile", 11, 12);
		Sprite.load("Wall", 11, 12);
		
		Engine.setScene(new Level());
		Engine.init();
	}
}
