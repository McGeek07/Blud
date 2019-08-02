package blud.game.level;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.game.Game;
import blud.game.entity.Entity;
import blud.game.tile.Tile;
import blud.geom.Vector2f;

public class Grid implements Renderable, Updateable {	
	public Level
		level;
	public Grid
		north,
		south,
		east,
		west,
		north_east,
		north_west,
		south_east,
		south_west;	
	public float
		player_vision,
		entity_vision;
	
	public Tile
		tile;
	public Entity
		entity;

	protected final Vector2f.Mutable
		local = new Vector2f.Mutable(),
		pixel = new Vector2f.Mutable();
	
	public Grid(Level level, int i, int j) {
		this.level = level;
		this.local.set(i, j);
		this.pixel.set(Game.localToPixel(i, j));
		
		boolean
			north = false,
			south = false;		
		if(north = j < Level.LEVEL_H - 1) {
			this.north = level.grid[i][j + 1];
			if(this.north != null)
				this.north.south = this;
		}
		if(south = j > 0) {
			this.south = level.grid[i][j - 1];
			if(this.south != null)
				this.south.north = this;
		}
		if(i < Level.LEVEL_W - 1) {
			east = level.grid[i + 1][j];
			if(north)
				north_east = level.grid[i + 1][j + 1];
			if(south)
				south_east = level.grid[i + 1][j - 1];
			if(east != null)
				east.west = this;
			if(north_east != null)
				north_east.south_west = this;
			if(south_east != null)
				south_east.north_west = this;
		}
		if(i > 0) {
			west = level.grid[i - 1][j];
			if(north)
				north_west = level.grid[i - 1][j + 1];
			if(south)
				south_west = level.grid[i - 1][j - 1];
			if(west != null)
				west.east = this;
			if(north_west != null)
				north_west.south_east = this;
			if(south_west != null)
				south_west.north_east = this;
		}
	}

	@Override
	public void render(RenderContext context) {
		if(this.tile != null)
			this.tile.render(context);
		if(this.entity != null)
			this.entity.render(context);
	}

	@Override
	public void update(UpdateContext context) {
		float transparency = player_vision * entity_vision;
		if(this.tile != null) {	
			this.tile.setShadowTransparency(transparency);
			this.tile.update(context);
		}
		if(this.entity != null) {
			this.entity.setShadowTransparency(transparency);
			this.entity.update(context);
		}
	}
	
	protected void update_player_vision() {
		if(entity != null && entity.player_vision_value > 0)
			if(entity.player_vision_direction != null)
				update_player_vision(
						entity.player_vision_value,
						entity.player_vision_dropoff,
						entity.player_vision_direction
						);
			else
				update_player_vision(
						entity.player_vision_value,
						entity.player_vision_dropoff
						);
	}
	
	protected void update_player_vision(float value, float dropoff, Vector2f direction) {
		if(value > 0 && this.player_vision <= value) {
			this.player_vision = value;			
			if((value -= dropoff) > 0) {
				if(direction == Game.NORTH && north != null && (north.entity == null || north.entity.player_vision_transparency)) north.update_player_vision(value, dropoff, direction);
				if(direction == Game.SOUTH && south != null && (south.entity == null || south.entity.player_vision_transparency)) south.update_player_vision(value, dropoff, direction);
				if(direction == Game.EAST && east != null && (east.entity == null || east.entity.player_vision_transparency)) east.update_player_vision(value, dropoff, direction);
				if(direction == Game.WEST && west != null && (west.entity == null || west.entity.player_vision_transparency)) west.update_player_vision(value, dropoff, direction);
				if(direction == Game.NORTH_EAST && north_east != null && (north_east.entity == null || north_east.entity.player_vision_transparency)) north_east.update_player_vision(value, dropoff, direction);
				if(direction == Game.NORTH_WEST && north_west != null && (north_west.entity == null || north_west.entity.player_vision_transparency)) north_west.update_player_vision(value, dropoff, direction);
				if(direction == Game.SOUTH_EAST && south_east != null && (south_east.entity == null || south_east.entity.player_vision_transparency)) south_east.update_player_vision(value, dropoff, direction);
				if(direction == Game.SOUTH_WEST && south_west != null && (south_west.entity == null || south_west.entity.player_vision_transparency)) south_west.update_player_vision(value, dropoff, direction);
			}
		}
	}
	
