package blud.game;

import blud.geom.Vector2f;

public class Game {
	public static final int
		NORTH 	= 0,
		NORTH_EAST = 1,
		EAST 	= 2,
		SOUTH_EAST = 3,
		SOUTH 	= 4,
		SOUTH_WEST = 5,		
		WEST 	= 6,
		NORTH_WEST = 7;
	public static final Vector2f[]
		DIRECTION = {
			new Vector2f( 0f,  1f),//NORTH
			new Vector2f( 1f,  1f),//NORTH EAST
			new Vector2f( 1f,  0f),//EAST,
			new Vector2f( 1f, -1f),//SOUTH_EAST
			new Vector2f( 0f, -1f),//SOUTH
			new Vector2f(-1f, -1f),//SOUTH_WEST
			new Vector2f(-1f,  0f),//WEST
			new Vector2f(-1f,  1f) //NORTH_WEST
		};		
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
