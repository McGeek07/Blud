package blud.game.level;

import java.awt.Color;

import blud.core.input.Input;
import blud.core.scene.Scene;
import blud.game.tile.Tile;
import blud.geom.Vector2f;

public class Level extends Scene {
	public static final int
		LEVEL_W = 16,
		LEVEL_H = 16;
	private final Tile[][]
		T = new Tile
				[LEVEL_W]
				[LEVEL_H];	
	
	private Vector2f.Mutable
		focus = new Vector2f.Mutable();
	
	public Level() {
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++)
				T[i][j] = Tile.local(i, j);			
	}	
	
	@Override
	public void onRender(RenderContext context) {
		context.g2D.setColor(Color.WHITE);
		context.g2D.fillRect(
				0,
				0,
				context.canvas_w,
				context.canvas_h
				);
		if(Input.isKeyDn(Input.KEY_W))
			focus.setY(focus.Y() - 1);
		if(Input.isKeyDn(Input.KEY_A))
			focus.setX(focus.X() - 1);
		if(Input.isKeyDn(Input.KEY_S))
			focus.setY(focus.Y() + 1);
		if(Input.isKeyDn(Input.KEY_D))
			focus.setX(focus.X() + 1);
		context.g2D.translate(
				context.canvas_w / 2 - focus.X(),
				context.canvas_h / 2 - focus.Y()
				);
		
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {
				T[i][j].render(context);
			}
		
//		for(int i = 0; i < LEVEL_W; i ++)
//			for(int j = 0; j < LEVEL_H; j ++) {
//				context.g2D.setColor( new Color(
//								(float)i / LEVEL_W,
//								(float)j / LEVEL_H,
//								((i + j) & 1) == 0 ? 1f : 0f
//								));
//				for(float x = 0; x < Tile.TILE_W; x ++)
//					for(float y = 0; y < Tile.TILE_H; y ++) {
//						float
//							tx = i + (x / Tile.TILE_W),
//							ty = j + (y / Tile.TILE_H);
//						Vector2f
//							pixel = Tile.localToPixel(
//									tx,
//									ty
//									);
//						context.g2D.fillRect(pixel.x(), pixel.y(), 1, 1);
//					}				
//			}
	}
}