	protected void update_player_vision(float value, float dropoff) {
		if(value > 0 && this.player_vision <= value) {
			this.player_vision = value;
			if((value -= dropoff) > 0) {
				if(north != null && (north.entity == null || north.entity.player_vision_transparency)) north.update_player_vision(value, dropoff);
				if(south != null && (south.entity == null || south.entity.player_vision_transparency)) south.update_player_vision(value, dropoff);
				if(east != null && (east.entity == null || east.entity.player_vision_transparency)) east.update_player_vision(value, dropoff);
				if(west != null && (west.entity == null || west.entity.player_vision_transparency)) west.update_player_vision(value, dropoff);
			}
		}
	}
	
	protected void update_entity_vision() {
		if(entity != null && entity.entity_vision_value > 0)
			if(entity.entity_vision_direction != null)
				update_entity_vision(
						entity.entity_vision_value,
						entity.entity_vision_dropoff,
						entity.entity_vision_direction
						);
			else
				update_entity_vision(
						entity.entity_vision_value,
						entity.entity_vision_dropoff
						);
	}
	
	protected void update_entity_vision(float value, float dropoff, Vector2f direction) {
		if(value > 0 && this.entity_vision <= value) {
			this.entity_vision = value;			
			if((value -= dropoff) > 0) {
				if(direction == Game.NORTH && north != null && (north.entity == null || north.entity.entity_vision_transparency)) north.update_entity_vision(value, dropoff, direction);
				if(direction == Game.SOUTH && south != null && (south.entity == null || south.entity.entity_vision_transparency)) south.update_entity_vision(value, dropoff, direction);
				if(direction == Game.EAST && east != null && (east.entity == null || east.entity.entity_vision_transparency)) east.update_entity_vision(value, dropoff, direction);
				if(direction == Game.WEST && west != null && (west.entity == null || west.entity.entity_vision_transparency)) west.update_entity_vision(value, dropoff, direction);
				if(direction == Game.NORTH_EAST && north_east != null && (north_east.entity == null || north_east.entity.entity_vision_transparency)) north_east.update_entity_vision(value, dropoff, direction);
				if(direction == Game.NORTH_WEST && north_west != null && (north_west.entity == null || north_west.entity.entity_vision_transparency)) north_west.update_entity_vision(value, dropoff, direction);
				if(direction == Game.SOUTH_EAST && south_east != null && (south_east.entity == null || south_east.entity.entity_vision_transparency)) south_east.update_entity_vision(value, dropoff, direction);
				if(direction == Game.SOUTH_WEST && south_west != null && (south_west.entity == null || south_west.entity.entity_vision_transparency)) south_west.update_entity_vision(value, dropoff, direction);
			}
		}
	}
	
	protected void update_entity_vision(float value, float dropoff) {
		if(value > 0 && this.entity_vision <= value) {
			this.entity_vision = value;
			if((value -= dropoff) > 0) {
				if(north != null && (north.entity == null || north.entity.entity_vision_transparency)) north.update_entity_vision(value, dropoff);
				if(south != null && (south.entity == null || south.entity.entity_vision_transparency)) south.update_entity_vision(value, dropoff);
				if(east != null && (east.entity == null || east.entity.entity_vision_transparency)) east.update_entity_vision(value, dropoff);
				if(west != null && (west.entity == null || west.entity.entity_vision_transparency)) west.update_entity_vision(value, dropoff);
			}
		}
	}
}
