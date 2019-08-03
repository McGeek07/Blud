package blud.game.level.entity;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.game.Game;
import blud.game.level.grid.Grid;
import blud.game.sprite.Sprite;
import blud.geom.Vector;
import blud.geom.Vector2f;

public abstract class Entity implements Renderable, Updateable {
	public Sprite.Group
		sprites;
	public final Vector2f.Mutable
		local = new Vector2f.Mutable(),
		pixel = new Vector2f.Mutable();
	public Grid
		grid;
	
	public Entity(Sprite... sprites) {
		this(0f, 0f, sprites);
	}
	
	public Entity(Vector local, Sprite... sprites) {
		this(local.X(), local.Y(), sprites);
	}
	
	public Entity(float i, float j, Sprite... sprites) {
		//this.type = type;
		this.setLocal(i, j);
		this.sprites = new Sprite.Group(sprites);
	}
	
	public void setTransparency(float transparency) {
		this.sprites.setTransparency(transparency);
	}
	
	public void setSpriteTransparency(float transparency) {
		this.sprites.setSpriteTransparency(transparency);
	}
	
	public void setShadowTransparency(float transparency) {
		this.sprites.setShadowTransparency(transparency);
	}
	
	public void setLocal(Vector v) {
		this.setLocal(v.X(), v.Y());
	}
	
	public void setLocal(float i, float j) {
		this.local.set(i, j);
		this.pixel.set(Game.localToPixel(i, j));
	}
	
	public void setPixel(Vector v) {
		this.setPixel(v.X(), v.Y());
	}
	
	public void setPixel(float x, float y) {
		this.pixel.set(x, y);
		this.local.set(Game.pixelToLocal(x, y));
	}
	
	public Vector2f local() {
		return this.local;
	}
	
	public Vector2f pixel() {
		return this.pixel;
	}

	@Override
	public void render(RenderContext context) {
		context = context.push();
		context.g2D.translate(
				pixel.x() - Game.SPRITE_W / 2,
				pixel.y() - Game.SPRITE_H / 2
				);
		this.sprites.render(context);
		context = context.pull();
		this.onRender(context);
	}

	@Override
	public void update(UpdateContext context) {
		context = context.push();
		this.sprites.update(context);
		context = context.pull();
		this.onUpdate(context);
	}
	
	public String getClassName() {
		return getClass().getSimpleName();
	}
	
	public abstract void onRender(RenderContext context);
	public abstract void onUpdate(UpdateContext context);
}