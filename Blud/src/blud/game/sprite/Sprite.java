package blud.game.sprite;

import java.awt.image.BufferedImage;

import blud.core.Renderable;
import blud.core.Updateable;

public class Sprite implements Renderable, Updateable {
	protected BufferedImage[]
		frames;
	protected int
		frame;

	@Override
	public void render(RenderContext context) {
		context.g2D.drawImage(
				frames[frame],
				null,
				0, 
				0
				);
	}
	
	@Override
	public void update(UpdateContext context) {
		
	}
}
