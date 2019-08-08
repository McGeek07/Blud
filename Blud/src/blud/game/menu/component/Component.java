package blud.game.menu.component;

import blud.core.Renderable;
import blud.core.Updateable;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector2f;
import blud.util.Util;

public abstract class Component implements Renderable, Updateable {
	public static final int		
		DEFAULT = 0,
		WHITE 	= 1,
		BLACK 	= 2,
		TEXT_W  = 6,
		TEXT_H  = 6,
		SLOT_W  = 8,
		SLOT_H  = 11,
		PADDING = 2;
	public static final Sprite
		TEXT = Sprites.get("Text");
	public String
		text;
	public int
		line,
		color;
	public final Vector2f.Mutable
		loc = new Vector2f.Mutable(),
		dim = new Vector2f.Mutable();
	public boolean
		hasHover;
	
	public Component(String text) {
		this.text = text;
	}

	
	public void onKeyDnAction(int key) {
		
	}
	
	public void onKeyUpAction(int key) {
		
	}
	
	public void onBtnUpAction(int btn) {
		
	}
	
	public void onBtnDnAction(int btn) {
		
	}
	
	public void onMouseHover() {
		
	}
	
	public void onMouseLeave() {
		
	}


	@Override
	public void render(RenderContext context) {
		onRender(context);
		drawText(
				context,
				text,
				line,
				loc.x() + PADDING,
				loc.y() + PADDING,
				dim.x() - PADDING * 2,
				dim.y() - PADDING * 2,
				color
				);
	}

	@Override
	public void update(UpdateContext context) {
		onUpdate(context);
	}
	
	public boolean contains(int x, int y) {
		return
				x >= loc.x() && x <= loc.x() + dim.x() &&
				y >= loc.y() && y <= loc.y() + dim.y();
	}
	
	public void onRender(RenderContext context) { }
	public void onUpdate(UpdateContext context) { }	
	
	public static void drawText(RenderContext context, String text, int line, int x, int y, int w, int h, int color) {
		if(text != null) {
			text = text.toUpperCase();		
			int
				_w = w / TEXT_W,
				_h = h / TEXT_H,
				a = (int)Util.max(line * _w          , 0            ),
				b = (int)Util.min(line * _w + _h * _w, text.length());
			for(int i = a; i < b && i < b; i ++) {			
				char c = text.charAt(i);
				if(c >= '!' && c <= '_') {
					int
						_x = x + ((i % _w)       ) * TEXT_W + TEXT_W / 2,
						_y = y + ((i / _w) - line) * TEXT_H + TEXT_H / 2;//y + (i / _w) * 5 - (line * 5);
					switch(color) {
						case DEFAULT: 
							TEXT.setBlackTransparency(1f);
							TEXT.setWhiteTransparency(1f);
							break;
						case WHITE: 
							TEXT.setBlackTransparency(1f);
							TEXT.setWhiteTransparency(0f);
							break;
						case BLACK: 
							TEXT.setBlackTransparency(0f);
							TEXT.setWhiteTransparency(1f);
							break;
					}
					TEXT.frame = c - 33;
					TEXT.pixel.set(_x, _y);
					TEXT.render(context);
				}			
			}		
		}
	}
	
	public static Component format(Component component, int i, int j, int w, int h, int padding) {
		component.loc.set(
				padding + i * (padding + SLOT_W),
				padding + j * (padding + SLOT_H)
				);
		component.dim.set(
				w * (SLOT_W + padding) - padding * 2,
				h * (SLOT_H + padding) - padding * 2
				);
		return component;
	}

}
