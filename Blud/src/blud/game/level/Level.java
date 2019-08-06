package blud.game.level;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;

import blud.core.scene.Scene;
import blud.game.Game;
import blud.game.level.node.Node;
import blud.game.level.tile.Tile;
import blud.game.level.tile.tiles.Tiles;
import blud.game.level.unit.Unit;
import blud.game.level.unit.units.Units;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector2f;
import blud.util.Util;

public class Level extends Scene {
	public static final int
		LEVEL_W = 16,
		LEVEL_H = 16;	
	public Sprite
		bg;
	public final Node[][]
		grid = new Node
				[LEVEL_W]
				[LEVEL_H];	
	public final Vector2f.Mutable
		camera = new Vector2f.Mutable();
	public float
		playerVisionFloor =  0f,
		entityVisionFloor = .25f;
	
	public Level() {		
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++)
				grid[i][j] = new Node(this, i, j);		
	}	
	
	public Node at(Vector2f local) {
		return at(local.X(), local.Y());
	}
	
	public Node at(float i, float j) {
		if(
				i >= 0 && i < LEVEL_W &&
				j >= 0 && j < LEVEL_H
				)
			return grid[(int)i][(int)j];
		return null;
	}
	
	@Override
	public void onRender(RenderContext context) {
		context.g2D.translate(
				context.canvas_w / 2,
				context.canvas_h / 2
				);
		if(bg != null)
			bg.render(context);
		context.g2D.translate(
				- camera.x(),
				- camera.y()
				);
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) 
				grid[i][j].setBlackTransparency(grid[i][j].playerVision > 0? grid[i][j].entityVision : 0f);
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) 
				if(grid[i][j].tile != null)
					grid[i][j].tile.render(context);
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) 
				if(grid[i][j].unit != null)
					grid[i][j].unit.render(context);
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		if(bg != null)
			bg.update(context);
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {
				grid[i][j].setSpriteTransparency(0f);
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
			if(bg != null)
				out.println("bg:" + bg.name + "," + bg.speed);
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
				bg = Sprites.get(name);
				bg.loop(Util.stringToFloat(speed));
			}
			if(line.startsWith("grid:")) {
				String[] 
					temp = line.substring("grid:".length()).split("\\&");
				String
					v 	= temp.length > 0 ? temp[0].trim() : "",
					tile 	= temp.length > 1 ? temp[1].trim() : "",
					unit 	= temp.length > 2 ? temp[2].trim() : "";
				Vector2f 
					local = Vector2f.parseVector2f(v),
					pixel = Game.localToPixel(local);
				Node node = at(local);
				if(node != null) {
					if(!tile.isEmpty()) {
						Tile t = Tiles.load(tile);
						t.pixel.set(pixel);
						node.setTile(t);
					}
					if(!unit.isEmpty()) {
						Unit u = Units.load(unit);
						u.pixel.set(pixel);
						node.setUnit(u);
					}
				}
			}			
		}
	}
}
