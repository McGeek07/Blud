package blud.core.scene;

import blud.core.Renderable;
import blud.core.Updateable;

public class Scene implements Renderable, Updateable {
	
	public void onInit() { }	
	public void onExit() { }	
	public void onAttach() { }	
	public void onDetach() { }
	public void onMouseMoved(int x, int y) { }
	public void onWheelMoved(int wheel) { }
	public void onKeyDnAction(int key) { }
	public void onKeyUpAction(int key) { }
	public void onBtnDnAction(int btn, int x, int y) { }
	public void onBtnUpAction(int btn, int x, int y) { }	
	public void onRender(RenderContext context) { }	
	public void onUpdate(UpdateContext context) { }

	@Override
	public void render(RenderContext context) {
		context = context.push();
		this.onRender(context);
		context = context.pop();
	}

	@Override
	public void update(UpdateContext context) {
		context = context.push();
		this.onUpdate(context);
		context = context.pop();
	}
}
