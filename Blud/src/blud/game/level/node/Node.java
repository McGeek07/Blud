package blud.game.level.node;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.game.Game;
import blud.game.level.Level;
import blud.game.level.tile.Tile;
import blud.game.level.unit.Unit;
import blud.geom.Vector2f;

public class Node implements Renderable, Updateable {	
	public Level
		level;
	public Node[]
		neighbor;
	public float
		playerVision,
		entityVision;
	
	public Tile
		tile;
	public Unit
		unit;
	public final Vector2f
		local,
		pixel;
	
	public boolean
		reserved;
	
	public Node(Level level, int i, int j) {
		this.level = level;
		this.local = new Vector2f(i, j);
		this.pixel = Game.localToPixel(i, j);
				
		this.neighbor = new Node[4];
		for(int k = 0; k < neighbor.length; k ++) {
			Vector2f direction = Game.LOCAL_DIRECTION[k];
			if(
					i + direction.x() >= 0 && i + direction.x() < Level.LEVEL_W &&
					j + direction.y() >= 0 && j + direction.y() < Level.LEVEL_H) {				
				neighbor[k] = level.grid
						[i + direction.x()]
						[j + direction.y()];
				if(neighbor[k] != null)
					neighbor[k].neighbor[(k + 2) % 4] = this;
			}
		}
	}
	
	public void setTile(Tile tile) {
		if(this.tile != null)
			this.tile.node = null;
		this.tile = tile;
		if(this.tile != null)
			this.tile.node = this;
	}
	
	public void setUnit(Unit unit) {
		if(this.unit != null)
			this.unit.node = null;
		this.unit = unit;
		if(this.unit != null)
			this.unit.node = this;
	}
	
	public boolean hasTile() {
		return tile != null;
	}
	
	public boolean hasUnit() {
		return unit != null;
	}
	
	public boolean blocksPlayerVision() {
		return unit != null && unit.blocksPlayerVision;
	}
	
	public boolean blocksEntityVision() {
		return unit != null && unit.blocksEntityVision;
	}
	
	public void setSpriteTransparency(float transparency) {
		if(tile != null)
			tile.setSpriteTransparency(transparency);
		if(unit != null)
			unit.setSpriteTransparency(transparency);		
	}
	
	public void setWhiteTransparnecy(float transparency) {
		if(tile != null)
			tile.setWhiteTransparency(transparency);
		if(unit != null)
			unit.setWhiteTransparency(transparency);
	}
	
	public void setBlackTransparency(float transparency) {
		if(tile != null)
			tile.setBlackTransparency(transparency);
		if(unit != null)
			unit.setBlackTransparency(transparency);		
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
					if(neighbor[direction] != null)
						neighbor[direction].updatePlayerVision(level - (level / range), range - 1, direction);
				} else {
					for(int i = 0; i < neighbor.length; i ++ )
						if(neighbor[i] != null)
							neighbor[i].updatePlayerVision(level - (level / range), range - 1, direction);
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
					if(neighbor[direction] != null)
						neighbor[direction].updateEntityVision(level - (level / range), range - 1, direction);
				} else {
					for(int i = 0; i < neighbor.length; i ++ )
						if(neighbor[i] != null)
							neighbor[i].updateEntityVision(level - (level / range), range - 1, direction);
				}
		}
	}
}