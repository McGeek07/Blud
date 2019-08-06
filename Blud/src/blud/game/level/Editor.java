package blud.game.level;

import java.awt.Color;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.core.input.Input;
import blud.game.Game;
import blud.game.level.entity.Entity;
import blud.game.level.tile.Tile;
import blud.game.level.tile.Trap;
import blud.game.level.tile.tiles.Tiles;
import blud.game.level.unit.Unit;
import blud.game.level.unit.Wall;
import blud.game.level.unit.units.Units;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector;
import blud.geom.Vector2f;
import blud.util.Logic;

public class Editor extends Level {
	public static final int
		EDIT = 0,
		PLAY = 1,
		GLOBAL_VISION = 0,
		ENTITY_VISION = 1,
		PLAYER_VISION = 2;
	protected List<Tile>
		tiles = new LinkedList<>(),
		traps = new LinkedList<>();
	protected List<Unit>
		units = new LinkedList<>(),
		walls = new LinkedList<>();
	protected Sprite
		debug;
	protected Brush
		brush;
	protected int
		editorMode,
		visionMode;
	
	protected File
		file;
	
	public Editor(String path) {
		this(new File(path));
	}
	
	public Editor(File file  ) {
		super();
		List<Tile> tiles = Tiles.load(new LinkedList<Tile>());
		List<Unit> units = Units.load(new LinkedList<Unit>());		
		for(Tile tile: tiles)
			if(tile instanceof Trap)
				this.traps.add(tile);
			else
				this.tiles.add(tile);
		for(Unit unit: units)
			if(unit instanceof Wall)
				this.walls.add(unit);
			else
				this.units.add(unit);
		load(this.file = file);
		debug = Sprites.get("Debug");
		brush = new Brush();
	}	
	
	
	@Override
	public void onRender(RenderContext context) {
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
		switch(editorMode) {
			case EDIT:
				for(int i = 0; i < LEVEL_W; i ++)
					for(int j = 0; j < LEVEL_H; j ++) 
						switch(visionMode) {
							case GLOBAL_VISION: grid[i][j].setBlackTransparency(1f); break;
							case ENTITY_VISION: grid[i][j].setBlackTransparency(grid[i][j].entityVision); break;
							case PLAYER_VISION: grid[i][j].setBlackTransparency(grid[i][j].playerVision > 0 ? grid[i][j].entityVision : 0f); break;
						}
				for(int i = 0; i < LEVEL_W; i ++)
					for(int j = 0; j < LEVEL_H; j ++)  {
						context = context.push();
							debug.pixel.set(grid[i][j].pixel);
							debug.frame = (i + j) & 1;
							debug.render(context);					
						context = context.pull();
						
						if(grid[i][j].tile != null)
							grid[i][j].tile.render(context);
						if(brush.mode < 2 && i == brush.cursor.x() && j == brush.cursor.y())
							brush.render(context);
					}
				for(int i = 0; i < LEVEL_W; i ++)
					for(int j = 0; j < LEVEL_H; j ++) {
						if(grid[i][j].unit != null)
							grid[i][j].unit.render(context);
						if(brush.mode > 1 && i == brush.cursor.x() && j == brush.cursor.y())
							brush.render(context);
					}
				break;
			case PLAY:
				for(int i = 0; i < LEVEL_W; i ++)
					for(int j = 0; j < LEVEL_H; j ++) 
						switch(visionMode) {
							case GLOBAL_VISION: grid[i][j].setBlackTransparency(1f); break;
							case ENTITY_VISION: grid[i][j].setBlackTransparency(grid[i][j].entityVision); break;
							case PLAYER_VISION: grid[i][j].setBlackTransparency(grid[i][j].playerVision > 0 ? grid[i][j].entityVision : 0f); break;
						}
				for(int i = 0; i < LEVEL_W; i ++)
					for(int j = 0; j < LEVEL_H; j ++) 
						if(grid[i][j].tile != null)
							grid[i][j].tile.render(context);
				for(int i = 0; i < LEVEL_W; i ++)
					for(int j = 0; j < LEVEL_H; j ++) 
						if(grid[i][j].unit != null)
							grid[i][j].unit.render(context);
				break;
		}		
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		if(bg != null)
			bg.update(context);	
		if(Input.isKeyDnAction(Input.KEY_1))
			this.visionMode = GLOBAL_VISION;
		if(Input.isKeyDnAction(Input.KEY_2))
			this.visionMode = ENTITY_VISION;
		if(Input.isKeyDnAction(Input.KEY_3))
			this.visionMode = PLAYER_VISION;
		switch(editorMode) {
			case EDIT:				
				if(Input.isKeyDnAction(Input.KEY_RETURN)) {
					editorMode = PLAY;
					save(this.file);	
					return;
				}
				if(Input.isKeyDn(Logic.OR, Input.KEY_W, Input.KEY_UP_ARROW))
					Vector.add(camera, 0, - 2);
				if(Input.isKeyDn(Logic.OR, Input.KEY_A, Input.KEY_L_ARROW ))
					Vector.add(camera, - 2, 0);
				if(Input.isKeyDn(Logic.OR, Input.KEY_S, Input.KEY_DN_ARROW))
					Vector.add(camera, 0, + 2);
				if(Input.isKeyDn(Logic.OR, Input.KEY_D, Input.KEY_R_ARROW ))
					Vector.add(camera, + 2, 0);
				if(Input.isKeyDnAction(Input.KEY_TAB))
					brush.nextMode();
				if(Input.isWheelDn())
					brush.nextBrush();
				if(Input.isWheelUp())
					brush.prevBrush();
				if(Input.isBtnDn(Input.BTN_1))
					brush.paint();
				if(Input.isBtnDn(Input.BTN_3))
					brush.erase();
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
						if(grid[i][j].unit != null)
							if(
								i - brush.cursor.x() >= 0 && i - brush.cursor.x() < 2 &&
								j - brush.cursor.y() >= 0 && j - brush.cursor.y() < 2)
								grid[i][j].unit.setSpriteTransparency(.5f);
							else
								grid[i][j].unit.setSpriteTransparency( 0f);									
				context = context.push();
				brush.update(context);		
				context = context.pull();
				break;
			case PLAY:
				if(Input.isKeyDnAction(Input.KEY_RETURN)) {
					editorMode = EDIT;
					load(this.file);
					return;
				}
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
					for(int j = 0; j < LEVEL_H; j ++) {
						grid[i][j].update(context);	
					}
		}		
	}
	
