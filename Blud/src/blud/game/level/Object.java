package blud.game.level;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.game.Game;
import blud.game.sprite.Sprite;
import blud.geom.Vector;
import blud.geom.Vector2f;
import blud.util.Copyable;

public abstract class Object implements Renderable, Updateable, Copyable<Object> {	
	protected final Sprite.Group
		sprites;
	protected Vector2f.Mutable
		local = new Vector2f.Mutable(),
		pixel = new Vector2f.Mutable();
	protected Grid
		grid;
	
	public Object(Vector local, Sprite... sprites) {
		this(local.X(), local.Y(), sprites);
	}
	
	public Object(float i, float j, Sprite... sprites) {
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
				pixel.x(),
				pixel.y()
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
	
	public abstract void onRender(RenderContext context);
	public abstract void onUpdate(UpdateContext context);
}
