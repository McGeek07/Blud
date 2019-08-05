package blud.game.level.tile.tiles;

import blud.game.level.tile.Tile;
import blud.game.sprite.sprites.Sprites;

public class SewerTileWater extends Tile{

	public SewerTileWater() {
		super();
		sprites.add(Sprites.get("SewerTileWater"));
		sprites.loop(0, 2f);
	}
	
	@Override
	public void onRender(RenderContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate(UpdateContext context) {
		// TODO Auto-generated method stub
		
	}

}
