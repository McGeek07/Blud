package blud.game.menu;

import java.awt.Color;
import java.util.ArrayList;

import blud.core.input.Input;
import blud.core.scene.Scene;
import blud.game.menu.component.Component;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector2f;

public class Menu extends Scene {
	public final ArrayList<Component>
		children = new ArrayList<>();
	public Sprite
		bg = Sprites.get("bg1");
	
	public Menu() {
	}
	
	@Override
	public void onKeyDnAction(int key) {
		for(Component child: children)
			if(child.hasHover)
				child.onKeyDnAction(key);
	}
	
	@Override
	public void onKeyUpAction(int key) {
		for(Component child: children)
			if(child.hasHover)
				child.onKeyUpAction(key);
	}
	
	@Override
	public void onBtnDnAction(int btn, int x, int y) {
		for(Component child: children)
			if(child.hasHover)
				child.onBtnDnAction(btn);
	}
	
	@Override
	public void onBtnUpAction(int btn, int x, int y) {
		for(Component child: children)
			if(child.hasHover)
				child.onBtnUpAction(btn);
	}
	
	@Override
	public void onMouseMoved(int x, int y) {
		for(Component child: children) {
			if(child.hasHover && !child.contains(x, y)) {
				child.hasHover = false;
				child.onMouseLeave();
			}
			if(!child.hasHover && child.contains(x, y)) {
				child.hasHover = true;
				child.onMouseHover();
			}
		}			
	}
	
	@Override
	public void onAttach() {
		Vector2f mouse = Input.getMouse();
		onMouseMoved(
				mouse.x(),
				mouse.y()
				);
	}
	
	@Override
	public void onRender(RenderContext context) {
		context.g2D.setColor(Color.BLACK);
		context.g2D.fillRect(
				0,
				0,
				context.canvas_w,
				context.canvas_h
				);
		if(bg != null) {
			bg.pixel.set(
					context.canvas_w / 2,
					context.canvas_h / 2
					);
			bg.render(context);
		}
		for(Component child: children)
			child.render(context);
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		if(bg != null)
			bg.update(context);
		for(Component child: children)
			child.update(context);
	}
}
