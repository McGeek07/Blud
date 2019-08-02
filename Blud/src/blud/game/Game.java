package blud.game;

import blud.geom.Vector2f;

public class Game {
	public static final int
		TILE_W = 6,
		TILE_H = 6,
		SPRITE_W = 11,
		SPRITE_H = 12;
	
	public static final Vector2f localToPixel(Vector2f local) {
		return localToPixel(local.X(), local.Y());
	}
	
	public static final Vector2f localToPixel(float i, float j) {
		float 
			x = i * TILE_W,
			y = j * TILE_H;
		return new Vector2f(
				(x - y)    ,
				(x + y) / 2
				);
	}
	
	public static final Vector2f pixelToLocal(Vector2f pixel) {
		return pixelToLocal(pixel.X(), pixel.Y());
	}
	
	public static final Vector2f pixelToLocal(float x, float y) {
		float
			i = (2 * y + x) / 2,
			j = (2 * y - x) / 2;
		return new Vector2f(
				i / TILE_W,
				j / TILE_H
				);
	}
}
