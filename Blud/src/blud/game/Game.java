package blud.game;

import blud.geom.Vector2f;

public class Game {
	public static final int
		TILE_W = 6,
		TILE_H = 6;
	
	public static final Vector2f localToPixel(Vector2f local) {
		return localToPixel(local.X(), local.Y());
	}
	
	public static final Vector2f localToPixel(float i, float j) {
		i *= TILE_W;
		j *= TILE_H;
		return new Vector2f(
				(i - j)    ,
				(i + j) / 2
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
