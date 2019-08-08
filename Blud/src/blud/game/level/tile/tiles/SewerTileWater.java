package blud.game.level.tile.tiles;

import blud.game.level.tile.Tile;
import blud.game.sprite.sprites.Sprites;

public class SewerTileWater extends Tile {

	public SewerTileWater() {
		super();
		sprites.add(Sprites.get("SewerTileWater"));
	}
	
	@Override
	public void onRender1(RenderContext context) {
		if(node != null) {
			int
				i = node.local.x(),
				j = node.local.y();
			sprites.setFrame((int)(3 * context.t + ((i + j) & 1)) % 2);
		}
	}

}
