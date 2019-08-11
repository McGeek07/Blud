package blud.game.level;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;

import blud.core.scene.Scene;
import blud.game.Game;
import blud.game.level.node.Node;
import blud.game.level.tile.Tile;
import blud.game.level.tile.tiles.Tiles;
import blud.game.level.unit.Enemy;
import blud.game.level.unit.Unit;
import blud.game.level.unit.units.Units;
import blud.game.menu.menus.Menus;
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
		lightFloor = .4f;
	public boolean
		updateLighting = true,
		updatePlayerVision = true,
		updateEntityVision = true;	
	public final Sprite
		vision = Sprites.get("Vision"),
		facing = Sprites.get("Facing");
	
	protected File
		file;
	
	public Level(String path) {
		this(new File(path));
	}
	
	public Level(File file  ) {
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++)
				grid[i][j] = new Node(this, i, j);
		this.load(this.file = file);
		vision.setSpriteTransparency(.6f);
		vision.loop(3f);
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
			for(int j = 0; j < LEVEL_H; j ++) {
				if(grid[i][j].tile != null)
					grid[i][j].tile.render(context);
//				if(grid[i][j].lightLevel > 0 && grid[i][j].playerVision && grid[i][j].entityVision) {
//					float transparency = grid[i][j].lightLevel * (1f - lightFloor) + lightFloor;
//					danger.setBlackTransparency(grid[i][j].playerVision ? transparency: 0f);
//					vision.pixel.set(Game.localToPixel(i, j));
//					vision.render(context);
//				}
			}									
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {
				if(grid[i][j].unit instanceof Enemy) {
					facing.frame = grid[i][j].unit.facing;
					facing.pixel.set(grid[i][j].pixel);
					facing.render(context);
				}					
				if(grid[i][j].unit != null)
					grid[i][j].unit.render(context);
			}
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		if(bg != null)
			bg.update(context);
		if(updateLighting || updatePlayerVision || updateEntityVision) {
			for(int i = 0; i < LEVEL_W; i ++)
				for(int j = 0; j < LEVEL_H; j ++) {
					if(updateLighting) grid[i][j].lightLevel = 0f;
					if(updatePlayerVision) grid[i][j].playerVision = false;
					if(updateEntityVision) grid[i][j].entityVision = false;
				}
			for(int i = 0; i < LEVEL_W; i ++)
				for(int j = 0; j < LEVEL_H; j ++) {
					if(updateLighting) grid[i][j].castLight();
					if(updatePlayerVision) grid[i][j].castPlayerVision();
					if(updateEntityVision) grid[i][j].castEntityVision();
				}
			for(int i = 0; i < LEVEL_W; i ++)
				for(int j = 0; j < LEVEL_H; j ++) {
					float transparency = grid[i][j].lightLevel * (1f - lightFloor) + lightFloor ;
					grid[i][j].setBlackTransparency(grid[i][j].playerVision ? transparency : 0f);
				}
			updateLighting = false;
			updatePlayerVision = false;
			updateEntityVision = false;
		}	
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++)
				grid[i][j].update(context);	
		vision.update(context);
	}
	
	@Override
	public void onAttach() {
		Menus.TRACK0.stop();
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
								+ "&" + (grid[i][j].tile != null ? grid[i][j].tile.getClassName()                                : "") 
								+ "&" + (grid[i][j].unit != null ? grid[i][j].unit.getClassName() + ":" + grid[i][j].unit.facing : "")
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
					tile 	= temp.length > 1 ? temp[1].trim() : "";
				int i = temp.length > 2 ? temp[2].indexOf(":") : 0;
				String
					unit 	= temp.length > 2 ? temp[2].substring(0, i).trim() : "",
					face    = temp.length > 2 ? temp[2].substring(++ i).trim() : "";
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
						
						u.facing = Util.stringToInt(face);
						u.state = -1;
						u.idle();
					}
				}
			}			
		}
	}
}
