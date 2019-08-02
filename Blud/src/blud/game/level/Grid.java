package blud.game.level;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.game.Game;
import blud.game.entity.Entity;
import blud.game.tile.Tile;
import blud.geom.Vector2f;

public class Grid implements Renderable, Updateable {	
	protected Level
		level;
	protected final Vector2f.Mutable
		local = new Vector2f.Mutable(),
		pixel = new Vector2f.Mutable();
	protected Grid
		north,
		south,
		east,
		west;
	
	protected float
		light;	
	protected Tile
		tile;
	protected Entity
		entity;
	
	public Grid(Level level, int i, int j) {
		this.level = level;
		this.local.set(i, j);
		this.pixel.set(Game.localToPixel(i, j));
		
		if(j > 0) {
			north = level.grid[i][j - 1];
			if(north != null)
				north.south = this;
		}
		if(j < Level.LEVEL_H - 1) {
			south = level.grid[i][j + 1];
			if(south != null)
				south.north = this;
		}
		if(i > 0) {
			west = level.grid[i - 1][j];
			if(west != null)
				west.east = this;
		}
		if(i < Level.LEVEL_W - 1) {
			east = level.grid[i + 1][j];
			if(east != null)
				east.west = this;
		}		
	}
	
	public Grid north() {
		return north;
	}
	
	public Grid south() {
		return south;
	}
	
	public Grid east() {
		return east;
	}
	
	public Grid west() {
		return west;
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
