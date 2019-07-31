package blud;

import blud.core.Engine;
import blud.util.Version;

public class Blud {
	public static final Version
		VERSION = new Version("Blud", 0, 0, 1);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Engine.init();
	}
}
