package blud.game.menu.menus;

import blud.core.Engine;
import blud.game.menu.Menu;
import blud.game.menu.component.components.Button.Action;
import blud.game.menu.component.Component;
import blud.game.menu.component.components.Button;
import blud.game.menu.component.components.Label;

public class Info extends Menu {
	public static final Action
		BACK_ACTION = () -> {
			Engine.setScene(Menus.MAIN);
		};
	public Info() {
		Label label1 = new Label("Info");
		Label label2 = new Label(" W  MOVE/ ASD ATTACK");
		Label label3 = new Label("ESC PAUSE"           );
		Button back_button = new Button("Back", BACK_ACTION);
		label1.loc.set(19,  6);
		label1.dim.set(26,  8);
		label2.loc.set( 1, 16);
		label2.dim.set(63, 14);
		label2.color = Component.WHITE;
		label3.loc.set( 1, 36);
		label3.dim.set(63,  8);
		label3.color = Component.WHITE;
		back_button.loc.set(19, 49);
		back_button.dim.set(26,  8);
		
		this.children.add(label1);
		this.children.add(label2);
		this.children.add(label3);
		this.children.add(back_button);
	}
	
	@Override
	public void onAttach() {
		super.onAttach();
		Menus.TRACK0.loop(1f);
	}
}
