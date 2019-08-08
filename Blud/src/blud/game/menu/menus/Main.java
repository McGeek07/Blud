package blud.game.menu.menus;

import blud.core.Engine;
import blud.game.level.Editor;
import blud.game.menu.Menu;
import blud.game.menu.component.Component;
import blud.game.menu.component.components.Button;
import blud.game.menu.component.components.Button.Action;
import blud.game.menu.component.components.Label;

public class Main extends Menu {
	public static final Action
		PLAY_ACTION = () -> {
			Engine.setScene(new Editor("level.txt"));
		},
		INFO_ACTION = () -> {
			Engine.setScene(Menus.INFO);
		},
		QUIT_ACTION = () -> {
			Engine.exit();
		};
	public Main() {
		Label label = new Label("Blud");
		label.loc.set(17, 8);
		label.dim.set(28,12);		
		children.add( label);
		children.add(Component.format(new Button("Play"	, PLAY_ACTION) , 0, 2, 7, 1, 1));
		children.add(Component.format(new Button("Info"	, INFO_ACTION) , 0, 3, 7, 1, 1));
		children.add(Component.format(new Button("Quit"	, QUIT_ACTION) , 0, 4, 7, 1, 1));
	}
}
