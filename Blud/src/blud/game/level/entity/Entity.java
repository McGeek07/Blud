package blud.game.level.entity;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.game.Game;
import blud.game.level.node.Node;
import blud.game.sprite.Sprite;
import blud.geom.Vector;
import blud.geom.Vector2f;

public abstract class Entity implements Renderable, Updateable {
	public final Sprite.Group
		sprites = new Sprite.Group(),
		effects = new Sprite.Group();
	public final Vector2f.Mutable
		pixel = new Vector2f.Mutable();
	public Node
		node;
	
	public Entity() {
		this(0f, 0f);
	}
	
	public Entity(Vector local) {
		this(local.X(), local.Y());
	}
	
	public Entity(float i, float j) {
		this.pixel.set(Game.localToPixel(i, j));
	}
	
	public void setSpriteTransparency(float transparency) {
		this.sprites.setSpriteTransparency(transparency);
	}	
	
	public void setWhiteTransparency(float transparency) {
		this.sprites.setWhiteTransparency(transparency);
	}
	
	public void setBlackTransparency(float transparency) {
		this.sprites.setBlackTransparency(transparency);
	}

	@Override
	public void render(RenderContext context) {
		onRender1(context);
		sprites.setPixel(pixel);
		sprites.render(context);		
		if(effects.sprites.size() > 0 && effects.get().mode > 0)
			effects.render(context);
		onRender2(context);
	}

	@Override
	public void update(UpdateContext context) {
		onUpdate1(context);
		sprites.setPixel(pixel);
		sprites.update(context);
		if(effects.sprites.size() > 0 && effects.get().mode > 0)
			effects.update(context);
		onUpdate2(context);
	}
	
	public String getClassName() {
		return getClass().getSimpleName();
	}
	
	public void onRender1(RenderContext context) { }
	public void onUpdate1(UpdateContext context) { }
	public void onRender2(RenderContext context) { }
	public void onUpdate2(UpdateContext context) { }
}
