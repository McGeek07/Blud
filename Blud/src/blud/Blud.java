package blud;

import blud.core.Engine;
import blud.game.level.Level;
import blud.util.Version;

public class Blud {
	public static final Version
		VERSION = new Version("Blud", 0, 0, 2);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Engine.setScene(new Level());
		Engine.init();
	}
}
