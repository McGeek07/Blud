package blud.game.menu.menus;

import blud.core.Engine;
import blud.game.menu.Menu;
import blud.game.menu.component.components.Button.Action;
import blud.game.menu.component.components.Label;

public class Info extends Menu {
	public static final Action
		BACK_ACTION = () -> {
			Engine.setScene(Menus.MAIN);
		};
	public Info() {
		Label label = new Label("Info");
		label.loc.set(17, 8);
		label.dim.set(28,12);
	}
}