	@Override
	public void onExit() {
		if(editorMode == EDIT)
			save(this.file);
	}

	public Vector2f getMousePixel(UpdateContext context) {
		Vector2f mouse = Input.getMouse();
		return new Vector2f(
				mouse.X() - context.canvas_w / 2 + camera.X(),
				mouse.Y() - context.canvas_h / 2 + camera.Y()
				);
	}
	
	public Vector2f getMousePixel(RenderContext context) {
		Vector2f mouse = Input.getMouse();
		return new Vector2f(
				mouse.X() - context.canvas_w / 2 + camera.X(),
				mouse.Y() - context.canvas_h / 2 + camera.Y()
				);
	}
	
	public Vector2f getMouseLocal(UpdateContext context) {
		return Game.pixelToLocal(getMousePixel(context));
	}
	
	public Vector2f getMouseLocal(RenderContext context) {
		return Game.pixelToLocal(getMousePixel(context));
	}
	
	public class Brush implements Renderable, Updateable {
		public static final int
			TILE = 0,
			TRAP = 1,
			UNIT = 2,
			WALL = 3;
		protected final Vector2f.Mutable
			cursor = new Vector2f.Mutable();
		protected final Sprite.Group
			sprite = new Sprite.Group(
					Sprites.get("TileCursor1"),
					Sprites.get("TileCursor2"),
					Sprites.get("UnitCursor1"),
					Sprites.get("UnitCursor2")
					);
		protected int
			tile,
			trap,
			unit,
			wall,
			mode;
		
		protected Entity
			brush;
		
