package blud.game.level.tile.tiles;

import blud.game.level.tile.Tile;
import blud.game.sprite.sprites.Sprites;

public class HellTile extends Tile {
	
	public HellTile() {
		super();
		sprites.add(Sprites.get("HellTile"));
	}
	
	@Override
	public void onRender1(RenderContext context) {
		
	}

}
