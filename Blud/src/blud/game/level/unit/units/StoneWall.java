package blud.game.level.unit.units;

import blud.game.level.unit.Wall;
import blud.game.sprite.sprites.Sprites;

public class StoneWall extends Wall{

	public StoneWall() {
		super(Sprites.get("DebugWall"));
		this.blocksPlayerVision = true;
		this.blocksEntityVision = true;
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
