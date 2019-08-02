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
		if(this.tile != null)
			this.tile.update(context);
		if(this.entity != null)
			this.entity.update(context);
	}	
}
