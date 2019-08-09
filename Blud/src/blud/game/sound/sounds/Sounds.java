package blud.game.sound.sounds;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import blud.game.sound.Sound;
import blud.util.Util;

public class Sounds {
	public static final String
		INDEX = "index";
	protected final static HashMap<String, Sound>
		SOUNDS = new HashMap<>();
	
	public static void load() {
		LinkedList<String> list = new LinkedList<String>();
		Util.parseFromResource(Sounds.class, INDEX, list);
		for(String line: list)
			if(!line.startsWith("//")) {
				String name = line.trim();
				load(name);
			}
	}
	
	public static Sound load(String name) {
		Clip clip = createClip(name);
		Sound sound = new Sound(
				name,
				clip
				);
		SOUNDS.put(name, sound);
		return sound;
	}
	
	public static Clip createClip(String name) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sounds.class.getResource(name + ".wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			
			return clip;
		} catch (UnsupportedAudioFileException uafe) {
			uafe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch (LineUnavailableException lue) {
			lue.printStackTrace();
		}
		return null;
	}
	
	public static Sound get(String name) {
		Sound sound = SOUNDS.get(name);
		return sound != null ?
				sound.copy() :
				null;
	}
}
