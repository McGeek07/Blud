package blud.core;

import blud.util.Copyable;

public interface Updateable {
	public void update(UpdateContext context);
	
	public static class UpdateContext implements Copyable<UpdateContext> {
		public int
			canvas_w,
			canvas_h;
		public float 
			dt,
			t;
		
		private UpdateContext
			parent;
		
		public UpdateContext push() {
			UpdateContext copy = this.copy();
			copy.parent = this;
			return copy;
		}
		
		public UpdateContext pull() {
			if(this.parent != null) {
				return this.parent;
			}
			return this;
		}

		@Override
		public UpdateContext copy() {
			UpdateContext copy = new UpdateContext();
			copy.canvas_w = this.canvas_w;
			copy.canvas_h = this.canvas_h;
			copy.dt = this.dt;
			copy.t  =  this.t;
			return copy;
		}
	}
}