		public Brush() {
			this.setMode(TILE);
			sprite.loop(3, 4f);
			sprite.loop(2, 4f);
			sprite.loop(1, 4f);
			sprite.loop(0, 4f);
			sprite.setSpriteTransparency(.5f);
		}


		@Override
		public void render(RenderContext context) {
			if(brush != null) {
				brush.setSpriteTransparency(0f);
				brush.render(context);
			}
			sprite.render(context);
		}

		@Override
		public void update(UpdateContext context) {
			Vector2f mouse = getMouseLocal(context);
			if(
					mouse.X() > 0 && mouse.X() < LEVEL_W &&
					mouse.Y() > 0 && mouse.Y() < LEVEL_H
					) {
				cursor.set(mouse);
				int 
					i = cursor.x(),
					j = cursor.y();
				if(brush != null)
					brush.pixel.set(Game.localToPixel(i, j));
				if(sprite != null)
					sprite.setPixel(Game.localToPixel(i, j));
				switch(mode) {
					case TILE: 
					case TRAP:
						if(grid[i][j].tile != null) 
							sprite.set(1); 
						else 
							sprite.set(0); 
						break;
					case UNIT: 
					case WALL:
						if(grid[i][j].unit != null) 
							sprite.set(3);
						else
							sprite.set(2);
						break;
				}
			}			
			sprite.update(context);
		}
		
		public void setMode(int mode) {
			this.mode = mode;
			switch(this.mode) {
				case TILE: brush = tiles.get(tile); break;
				case TRAP: brush = traps.get(trap); break;
				case UNIT: brush = units.get(unit); break;
				case WALL: brush = walls.get(wall); break;
			}
		}
		
		public void nextMode() {
			mode ++;
			if(mode > 3)
				mode = 0;
			setMode(mode);
		}
		
		public void prevMode() {
			mode --;
			if(mode < 0)
				mode = 3;
			setMode(mode);
		}
		
		public void nextBrush() {
			switch(mode) {
				case TILE: 
					tile ++; 
					if(tile >= tiles.size()) 
						tile = 0; 
					brush = tiles.get(tile); 
					break;
				case TRAP: 
					trap ++; 
					if(trap >= traps.size()) 
						trap = 0; 
					brush = traps.get(trap); 
					break;
				case UNIT: 
					unit ++; 
					if(unit >= units.size()) 
						unit = 0; 
					brush = units.get(unit); 
					break;
				case WALL: 
					wall ++; 
					if(wall >= walls.size()) 
						wall = 0; 
					brush = walls.get(wall); 
					break;
			}
		}
		
		public void prevBrush() {
			switch(mode) {
				case TILE: 
					tile --; 
					if(tile < 0) 
						tile = tiles.size() - 1; 
					brush = tiles.get(tile); 
					break;
				case TRAP: 
					trap --; 
					if(trap < 0) 
						trap = traps.size() - 1; 
					brush = traps.get(trap); 
					break;
				case UNIT: 
					unit --; 
					if(unit < 0) 
						unit = units.size() - 1; 
					brush = units.get(unit); 
					break;
				case WALL: 
					wall --; 
					if(wall < 0) 
						wall = walls.size() - 1; 
					brush = walls.get(wall); 
					break;
			}
		}
		
		public void paint() {
			try {
				int
					i = cursor.x(),
					j = cursor.y();
				switch(mode) {
					case TILE: 
					case TRAP:
						Tile t = (Tile)brush.getClass().newInstance(); 
						t.pixel.set(Game.localToPixel(i, j));
						grid[i][j].setTile(t);
						break;
					case UNIT: 
					case WALL:
						Unit u = (Unit)brush.getClass().newInstance(); 
						u.pixel.set(Game.localToPixel(i, j));
						grid[i][j].setUnit(u);
						break;
				}				
			} catch (Exception ex) {
				ex.printStackTrace();
			}			
		}
		
		public void erase() {
			int
				i = cursor.x(),
				j = cursor.y();
			switch(mode) {
				case TILE: 
				case TRAP:
					grid[i][j].tile = null; break;
				case UNIT: 
				case WALL:
					grid[i][j].unit = null; break;
			}
		}
	}
}
