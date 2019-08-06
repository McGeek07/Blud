package blud.game.level.unit.units;

import blud.game.level.unit.Wall;
import blud.game.sprite.sprites.Sprites;

public class BookShelfRight extends Wall{

	public BookShelfRight() {
		super();
		sprites.add(Sprites.get("BookShelfRight"));
	}
	
	@Override
	public void onRender(RenderContext context) {
	}

	@Override
	public void onUpdate(UpdateContext context) {
	}

}
