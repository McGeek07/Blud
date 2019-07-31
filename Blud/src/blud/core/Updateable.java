package blud.core;

public interface Updateable {
	public void update(UpdateContext context);
	
	public static class UpdateContext {
		public int
			canvas_w,
			canvas_h;
		public float 
			dt;
	}
}
