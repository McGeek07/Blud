package blud.game.level.node;

import java.util.List;

import blud.core.Engine;
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
		lightLevel;
	public boolean
		playerVision,
		entityVision,
		isReserved;
	
	public Tile
		tile;
	public Unit
		unit;
	
	public final Vector2f
		local,
		pixel;
	
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
		level.updateLighting = true;
		level.updatePlayerVision = true;
		level.updateEntityVision = true;
	}
	
	public void setUnit(Unit unit) {
		if(this.unit != null)
			this.unit.node = null;
		this.unit = unit;
		if(this.unit != null)
			this.unit.node = this;
		level.updateLighting = true;
		level.updatePlayerVision = true;
		level.updateEntityVision = true;
	}
	
	public boolean hasTile() {
		return tile != null;
	}
	
	public boolean hasUnit() {
		return unit != null;
	}
	
	public boolean isReserved() {
		return unit != null || isReserved;
	}
	
	public boolean blocksLight() {
		return unit != null && unit.blocksLight;
	}
	
	public boolean blocksPlayerVision() {
		return unit != null && unit.blocksPlayerVision;
	}
	
	public boolean blocksEntityVision() {
		return unit != null && unit.blocksEntityVision;
	}
	
	public boolean isRenderable() {
		return
				pixel.X() >= level.camera.X() - Engine.CANVAS_W / 2 - 6 &&
				pixel.X() <= level.camera.X() + Engine.CANVAS_W / 2 + 6 &&
				pixel.Y() >= level.camera.Y() - Engine.CANVAS_H / 2 - 6 &&
				pixel.Y() <= level.camera.Y() + Engine.CANVAS_H / 2 + 6;
	}
	
	public boolean isUpdateable() {
		return true;
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
	
	private static int
		CAST_ID;
	private int
		cast_id;
	
	public void castLight() {
		if(unit != null && unit.lightLevel > 0 && unit.lightRange > 0) {
			if(lightLevel < unit.lightLevel)
				lightLevel = unit.lightLevel;
			if(unit.lightDirection >= 0) {
				if(neighbor[unit.lightDirection] != null)
					neighbor[unit.lightDirection].castLight(unit.lightLevel - 1f / unit.lightRange, unit.lightRange - 1, unit.lightDirection);
			} else {
				for(int i = 0; i < neighbor.length; i ++)
					if(neighbor[i] != null)
						neighbor[              i].castLight(unit.lightLevel - 1f / unit.lightRange, unit.lightRange - 1, unit.lightDirection);
			}			
		}
	}
	
	public void castPlayerVision() {
		if(unit != null && unit.playerVisionRange > 0) {
			playerVision = true;
			if(unit.playerVisionDirection >= 0) {
				if(neighbor[unit.playerVisionDirection] != null)
					neighbor[unit.playerVisionDirection].castPlayerVision(unit.playerVisionRange - 1, unit.playerVisionDirection);
			} else {
				for(int i = 0; i < neighbor.length; i ++)
					if(neighbor[i] != null)
						neighbor[                     i].castPlayerVision(unit.playerVisionRange - 1, unit.playerVisionDirection);
			}			
		}
	}
	
	public void castEntityVision() {		
		if(unit != null && unit.entityVisionRange > 0) {
			entityVision = true;
			if(unit.entityVisionDirection >= 0) {
				if(neighbor[unit.entityVisionDirection] != null)
					neighbor[unit.entityVisionDirection].castEntityVision(unit.entityVisionRange - 1, unit.entityVisionDirection);
			} else {
				for(int i = 0; i < neighbor.length; i ++)
					if(neighbor[i] != null)
						neighbor[                     i].castEntityVision(unit.entityVisionRange - 1, unit.entityVisionDirection);
			}			
		}
	}
	
	public void castLight(float level, int range, int facing) {
		if(level > 0 && range > 0) {
			if(lightLevel < level)
				lightLevel = level;
			if(!blocksLight())
				if(facing >= 0) {
					if(neighbor[facing] != null)
						neighbor[facing].castLight(level - 1f / range, range - 1, facing);
				} else {
					for(int i = 0; i < neighbor.length; i ++)
						if(neighbor[i] != null)
							neighbor[ i].castLight(level - 1f / range, range - 1, facing);
				}					
		}
	}
	
	public void castPlayerVision(int range, int facing) {
		if(range > 0) {
			playerVision = true;
			if(!blocksPlayerVision())
				if(facing >= 0) {
					if(neighbor[facing] != null)
						neighbor[facing].castPlayerVision(range - 1, facing);
				} else {
					for(int i = 0; i < neighbor.length; i ++)
						if(neighbor[i] != null)
							neighbor[ i].castPlayerVision(range - 1, facing);
				}
		}
	}
	
	public void castEntityVision(int range, int facing) {
		if(range > 0) {
			entityVision = true;
			if(!blocksEntityVision())
				if(facing >= 0) {
					if(neighbor[facing] != null)
						neighbor[facing].castEntityVision(range - 1, facing);
				} else {
					for(int i = 0; i < neighbor.length; i ++)
						if(neighbor[i] != null)
							neighbor[ i].castEntityVision(range - 1, facing);
				}
		}
	}
	
	public List<Node> walk(List<Node> list, Check check1, Check check2, int range, int facing) {
		return walk(list, check1, check2, range, facing, ++ CAST_ID);
	}
	
	private List<Node> walk(List<Node> list, Check check1, Check check2, int range, int facing, int cast_id) {
		if(range > 0) {
			if(this.cast_id != cast_id) {
				this.cast_id = cast_id;
				if(check2.check(this))
					list.add(this);
			}
			if(facing >= 0) {
				if(neighbor[facing] != null && !check1.check(neighbor[facing]))
					neighbor[facing].walk(list, check1, check2, range - 1, facing, cast_id);
			} else {
				for(int i = 0; i < neighbor.length; i ++)
					if(neighbor[i] != null && !check1.check(neighbor[i]))
						neighbor[ i].walk(list, check1, check2, range - 1, facing, cast_id);
			}
		}
		return list;
	}
	
	
	public static interface Check {
		public boolean check(Node node);
	}
}
