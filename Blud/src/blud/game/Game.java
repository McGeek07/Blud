package blud.game;

import blud.geom.Vector2f;

public class Game {
	public static final int
		NORTH 	= 0,
		EAST 	= 1,
		SOUTH 	= 2,	
		WEST 	= 3;
	public static final Vector2f[]
		LOCAL_DIRECTION = {
			new Vector2f( 0f,  1f),//NORTH
			new Vector2f( 1f,  0f),//EAST,
			new Vector2f( 0f, -1f),//SOUTH
			new Vector2f(-1f,  0f),//WEST
		},
		PIXEL_DIRECTION = {
			localToPixel(LOCAL_DIRECTION[0]),
			localToPixel(LOCAL_DIRECTION[1]),
			localToPixel(LOCAL_DIRECTION[2]),
			localToPixel(LOCAL_DIRECTION[3])
		};
	public static final int
		TILE_W = 8,
		TILE_H = 8,
		SPRITE_W = 15,
		SPRITE_H = 16;
	
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
