package blud.game.menu.menus;

import java.awt.Color;

import blud.core.Engine;
import blud.game.level.Level;
import blud.game.menu.Menu;
import blud.game.menu.component.Component;
import blud.game.menu.component.components.Button;
import blud.game.menu.component.components.Button.Action;
import blud.game.menu.component.components.Label;

public class Pause extends Menu {
	protected Level
		level;	
	protected final Action
		PLAY_ACTION = () -> {			
			Engine.setScene(level);
		},
		MENU_ACTION = () -> {
			Engine.setScene(Menus.MAIN);
		},
		QUIT_ACTION = () -> {
			Engine.exit();
		};
	public Pause(Level level) {
		this.level = level;
		Label label = new Label("Pause");
		Button play_button = new Button("Play", PLAY_ACTION);
		Button menu_button = new Button("Menu", MENU_ACTION);
		Button quit_button = new Button("Quit", QUIT_ACTION);
		
		label.loc.set(16,  6);
		label.dim.set(32,  8);
		play_button.loc.set(19, 19);
		play_button.dim.set(26,  8);
		menu_button.loc.set(19, 29);
		menu_button.dim.set(26,  8);
		quit_button.loc.set(19, 39);
		quit_button.dim.set(26,  8);
		
		children.add(label);
		children.add(play_button);
		children.add(menu_button);
		children.add(quit_button);
	}
	
	@Override
	public void onRender(RenderContext context) {
		level.render(context);
		context.g2D.setColor(new Color(0f, 0f, 0f, .5f));
		context.g2D.fillRect(
				0,
				0,
				context.canvas_w,
				context.canvas_h
				);
		for(Component child: children)
			child.render(context);
	}
}
