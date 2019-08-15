package blud.game.sound;

import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import blud.game.sound.sounds.Sounds;
import blud.util.Copyable;

public class Sound implements Copyable<Sound> {
	public final String
		name;
	protected ArrayList<Clip>
		clips = new ArrayList<>();
	
	public float
		volume = 1f;
	
	public Sound(
			Sound sound
			) {
		this(
				sound.name
				);
	}
	
	public Sound(
			String name
			) {
		this.name = name;
		clips.add(Sounds.createClip(this.name));
		clips.add(Sounds.createClip(this.name));
		setVolume(volume);
	}
	
	public void setVolume(float volume) {
		for(Clip clip: this.clips) {
			FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}
	}
	
	public void play(float volume) {
		this.setVolume(volume);
		this.play();
	}
	
	public void loop(float volume) {
		this.setVolume(volume);
		this.loop();
	}
	
	public void play() {
		for(Clip clip: clips)
			if(!clip.isRunning()) {
				clip.setFramePosition(0);
				clip.loop(0);
				return;
			}
		
		Clip clip = Sounds.createClip(this.name);
		FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
		clip.setFramePosition(0);
		clip.loop(0);
		
		clips.add(clip);		
	}
	
	public void loop() {
		for(Clip clip: clips)
			if(!clip.isRunning()) {
				clip.setFramePosition(0);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				return;
			}
		
		Clip clip = Sounds.createClip(this.name);
		FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
		clips.add(clip);
	}
	
	public void stop() {
		for(Clip clip: clips)
			clip.stop();
	}
	
	public boolean isPlaying() {
		for(Clip clip: clips)
			if(clip.isRunning())
				return true;
		return false;
	}

	@Override
	public Sound copy() {
		return new Sound(this);
	}
	
	public static class Group {
		public final ArrayList<Sound>
			sounds = new ArrayList<>();
		public int
			sound;
		
		public Group() {
			//do nothing
		}
		
		public Group(Sound... sounds) {
			this.add(sounds);
		}
		
		public void add(Sound... sounds) {
			for(Sound sound: sounds)
				this.sounds.add(sound);
		}
		
		public void del(Sound... sounds) {
			for(Sound sound: sounds)
				this.sounds.remove(sound);
		}
		
		public Sound get() {
			return this.sounds.get(this.sound);
		}
		
		public void play(float volume) {
			this.get().play(volume);
		}
		
		public void loop(float volume) {
			this.get().loop(volume);
		}
		
		public Sound set(int sound) {
			return this.sounds.get(this.sound = sound);
		}
		
		public void setVolume(float volume) {
			for(Sound sound: sounds)
				sound.setVolume(volume);
		}
		
		public void play(int sound, float volume) {
			this.set(sound).play(volume);
		}
		
		public void loop(int sound, float volume) {
			this.set(sound).loop(volume);
		}
		
		public void stop(int sound) {
			this.set(sound).stop();
		}
		
		public void stop() {
			for(Sound sound: sounds)
				sound.stop();
		}
	}
}
