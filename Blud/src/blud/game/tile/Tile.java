package blud.game.tile;

import blud.game.sprite.Sprite;
import blud.geom.Vector;

public abstract class Tile extends blud.game.level.Object {
	
	public Tile(Sprite... sprites) {
		super(sprites);
	}
	
	public Tile(Vector local, Sprite... sprites) {
		super(local, sprites);
	}
	
	public Tile(float i, float j, Sprite... sprites) {
		super(i, j, sprites);
	}
	
	public static class Debug extends Tile {
		
		public Debug(float i, float j) {
			super(i, j, Sprite.get("Tile"));
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
	
}
