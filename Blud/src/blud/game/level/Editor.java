package blud.game.level;

import java.awt.Color;

import blud.core.input.Input;
import blud.game.Game;
import blud.game.sprite.Sprite;
import blud.geom.Vector;
import blud.geom.Vector2f;

public class Editor extends Level {
	protected final Vector2f.Mutable
		brush_center = new Vector2f.Mutable();
	protected final Sprite.Group
		brush_sprite = new Sprite.Group(
				Sprite.get("select_r"),
				Sprite.get("select_g"),
				Sprite.get("select_b")
				);
	
	public Editor() {
		super();
		brush_sprite.loop(0, 4f);
		brush_sprite.loop(1, 4f);
		brush_sprite.setShadowTransparency(1f);
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
		context.g2D.translate(
				context.canvas_w / 2,
				context.canvas_h / 2
				);
		
		for(int i = 0; i < LEVEL_W; i ++)
			for(int j = 0; j < LEVEL_H; j ++) {
				if(grid[i][j].tile != null)
					grid[i][j].tile.render(context);
				if(
						i == brush_center.x() && 
						j == brush_center.y()
						) {
					context = context.push();
					Vector2f pixel = Game.localToPixel(brush_center);
					context.g2D.translate(
							pixel.x(),
							pixel.y()
							);
					brush_sprite.render(context);					
					context = context.pull();
				}
				if(grid[i][j].entity != null)
					grid[i][j].entity.render(context);
			}
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		if(Input.isKeyDnAction(Input.KEY_W) && brush_center.y() > 0)
			Vector.add(brush_center, 0, - 1);
		if(Input.isKeyDnAction(Input.KEY_A) && brush_center.x() > 0)
			Vector.add(brush_center, - 1, 0);
		if(Input.isKeyDnAction(Input.KEY_S) && brush_center.y() < LEVEL_H - 1)
			Vector.add(brush_center, 0, + 1);
		if(Input.isKeyDnAction(Input.KEY_D) && brush_center.x() < LEVEL_W - 1)
			Vector.add(brush_center, + 1, 0);
		context = context.push();
		brush_sprite.update(context);		
		context = context.pull();
	}
}
