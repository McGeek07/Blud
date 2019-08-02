package blud.game.level;

import java.awt.Color;
import java.util.Random;

import blud.core.input.Input;
import blud.core.scene.Scene;
import blud.game.entity.Entity;
import blud.game.tile.Tile;
import blud.geom.Vector;
import blud.geom.Vector2f;

public class Level extends Scene {
	public static final int
		LEVEL_W = 16,
		LEVEL_H = 16;
	protected final Grid[][]
		grid = new Grid
				[LEVEL_W]
				[LEVEL_H];
	
	private Vector2f.Mutable
		focus = new Vector2f.Mutable();
	
	private Random
		random = new Random();
	
	public Level() {
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {
				grid[i][j] = new Grid(this, i, j);
				grid[i][j].tile = new Tile.Debug(i, j);
				grid[i][j].tile.setShadowTransparency(1f);
			}
	}	
	
	public void add(Tile tile) {
		int
			i = tile.local.x(),
			j = tile.local.y();
		if(grid[i][j].tile == null)
			grid[i][j].tile = tile;
	}
	
	public void del(Tile tile) {
		int
			i = tile.local.x(),
			j = tile.local.y();
		if(grid[i][j].tile == tile)
			grid[i][j].tile = null;
	}
	
	public void delTile(Vector local) {
		delTile(local.x(), local.y());
	}
	
	public void delTile(int i, int j) {
		grid[i][j].tile = null;
	}
	
	public void add(Entity entity) {
		int
			i = entity.local.x(),
			j = entity.local.y();
		if(grid[i][j].entity == null)
			grid[i][j].entity = entity;
	}
	
	public void del(Entity entity) {
		int
			i = entity.local.x(),
			j = entity.local.y();
		if(grid[i][j].entity == entity)
			grid[i][j].entity = null;
	}
	
	public void delEntity(Vector local) {
		delEntity(local.x(), local.y());
	}
	
	public void delEntity(int i , int j) {
		grid[i][j].entity = null;
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
				grid[i][j].render(context);
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
	
	@Override
	public void onUpdate(UpdateContext context) {
		
	}
}
