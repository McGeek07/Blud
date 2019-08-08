package blud.game.level.tile.tiles;

import blud.game.level.tile.Tile;
import blud.game.sprite.sprites.Sprites;

public class SewerPlankTile extends Tile {
	
	public SewerPlankTile() {
		super();
		sprites.add(Sprites.get("SewerPlankTile"));
	}
	
	@Override
	public void onRender1(RenderContext context) {
		if(node != null) {
			int
				i = node.local.x(),
				j = node.local.y();
			sprites.setFrame((i + j) & 1);
		}
	}
	
}
