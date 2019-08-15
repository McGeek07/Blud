package blud.game.level.levels;

import java.util.ArrayList;
import java.util.LinkedList;

import blud.game.level.Level;
import blud.util.Util;

public class Levels {
	public static final String
		INDEX = "index",
		SAVE  = "blud.sav";
	private static final ArrayList<Level>
		LEVELS = new ArrayList<Level>();
	public static int
		level;
	
	public static void load() {
		LinkedList<String> list = new LinkedList<String>();
		Util.parseFromResource(Levels.class, INDEX, list);
		for(String line: list)
			if(!line.startsWith("//")) {
				String name = line.trim();
				Level level = load(name);
				LEVELS.add(level);
			}
		list.clear();
		Util.parseFromFile(SAVE, list);
		if(list.size() > 0)
			level = Util.stringToInt(list.get(0));
	}
	
	public static void save() {
		Util.printToFile(SAVE, false, level);
	}
	
	public static Level get() {
		Level level = LEVELS.get(Levels.level);
		level.reset();
		return level;
	}
	
	public static Level next() {
		Levels.level ++;
		if(Levels.level >= LEVELS.size())
			Levels.level = 0;
		Level level = LEVELS.get(Levels.level);
		level.reset();
		return level;
	}
	
	public static Level prev() {
		Levels.level --;
		if(Levels.level >= LEVELS.size())
			Levels.level = 0;
		Level level = LEVELS.get(Levels.level);
		level.reset();
		return level;
	}
	
	public static Level load(String name) {
		Level level = new Level(name);
		level.loadFromResource(Levels.class, name);
		return level;
	}
}
