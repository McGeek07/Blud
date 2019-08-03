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
	public Grid[]
		neighbors;
	public float
		playerVision,
		entityVision;
	
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
		return entity == null;
	}
	
	public boolean blocksPlayerVision() {
		return entity != null && entity.blocksPlayerVision;
	}
	
	public boolean blocksEntityVision() {
		return entity != null && entity.blocksEntityVision;
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
		float transparency = 0f;
		if(playerVision > 0)
			transparency = entityVision;
//			transparency = playerVision >= entityVision ?
//					playerVision :
//					entityVision ;
		if(this.tile != null) {	
			this.tile.setShadowTransparency(transparency);
			this.tile.update(context);
		}
		if(this.entity != null) {
			this.entity.setShadowTransparency(transparency);
			this.entity.update(context);
		}
	}
	
	protected void updatePlayerVision() {
		if(entity != null && entity.playerVisionLevel > 0)
			updatePlayerVision(
					entity.playerVisionLevel,
					entity.playerVisionRange,
					entity.playerVisionDirection
					);
	}
	
	protected void updatePlayerVision(float level, float range, int direction) {
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
	
	protected void updateEntityVision() {
		if(entity != null && entity.entityVisionLevel > 0)
			updateEntityVision(
					entity.entityVisionLevel,
					entity.entityVisionRange,
					entity.entityVisionDirection
					);
	}
	
	protected void updateEntityVision(float level, float range, int direction) {
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
