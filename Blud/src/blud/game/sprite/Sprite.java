package blud.game.sprite;

import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.geom.Vector;
import blud.geom.Vector2f;
import blud.util.Copyable;

public class Sprite implements Renderable, Updateable, Copyable<Sprite> {
	private static final int
		STOP = 0,
		PLAY = 1,
		LOOP = 2;

	public final String
		name;
	public final int
		w,
		h;
	public final BufferedImage[]
		spriteFrames,
		whiteFrames,
		blackFrames;
	public float
		frame,
		speed,
		spriteTransparency,
		whiteTransparency = 1f,
		blackTransparency = 1f;
	public int
		mode;
	public boolean
		flip;
	public final Vector2f.Mutable
		pixel = new Vector2f.Mutable();
	
	public Sprite(Sprite sprite) {
		this(
				sprite.name,
				sprite.w,
				sprite.h,
				sprite.spriteFrames,
				sprite.whiteFrames,
				sprite.blackFrames
				);
	}
	
	public Sprite(
			String name,
			int	w,
			int h,
			BufferedImage[] spriteFrames,
			BufferedImage[] whiteFrames,
			BufferedImage[] blackFrames
			) {
		this.name = name;
		this.w = w;
		this.h = h;
		this.spriteFrames = spriteFrames;
		this.whiteFrames = whiteFrames;
		this.blackFrames = blackFrames;
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
		this.speed = speed;
		if(this.mode == STOP) {			
			this.frame = 0f;
			this.mode = PLAY;
		}
	}
	
	public void play(float speed, Vector pixel) {
		this.pixel.set(pixel);
		this.play(speed);
	}
	
	public void play(float speed, float x, float y) {
		this.pixel.set(x, y);
		this.play(speed);
	}
	
	public void loop(float speed) {
		this.speed = speed;
		if(this.mode == STOP) {
			this.frame = 0f;
			this.mode = LOOP;
		}
	}
	
	public void loop(float speed, Vector pixel) {
		this.pixel.set(pixel);
		this.play(speed);
	}
	
	public void loop(float speed, float x, float y) {
		this.pixel.set(x, y);
		this.play(speed);
	}
	
	public void stop() {
		this.mode = STOP;
	}
	
	public void stop(Vector pixel) {
		this.pixel.set(pixel);
		this.stop();
	}
	
	public void stop(float x, float y) {
		this.pixel.set(x, y);
		this.stop();
	}
	
	public void flip() {
		if(!flip)
			flip = true;
	}
	
	public void flop() {
		if( flip)
			flip = false;
	}

	@Override
	public void render(RenderContext context) {
		context = context.push();
		context.g2D.translate(
				pixel.x(),
				pixel.y()
				);
		if(flip)
			context.g2D.scale(-1, 1);
		context.g2D.translate(
				- w / 2,
				- h / 2
				);
		if(spriteTransparency < 1) {
			context.g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - spriteTransparency));
			context.g2D.drawImage(spriteFrames[(int)frame], null, 0,  0);
		}
		if(blackTransparency < 1) {
			context.g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - blackTransparency));
			context.g2D.drawImage(blackFrames[(int)frame], null, 0,  0);
		}
		if(whiteTransparency < 1) {
			context.g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - whiteTransparency));
			context.g2D.drawImage(whiteFrames[(int)frame], null, 0,  0);
		}
		context = context.pull();
	}
	
	@Override
	public void update(UpdateContext context) {
		if(mode > 0) {
			frame += speed * context.dt;
			switch(mode) {
				case PLAY: 
					if(frame <  0f) stop();
					if(frame >= spriteFrames.length) stop(); 
					break;
				case LOOP:
					if(frame <  0f) frame = spriteFrames.length - 1;
					if(frame >= spriteFrames.length) frame = 0f;
			}
		}		
	}	

	@Override
	public Sprite copy() {
		return new Sprite(this);
	}	
	
	public static class Group implements Renderable, Updateable {
		public final ArrayList<Sprite>
			sprites = new ArrayList<>();
		public int
			sprite;
		
		public Group() {
			//do nothing
		}
		
		public Group(Sprite... sprites) {
			this.add(sprites);
		}
		
		public void add(Sprite... sprites) {
			for(Sprite sprite: sprites)
				this.sprites.add(sprite);
		}
		
		public void del(Sprite... sprites) {
			for(Sprite sprite: sprites)
				this.sprites.remove(sprite);
		}
		
		public Sprite get() {
			return sprites.get(sprite);
		}
		
		public void setFrame(float frame) {
			get().frame = frame;
		}
		
		public void setPixel(Vector pixel) {
			get().pixel.set(pixel);
		}
		
		public void setPixel(float x, float y) {
			get().pixel.set(x, y);
		}
		
		public void setSpriteTransparency(float transparency) {
			for(Sprite sprite: sprites)
				sprite.setSpriteTransparency(transparency);
		}
		
		public void setWhiteTransparency(float transparency) {
			for(Sprite sprite: sprites)
				sprite.setWhiteTransparency(transparency);
		}
		
		public void setBlackTransparency(float transparency) {
			for(Sprite sprite: sprites)
					sprite.setBlackTransparency(transparency);
		}
		
		public void play(float speed) {
			this.get().play(speed);
		}
		
		public void play(float speed, Vector pixel) {
			this.get().play(speed, pixel);
		}
		
		public void play(float speed, float x, float y) {
			this.get().play(speed, x, y);
		}
		
		public void loop(float speed) {
			this.get().loop(speed);
		}
		
		public void loop(float speed, Vector pixel) {
			this.get().loop(speed, pixel);
		}
		
		public void loop(float speed, float x, float y) {
			this.get().loop(speed, x, y);
		}
		
		public void stop() {
			this.get().stop();
		}
		
		public void stop(Vector pixel) {
			this.get().stop(pixel);
		}
		
		public void stop(float x, float y) {
			this.get().stop(x, y);
		}
		
		public Sprite set(int sprite) {
			return sprites.get(this.sprite = sprite);
		}
		
		public void play(int sprite, float speed) {
			this.set(sprite).play(speed);
		}
		
		public void play(int sprite, float speed, Vector pixel) {
			this.set(sprite).play(speed, pixel);
		}
		
		public void play(int sprite, float speed, float x, float y) {
			this.set(sprite).play(speed, x, y);
		}
		
		public void loop(int sprite, float speed) {
			this.set(sprite).loop(speed);
		}
		
		public void loop(int sprite, float speed, Vector pixel) {
			this.set(sprite).loop(speed, pixel);
		}
		
		public void loop(int sprite, float speed, float x, float y) {
			this.set(sprite).loop(speed, x, y);
		}
		
		public void stop(int sprite) {
			this.set(sprite).stop();
		}
		
		public void stop(int sprite, Vector pixel) {
			this.set(sprite).stop(pixel);
		}
		
		public void stop(int sprite, float x, float y) {
			this.set(sprite).stop(x, y);
		}
		
		public void flip() {
			for(Sprite sprite: sprites)
				sprite.flip();
		}
		
		public void flop() {
			for(Sprite sprite: sprites)
				sprite.flop();
		}

		@Override
		public void render(RenderContext context) {
			if(sprites.size() > 0)
				get().render(context);
		}

		@Override
		public void update(UpdateContext context) {
			if(sprites.size() > 0)
				get().update(context);
		}
	}	
}
