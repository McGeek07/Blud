package blud.game.level.node;

import java.util.List;

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
	
	public boolean isReserved() {
		return unit != null || reserved;
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
	
	public void setWhiteTransparency(float transparency) {
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
		if(unit != null && unit.playerVisionLevel > 0 && unit.playerVisionRange > 0) {
			float
				level = unit.playerVisionLevel,
				range = unit.playerVisionRange;
			int
				facing = unit.playerVisionDirection;
			if(playerVision <= level)
				playerVision = level;
			level = level - (level - this.level.entityVisionFloor) / range;
			range = range - 1;
			if(facing >= 0) {
				if(neighbor[facing] != null)
					neighbor[facing].updatePlayerVision(level, range, facing);
			} else {
				for(int i = 0; i < neighbor.length; i ++ )
					if(neighbor[i] != null)
						neighbor[i].updatePlayerVision(level, range, facing);
			}
		}
	}
	
	public void updatePlayerVision(float level, float range, int facing) {
		if(level > 0 && range > 0 && playerVision <= level) {
			playerVision = level;
			if(!blocksPlayerVision()) {
				level = level - (level - this.level.playerVisionFloor) / range;
				range = range - 1;
				if(facing >= 0) {
					if(neighbor[facing] != null)
						neighbor[facing].updatePlayerVision(level, range, facing);
				} else {
					for(int i = 0; i < neighbor.length; i ++ )
						if(neighbor[i] != null)
							neighbor[i].updatePlayerVision(level, range, facing);
				}
			}
		}
	}
	
	public void updateEntityVision() {
		if(unit != null && unit.entityVisionLevel > 0 && unit.entityVisionRange > 0) {
			float
				level = unit.entityVisionLevel,
				range = unit.entityVisionRange;
			int
				facing = unit.entityVisionDirection;
			if(entityVision <= level)
				entityVision = level;
			level = level - (level - this.level.entityVisionFloor) / range;
			range = range - 1;
			if(facing >= 0) {
				if(neighbor[facing] != null)
					neighbor[facing].updateEntityVision(level, range, facing);
			} else {
				for(int i = 0; i < neighbor.length; i ++ )
					if(neighbor[i] != null)
						neighbor[i].updateEntityVision(level, range, facing);
			}
		}
	}
	
	public void updateEntityVision(float level, float range, int facing) {
		if(level > 0 && range > 0 && entityVision <= level) {
			entityVision = level;
			if(!blocksEntityVision()) {
				level = level - (level - this.level.entityVisionFloor) / range;
				range = range - 1;
				if(facing >= 0) {
					if(neighbor[facing] != null)
						neighbor[facing].updateEntityVision(level, range, facing);
				} else {
					for(int i = 0; i < neighbor.length; i ++ )
						if(neighbor[i] != null)
							neighbor[i].updateEntityVision(level, range, facing);
				}
			}
		}
	}
	private static int
		HASH;
	private int
		hash;
	
	public  List<Node> walk(List<Node> list, Walk walk, int range, int facing) {
		return this.walk(list, walk, range, facing, ++ HASH);
	}
	
	private List<Node> walk(List<Node> list, Walk walk, int range, int facing, int hash) {
		if(range > 0 && this.hash != hash) {
			if(walk.step(this))
				list.add(this);
			if(facing >= 0) {
				if(neighbor[facing] != null)
					neighbor[facing].walk(list, walk, range - 1, facing, hash);
			} else {
				for(int i = 0; i < neighbor.length; i ++)
					if(neighbor[i] != null)
						neighbor[i].walk(list, walk, range - 1, facing, hash);
			}
		}
		return list;
	}
	
	
	public static interface Walk {
		public boolean step(Node node);
	}
}
