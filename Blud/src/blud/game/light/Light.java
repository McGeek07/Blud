package blud.game.light;

import blud.game.Game;
import blud.geom.Vector;
import blud.geom.Vector2f;

public class Light {
	protected Vector2f.Mutable
		local = new Vector2f.Mutable(),
		pixel = new Vector2f.Mutable();		
	
	public void setLocal(Vector v) {
		this.setLocal(v.X(), v.Y());
	}
	
	public void setLocal(float x, float y) {
		this.local.set(x, y);
		this.pixel.set(Game.localToPixel(x, y));
	}
	
	public void setPixel(Vector v) {
		this.setPixel(v.X(), v.Y());
	}
	
	public void setPixel(float x, float y) {
		this.pixel.set(x, y);
		this.local.set(Game.pixelToLocal(x, y));
	}
	
	public Vector2f local() {
		return this.local;
	}
	
	public Vector2f pixel() {
		return this.pixel;
	}
}
