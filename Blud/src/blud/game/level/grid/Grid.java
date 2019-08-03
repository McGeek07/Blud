package blud.game.level.grid;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.game.Game;
import blud.game.level.Level;
import blud.game.level.tile.Tile;
import blud.game.level.unit.Unit;
import blud.geom.Vector2f;

public class Grid implements Renderable, Updateable {	
	public Level
		level;
	public Grid[]
		neighbors;
	public float
		playerVision,
		entityVision;
	
	public Tile
		tile;
	public Unit
		unit;
	public final Vector2f.Mutable
		local = new Vector2f.Mutable(),
		pixel = new Vector2f.Mutable();
	
	public Grid(Level level, int i, int j) {
		this.level = level;
		this.local.set(i, j);
		this.pixel.set(Game.localToPixel(i, j));
		
		this.neighbors = new Grid[8];
		for(int k = 0; k < neighbors.length; k ++) {
			Vector2f direction = Game.DIRECTION[k];
			if(
					i + direction.x() >= 0 && i + direction.x() < Level.LEVEL_W &&
					j + direction.y() >= 0 && j + direction.y() < Level.LEVEL_H) {				
				neighbors[k] = level.grid
						[i + direction.x()]
						[j + direction.y()];
				if(neighbors[k] != null)
					neighbors[k].neighbors[(k + 4) % 8] = this;
			}
		}
	}
	
	public boolean isEmpty() {
		return unit == null;
	}
	
	public boolean blocksPlayerVision() {
		return unit != null && unit.blocksPlayerVision;
	}
	
	public boolean blocksEntityVision() {
		return unit != null && unit.blocksEntityVision;
	}
	
	public void setTransparency(float transparency) {
		if(tile != null)
			tile.setTransparency(transparency);
		if(unit != null)
			unit.setTransparency(transparency);		
	}
	
	public void setSpriteTransparency(float transparency) {
		if(tile != null)
			tile.setSpriteTransparency(transparency);
		if(unit != null)
			unit.setSpriteTransparency(transparency);		
	}
	
	public void setShadowTransparency(float transparency) {
		if(tile != null)
			tile.setShadowTransparency(transparency);
		if(unit != null)
			unit.setShadowTransparency(transparency);		
	}

	@Override
	public void render(RenderContext context) {
		if(this.tile != null)
			this.tile.render(context);
		if(this.unit != null)
			this.unit.render(context);
	}

	@Override
	public void update(UpdateContext context) {
		if(this.tile != null)
			this.tile.update(context);
		if(this.unit != null)
			this.unit.update(context);
	}
	
	public void updatePlayerVision() {
		if(unit != null && unit.playerVisionLevel > 0)
			updatePlayerVision(
					unit.playerVisionLevel,
					unit.playerVisionRange,
					unit.playerVisionDirection
					);
	}
	
	public void updatePlayerVision(float level, float range, int direction) {
		if(playerVision <= level) {
			playerVision = level;
			if(!blocksPlayerVision())
				if(direction >= 0) {
					if(neighbors[direction] != null)
						neighbors[direction].updatePlayerVision(level - (level / range), range - 1, direction);
				} else {
					for(int i = 0; i < neighbors.length; i += 2)
						if(neighbors[i] != null)
							neighbors[i].updatePlayerVision(level - (level / range), range - 1, direction);
				}
		}
	}
	
	public void updateEntityVision() {
		if(unit != null && unit.entityVisionLevel > 0)
			updateEntityVision(
					unit.entityVisionLevel,
					unit.entityVisionRange,
					unit.entityVisionDirection
					);
	}
	
	public void updateEntityVision(float level, float range, int direction) {
		if(entityVision <= level) {
			entityVision = level;
			if(!blocksEntityVision())
				if(direction >= 0) {
					if(neighbors[direction] != null)
						neighbors[direction].updateEntityVision(level - (level / range), range - 1, direction);
				} else {
					for(int i = 0; i < neighbors.length; i += 2)
						if(neighbors[i] != null)
							neighbors[i].updateEntityVision(level - (level / range), range - 1, direction);
				}
		}
	}
}
