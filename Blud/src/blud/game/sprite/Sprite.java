package blud.game.sprite;

import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.util.Copyable;

public class Sprite implements Renderable, Updateable, Copyable<Sprite> {
	private static final int
		STOP = 0,
		PLAY = 1,
		LOOP = 2;
	protected BufferedImage[]
		spriteFrames,
		whiteFrames,
		blackFrames;
	public String
		name;
	public float
		frame,
		speed,
		spriteTransparency,
		whiteTransparency = 1f,
		blackTransparency = 1f;
	protected int
		mode;
	
	public Sprite(Sprite sprite) {
		this(
				sprite.name,
				sprite.spriteFrames,
				sprite.whiteFrames,
				sprite.blackFrames
				);
	}
	
	public Sprite(
			String name,
			BufferedImage[] spriteFrames,
			BufferedImage[] whiteFrames,
			BufferedImage[] blackFrames
			) {
		this.name = name;
		this.spriteFrames = spriteFrames;
		this.whiteFrames = whiteFrames;
		this.blackFrames = blackFrames;
	}
	
	public void nextFrame() {
		frame ++;
		if(frame >= spriteFrames.length)
			frame = 0f;
	}
	
	public void prevFrame() {
		frame --;
		if(frame < 0)
			frame = spriteFrames.length - 1;
	}
	
	public void setFrame(float frame) {
		this.frame = frame;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setSpriteTransparency(float transparency) {
		this.spriteTransparency = transparency;
	}
	
	public void setWhiteTransparency(float transparency) {
		this.whiteTransparency = transparency;
	}
	
	public void setBlackTransparency(float transparency) {
		this.blackTransparency = transparency;
	}
	
	public void play(float speed) {
		this.setSpeed(speed);
		this.setFrame(0f);
		this.play();
	}
	
	public void play() {
		this.mode = PLAY;
	}
	
	public void loop(float speed) {
		this.setSpeed(speed);
		this.setFrame(0f);
		this.mode = LOOP;
	}
	
	public void loop() {
		this.mode = LOOP;
	}
	
	public void stop() {
		this.setFrame(0f);
		this.mode = STOP;
	}

	@Override
	public void render(RenderContext context) {
		context = context.push();
		if(spriteTransparency < 1) {
			if(spriteTransparency > 0)
				context.g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - spriteTransparency));
			context.g2D.drawImage(spriteFrames[(int)frame], null, 0,  0);
		}
		if(whiteTransparency < 1) {
			if(whiteTransparency > 0)
				context.g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - whiteTransparency));
			context.g2D.drawImage(whiteFrames[(int)frame], null, 0,  0);
		}
		if(blackTransparency < 1) {
			if(blackTransparency > 0)
				context.g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - blackTransparency));
			context.g2D.drawImage(blackFrames[(int)frame], null, 0,  0);
		}
		context = context.pull();
	}
	
	@Override
	public void update(UpdateContext context) {
		if(mode > 0) {
			frame += speed * context.dt;
			switch(mode) {
				case PLAY: 
					if(frame <  0f           ) stop();
					if(frame >= spriteFrames.length) stop(); 
					break;
				case LOOP:
					if(frame <  0f           ) frame = spriteFrames.length - 1;
					if(frame >= spriteFrames.length) frame = 0f               ;
			}
		}		
	}	

	@Override
	public Sprite copy() {
		return new Sprite(this);
	}	
	
	public static class Group implements Renderable, Updateable {
		protected Sprite[]
			sprites;
		protected int
			sprite;
		
		public Group(Sprite... sprites) {
			this.sprites = sprites;
		}
		
		public Sprite getSprite() {
			return sprites[sprite];
		}
		
		public void nextFrame() {
			this.getSprite().nextFrame();
		}
		
		public void prevFrame() {
			this.getSprite().prevFrame();
		}
		
		public void setFrame(float frame) {
			this.getSprite().setFrame(frame);
		}
		
		public void setSpeed(float speed) {
			this.getSprite().setSpeed(speed);
		}
		
		public void setSpriteTransparency(float transparency) {
			for(int i = 0; i < sprites.length; i ++)
				sprites[i].setSpriteTransparency(transparency);
		}
		
		public void setWhiteTransparency(float transparency) {
			for(int i = 0; i < sprites.length; i ++)
				sprites[i].setWhiteTransparency(transparency);
		}
		
		public void setBlackTransparency(float transparency) {
			for(int i = 0; i < sprites.length; i ++)
				sprites[i].setBlackTransparency(transparency);
		}
		
		public void play() {
			this.getSprite().play();
		}
		
		public void loop() {
			this.getSprite().loop();
		}
		
		public void stop() {
			this.getSprite().stop();
		}
		
		public Sprite nextSprite() {
			sprite ++;
			if(sprite >= sprites.length)
				sprite = 0;
			return sprites[sprite];
		}
		
		public Sprite prevSprite() {
			sprite --;
			if(sprite < 0)
				sprite = sprites.length - 1;
			return sprites[sprite];
		}
		
		public Sprite setSprite(int sprite) {
			return sprites[this.sprite = sprite];
		}
		
		public void play(int sprite, float speed) {
			this.setSprite(sprite).play(speed);
		}
		
		public void loop(int sprite, float speed) {
			this.setSprite(sprite).loop(speed);
		}
		
		public void stop(int sprite) {
			this.setSprite(sprite).stop();
		}

		@Override
		public void render(RenderContext context) {
			sprites[sprite].render(context);
		}

		@Override
		public void update(UpdateContext context) {
			sprites[sprite].update(context);
		}
	}	
}
