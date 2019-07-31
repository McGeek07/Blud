package blud.core;

import java.awt.Graphics2D;

public interface Renderable {
	public void render(RenderContext context);
	
	public static class RenderContext {
		public Graphics2D
			g2D;
		public int
			canvas_w,
			canvas_h;
		public float
			dt;
	}
}
