package blud.game.level;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.core.input.Input;
import blud.game.Game;
import blud.game.entity.Entity;
import blud.game.entity.entities.Entities;
import blud.game.sprite.Sprite;
import blud.game.tile.Tile;
import blud.game.tile.tiles.Tiles;
import blud.game.wall.Wall;
import blud.game.wall.walls.Walls;
import blud.geom.Vector;
import blud.geom.Vector2f;
import blud.util.Logic;

public class Editor extends Level {
	protected final Tile[]
		tiles;
	protected final Wall[]
		walls;
	protected final Entity[]
		entities;
	
	protected final Sprite
		debug;
	protected final Brush
		brush;
	
	public Editor() {
		super();		
		tiles = new Tile[Tiles.NAMES.length];
		walls = new Wall[Walls.NAMES.length];
		entities = new Entity[Entities.NAMES.length];
		
		for(int i = 0; i < tiles.length; i ++)
			tiles[i] = TILES.get(Tiles.NAMES[i]);
		for(int i = 0; i < walls.length; i ++)
			walls[i] = WALLS.get(Walls.NAMES[i]);
		for(int i = 0; i < entities.length; i ++)
			entities[i] = ENTITIES.get(Entities.NAMES[i]);

		debug = Sprite.get("debug");
		brush = new Brush();
	}	
	
	
	@Override
	public void onRender(RenderContext context) {
		background.render(context);
		context.g2D.translate(
				context.canvas_w / 2 - camera.x(),
				context.canvas_h / 2 - camera.y()
				);
		
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {
				debug.setFrame((i + j) & 1);
				context = context.push();
				Vector2f pixel = Game.localToPixel(i, j);
				context.g2D.translate(
						pixel.x() - Game.SPRITE_W / 2,
						pixel.y() - Game.SPRITE_H / 2
						);
				debug.render(context);
				context = context.pull();
				grid[i][j].render(context);
				if(
						i == brush.cursor.x() &&
						j == brush.cursor.y()
						)
					brush.render(context);
			}
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		if(Input.isKeyDn(Logic.OR, Input.KEY_W, Input.KEY_UP_ARROW))
			Vector.add(camera, 0, - 1);
		if(Input.isKeyDn(Logic.OR, Input.KEY_A, Input.KEY_L_ARROW ))
			Vector.add(camera, - 1, 0);
		if(Input.isKeyDn(Logic.OR, Input.KEY_S, Input.KEY_DN_ARROW))
			Vector.add(camera, 0, + 1);
		if(Input.isKeyDn(Logic.OR, Input.KEY_D, Input.KEY_R_ARROW ))
			Vector.add(camera, + 1, 0);
		if(Input.isKeyDnAction(Input.KEY_TAB))
			brush.nextMode();
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++)
				if(grid[i][j].entity != null)
					if(
						i - brush.cursor.x() >= 0 && i - brush.cursor.x() < 2 &&
						j - brush.cursor.y() >= 0 && j - brush.cursor.y() < 2)
						grid[i][j].entity.setSpriteTransparency(.5f);
					else
						grid[i][j].entity.setSpriteTransparency( 0f);
							
		context = context.push();
		brush.update(context);		
		context = context.pull();
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
			WALL = 1,
			ENTITY = 2,
			CURSOR_W = 11,
			CURSOR_H = 12;
		protected final Vector2f.Mutable
			cursor = new Vector2f.Mutable();
		protected final Sprite.Group
			sprite = new Sprite.Group(
					Sprite.get("cursor_r"),
					Sprite.get("cursor_g"),
					Sprite.get("cursor_b"),
					Sprite.get("cursor_w")
					);
		protected int
			tile,
			wall,
			entity,
			mode;
		
		protected blud.game.level.Object
			brush;
		
		public Brush() {
			this.setMode(WALL);
			sprite.loop(3, 4f);
			sprite.setSpriteTransparency(.2f);
		}


		@Override
		public void render(RenderContext context) {
			if(brush != null) {
				brush.setSpriteTransparency(0f);
				brush.render(context);
			}
			context = context.push();
			Vector2f pixel = Game.localToPixel(
					cursor.x(),
					cursor.y()
					);
			context.g2D.translate(
					pixel.x() - CURSOR_W / 2,
					pixel.y() - CURSOR_H / 2
					);
			sprite.render(context);
			context = context.pull();
		}

		@Override
		public void update(UpdateContext context) {
			Vector2f mouse = getMouseLocal(context);
			if(
					mouse.X() > 0 && mouse.X() < LEVEL_W &&
					mouse.Y() > 0 && mouse.Y() < LEVEL_H
					) {
				cursor.set(mouse);
				if(brush != null)
					brush.setLocal(
							cursor.x(),
							cursor.y()
							);
			}			
			sprite.update(context);
		}
		
		public void nextMode() {
			mode ++;
			if(mode > 2)
				mode = 0;
			setMode(mode);
		}
		
		public void prevMode() {
			mode --;
			if(mode < 0)
				mode = 2;
			setMode(mode);
		}
		
		public void nextBrush() {
			switch(mode) {
				case TILE: 
					tile ++; 
					if(tile >= tiles.length) 
						tile = 0; 
					brush = tiles[tile]; 
					break;
				case WALL: 
					wall ++; 
					if(wall >= walls.length) 
						wall = 0; 
					brush = walls[wall]; 
					break;
				case ENTITY: 
					entity ++; 
					if(entity >= entities.length) 
						entity = 0; 
					brush = entities[entity]; 
					break;
			}
		}
		
		public void prevBrush() {
			switch(mode) {
				case TILE: 
					tile --; 
					if(tile < 0) 
						tile = tiles.length - 1; 
					brush = tiles[tile]; 
					break;
				case WALL: 
					wall --; 
					if(wall < 0) 
						wall = walls.length - 1; 
					brush = walls[wall]; 
					break;
				case ENTITY: 
					entity --; 
					if(entity < 0) 
						entity = entities.length - 1; 
					brush = entities[entity]; 
					break;
			}
		}
		
		public void setMode(int mode) {
			this.mode = mode;
			switch(this.mode) {
				case TILE: brush = tiles[tile]; break;
				case WALL: brush = walls[wall]; break;
				case ENTITY: brush = entities[entity]; break;
			}
		}
		
		public void paint() {
			switch(mode) {
			case TILE:
			}
		}
	}
}
