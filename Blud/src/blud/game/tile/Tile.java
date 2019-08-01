package blud.game.tile;

import java.util.Random;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.game.sprite.Sprite;
import blud.game.sprite.Sprites;
import blud.geom.Vector;
import blud.geom.Vector2f;

public class Tile implements Renderable, Updateable {
	public static final int
		TILE_W = 6,
		TILE_H = 6;	
	protected Sprite
		tile_sprite,
		wall_sprite;
	protected Vector2f.Mutable
		local = new Vector2f.Mutable(),
		pixel = new Vector2f.Mutable();	
	
	private static final Random
		random = new Random();
	
	protected Tile() {
		this.tile_sprite = Sprites.getTileSprite("Tile");
		if(random.nextBoolean())
			this.wall_sprite = Sprites.getTileSprite("Wall");
	}
	
	public Tile setLocal(Vector v) {
		return this.setLocal(v.X(), v.Y());
	}
	
	public Tile setLocal(float x, float y) {
		this.local.set(x, y);
		this.pixel.set(localToPixel(x, y));
		return this;
	}
	
	public Tile setPixel(Vector v) {
		return this.setPixel(v.X(), v.Y());
	}
	
	public Tile setPixel(float x, float y) {
		this.pixel.set(x, y);
		this.local.set(pixelToLocal(x, y));
		return this;
	}
	
	public Vector2f local() {
		return this.local;
	}
	
	public Vector2f pixel() {
		return this.pixel;
	}

	@Override
	public void render(RenderContext context) {
		context = context.push();
		context.g2D.translate(
				pixel.x() - Sprites.TILE_SPRITE_W / 2,
				pixel.y() - Sprites.TILE_SPRITE_H / 2
				);
		if(this.tile_sprite != null)
			this.tile_sprite.render(context);
		if(this.wall_sprite != null)
			this.wall_sprite.render(context);
		context = context.pull();
	}

	@Override
	public void update(UpdateContext context) {
		if(this.tile_sprite != null)
			this.tile_sprite.update(context);
		if(this.wall_sprite != null)
			this.wall_sprite.update(context);
	}
	
	public static Tile local(float x, float y) {
		return new Tile().setLocal(x, y);
	}
	
	public static Tile pixel(float x, float y) {
		return new Tile().setPixel(x, y);
	}	
	
	public static final Vector2f localToPixel(Vector2f local) {
		return localToPixel(local.X(), local.Y());
	}
	
	public static final Vector2f localToPixel(float x, float y) {
		x *= TILE_W;
		y *= TILE_H;
		return new Vector2f(
				(x - y)    ,
				(x + y) / 2
				);
	}
	
	public static final Vector2f pixelToLocal(Vector2f pixel) {
		return pixelToLocal(pixel.X(), pixel.Y());
	}
	
	public static final Vector2f pixelToLocal(float x, float y) {
		x = (2 * y + x) / 2;
		y = (2 * y - x) / 2;
		return new Vector2f(
				x / TILE_W,
				y / TILE_H
				);
	}
}
