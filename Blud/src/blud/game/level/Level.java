package blud.game.level;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;

import blud.core.scene.Scene;
import blud.game.level.grid.Grid;
import blud.game.level.tile.Tile;
import blud.game.level.tile.tiles.Tiles;
import blud.game.level.unit.Unit;
import blud.game.level.unit.units.Units;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector;
import blud.geom.Vector2f;
import blud.util.Util;

public class Level extends Scene {	
	public static final int
		LEVEL_W = 16,
		LEVEL_H = 16;
	
	protected Sprite
		background;
	public final Grid[][]
		grid = new Grid
				[LEVEL_W]
				[LEVEL_H];	
	public final Vector2f.Mutable
		camera = new Vector2f.Mutable();
	protected float
		playerVisionFloor =  0f,
		entityVisionFloor = .25f;
	
	public Level() {		
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++)
				grid[i][j] = new Grid(this, i, j);		
	}	
	
	public Grid at(Vector2f local) {
		return at(local.X(), local.Y());
	}
	
	public Grid at(float i, float j) {
		if(
				i >= 0 && i < LEVEL_W &&
				j >= 0 && j < LEVEL_H
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
	
	public void add(Unit entity) {
		int
			i = entity.local.x(),
			j = entity.local.y();
		if(grid[i][j].unit == null)
			grid[i][j].unit = entity;
	}
	
	public void del(Unit entity) {
		int
			i = entity.local.x(),
			j = entity.local.y();
		if(grid[i][j].unit == entity)
			grid[i][j].unit = null;
	}
	
	public void delEntity(Vector local) {
		delEntity(local.x(), local.y());
	}
	
	public void delEntity(int i , int j) {
		grid[i][j].unit = null;
	}
	
	@Override
	public void onRender(RenderContext context) {
		if(background != null)
			background.render(context);
		context.g2D.translate(
				context.canvas_w / 2 - camera.X(),
				context.canvas_h / 2 - camera.Y()
				);	
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {	
				grid[i][j].setShadowTransparency(grid[i][j].playerVision > 0? grid[i][j].entityVision : 0f);
				grid[i][j].render(context);		
			}
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		if(background != null)
			background.update(context);
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {
				grid[i][j].playerVision = playerVisionFloor;
				grid[i][j].entityVision = entityVisionFloor;
			}
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {
				grid[i][j].updatePlayerVision();
				grid[i][j].updateEntityVision();
			}
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++)
				grid[i][j].update(context);				
	}
	
	public void save(String path) {
		save(new File(path));
	}
	
	public void save(File file  ) {
		try(PrintWriter out = Util.createPrintWriter(file, false)) {
			if(background != null)
				out.println("bg:" + background.name + "," + background.speed);
			for(int i = 0; i < LEVEL_W; i ++)
				for(int j = 0; j < LEVEL_H ; j ++)
					if(grid[i][j].tile != null || grid[i][j].unit != null)
						out.println(
								"grid:" + grid[i][j].local 
								+ "&" + (grid[i][j].tile != null ? grid[i][j].tile.getClassName() : "") 
								+ "&" + (grid[i][j].unit != null ? grid[i][j].unit.getClassName() : "")
								);
		}
	}
	
	public void load(String path) {
		load(new File(path));
	}
	
	public void load(File file  ) {
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {
				grid[i][j].tile = null;
				grid[i][j].unit = null;
			}
		for(String line: Util.parseFromFile(file, new LinkedList<String>())) {
			line = line.trim();
			if(line.startsWith("bg:")) {
				String[] temp = line.substring("bg:".length()).split("\\,");
				String
					name  = temp.length > 0 ? temp[0] : "",
					speed = temp.length > 1 ? temp[1] : "";
				background = Sprites.get(name);
				background.loop(Util.stringToFloat(speed));
			}
			if(line.startsWith("grid:")) {
				String[] 
					temp = line.substring("grid:".length()).split("\\&");
				String
					local 	= temp.length > 0 ? temp[0].trim() : "",
					tile 	= temp.length > 1 ? temp[1].trim() : "",
					unit 	= temp.length > 2 ? temp[2].trim() : "";
				Grid grid = at(Vector2f.parseVector2f(local));
				if(grid != null) {
					if(!tile.isEmpty()) {
						Tile t = Tiles.load(tile);
						t.setLocal(grid.local);
						grid.setTile(t);
					}
					if(!unit.isEmpty()) {
						Unit u = Units.load(unit);
						u.setLocal(grid.local);
						grid.setUnit(u);
					}
				}
			}			
		}
	}
}
