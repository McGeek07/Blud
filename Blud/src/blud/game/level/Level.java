package blud.game.level;

import java.awt.Color;
import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import blud.core.Engine;
import blud.core.input.Input;
import blud.core.scene.Scene;
import blud.game.Game;
import blud.game.level.levels.Levels;
import blud.game.level.node.Node;
import blud.game.level.tile.Tile;
import blud.game.level.tile.tiles.Tiles;
import blud.game.level.unit.Unit;
import blud.game.level.unit.units.Units;
import blud.game.menu.component.Component;
import blud.game.menu.menus.Menus;
import blud.game.menu.menus.Pause;
import blud.game.sound.Sound;
import blud.game.sound.sounds.Sounds;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector2f;
import blud.util.Util;

public class Level extends Scene {
	public static final int
		LEVEL_W = 32,
		LEVEL_H = 32;	
	public Sprite
		bg;
	public Sound
		track;
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
		facing = Sprites.get("Facing");	
	
	protected String
		name;
	protected final LinkedList<String>
		data  = new LinkedList<String>();
	
	public Level(String name) {
		this.name = name;
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
	
	protected float
		transparency;	
	@Override
	public void onRender(RenderContext context) {
		context = context.push();
			context.g2D.setColor(Color.BLACK);
			context.g2D.fillRect(
					0,
					0,
					context.canvas_w,
					context.canvas_h
					);
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
				}									
			for(int i = 0; i < LEVEL_W; i ++)
				for(int j = 0; j < LEVEL_H; j ++) {									
					if(grid[i][j].unit != null) {
						if(grid[i][j].unit.drawFacing && grid[i][j].playerVision) {
							facing.frame = grid[i][j].unit.facing;
							facing.pixel.set(grid[i][j].pixel);
							facing.render(context);
						}
						grid[i][j].unit.render(context);
					}
				}
			context = context.pull();
			
			if(transparency <= 1f) {			
				Component.drawText(context, name, 0, 1, 57, 62, 62, Component.WHITE, transparency);
				transparency += .01f;
			}
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		if(Input.isKeyDnAction(Input.KEY_ESCAPE)) {
			Engine.setScene(new Pause(this));
			return;
		}
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
	}
	
	@Override
	public void onExit() {
		Levels.save();
	}
	
	@Override
	public void onAttach() {
		Menus.TRACK0.stop();
		if(track != null && !track.isPlaying())
			track.loop(.9f);
	}
	
	@Override
	public void onDetach() {
		if(track != null)
			track.stop();
	}
	
	public void reset() {
		this.load(this.data);
	}
	
	public void saveToFile(String path) {
		saveToFile(new File(path));
	}
	
	public void saveToFile(File file  ) {
		try(PrintWriter out = Util.createPrintWriter(file, false)) {
			if(bg != null)
				out.println("bg:" + bg.name + "," + bg.speed);
			if(track != null)
				out.println("track:" + track.name);
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
	
	public void loadFromFile(String path) {
		loadFromFile(new File(path));
	}
	
	public void loadFromFile(File file  ) {
		this.data.clear();
		Util.parseFromFile(file, this.data);
		this.load(this.data);
	}
	
	public void loadFromResource(Class<?> clazz, String name) {
		this.data.clear();
		Util.parseFromResource(clazz, name, this.data);
		this.load(this.data);
	}
	
	protected void load(List<String> data) {
		transparency = 0f;
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {
				grid[i][j].isReserved = false;
				grid[i][j].tile = null;
				grid[i][j].unit = null;
			}
		for(String line: data) {
			line = line.trim();
			if(line.startsWith("bg:") && bg == null) {
				String[] temp = line.substring("bg:".length()).split("\\,");
				String
					name  = temp.length > 0 ? temp[0] : "",
					speed = temp.length > 1 ? temp[1] : "";
				bg = Sprites.get(name);
				bg.loop(Util.stringToFloat(speed));
			}
			if(line.startsWith("track:") && track == null) {
				String name = line.substring("track:".length());
				track = Sounds.get(name);
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
