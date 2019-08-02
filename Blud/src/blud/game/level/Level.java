package blud.game.level;

import java.util.Random;

import blud.core.scene.Scene;
import blud.game.entity.Entity;
import blud.game.entity.entities.Entities;
import blud.game.sprite.Sprite;
import blud.game.tile.Tile;
import blud.game.tile.tiles.Tiles;
import blud.game.wall.walls.Debug;
import blud.game.wall.walls.Walls;
import blud.geom.Vector;
import blud.geom.Vector2f;

public class Level extends Scene {	
	public static final int
		LEVEL_W = 16,
		LEVEL_H = 16;
	public static final Entities
		ENTITIES = new Entities();
	public static final Tiles
		TILES = new Tiles();
	public static final Walls
		WALLS = new Walls();
	
	protected final Sprite.Group
		background = new Sprite.Group(
				Sprite.get("bg1"),
				Sprite.get("bg2")
				);
	protected final Grid[][]
		grid = new Grid
				[LEVEL_W]
				[LEVEL_H];	
	protected final Vector2f.Mutable
		camera = new Vector2f.Mutable();
	
	protected static final Random
		random = new Random();
	
	public Level() {
		background.setShadowTransparency(1f);
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++)
				grid[i][j] = new Grid(this, i, j);			
	}	
	
	public Grid at(Vector2f local) {
		return at(local.X(), local.Y());
	}
	
	public Grid at(float i, float j) {
		if(
				i > 0 && i < LEVEL_W &&
				j > 0 && j < LEVEL_H
				)
			return grid[(int)i][(int)j];
		return null;
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
		background.render(context);
		context.g2D.translate(
				context.canvas_w / 2 - camera.X(),
				context.canvas_h / 2 - camera.Y()
				);	
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++)
				grid[i][j].render(context);
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {
				grid[i][j].player_vision = 0f;
				grid[i][j].entity_vision = 0f;
			}
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {
				grid[i][j].update_entity_vision();
				grid[i][j].update_player_vision();
			}
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++)
				grid[i][j].update(context);				
	}
	
	public void save(String path) {
		
	}
	
	public void load(String path) {
		
	}
}
