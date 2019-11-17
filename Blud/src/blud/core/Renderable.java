package blud.core;

import java.awt.Graphics2D;

import blud.util.Copyable;

public interface Renderable {
	public void render(RenderContext context);
	
	public static class RenderContext implements Copyable<RenderContext>{
		public Graphics2D
			g2D;
		public int
			canvas_w,
			canvas_h;
		public float
			dt,
			t;
		
		private RenderContext
			parent;
		
		public RenderContext push() {
			RenderContext copy = this.copy();
			copy.parent = this;
			return copy;
		}
		
		public RenderContext pop() {
			if(this.parent != null)
				try {
					this.g2D.dispose();
					return this.parent;
				} finally {
					this.parent = null;
				}		
			return this;
		}

		@Override
		public RenderContext copy() {
			RenderContext copy = new RenderContext();
			copy.g2D = (Graphics2D)this.g2D.create();
			copy.canvas_w = this.canvas_w;
			copy.canvas_h = this.canvas_h;
			copy.dt = this.dt;
			copy.t  =  this.t;
			return copy;
		}
	}
}
