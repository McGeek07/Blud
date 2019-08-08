package blud.game.menu.component.components;

import java.awt.Color;

import blud.core.input.Input;
import blud.game.menu.component.Component;

public class Button extends Component {
	public boolean
		hasPress;
	public Color
		color1 = Color.BLACK,
		color2 = Color.RED;
	public Action
		action;
	
	public Button(String text, Action action) {
		super(text);
		this.action = action;
	}
	
	@Override
	public void onMouseHover() {
		
	}
	
	@Override
	public void onMouseLeave() {
		hasPress = false;
	}
	
	@Override
	public void onBtnDnAction(int btn) {
		if(btn == Input.BTN_1)
			hasPress = true;
	}
	
	@Override
	public void onBtnUpAction(int btn) {
		if(btn == Input.BTN_1)
			hasPress = false;
		if(action != null)
			action.perform();
	}
	
	@Override
	public void onRender(RenderContext context) {
		context.g2D.setColor(hasPress ? color2.darker() : hasHover ? color2 : color1);
		context.g2D.fillRect(loc.x(), loc.y(), dim.x(), dim.y());
		context.g2D.setColor(hasPress ? color1.darker() : hasHover ? color1 : color2);
		context.g2D.drawRect(loc.x(), loc.y(), dim.x(), dim.y());
		color = hasHover ? BLACK : DEFAULT;
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		
	}
	
	public static interface Action {
		public void perform();
	}
}
