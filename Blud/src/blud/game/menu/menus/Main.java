package blud.game.menu.menus;

import blud.core.Engine;
import blud.game.level.levels.Levels;
import blud.game.menu.Menu;
import blud.game.menu.component.components.Button;
import blud.game.menu.component.components.Button.Action;
import blud.game.menu.component.components.Label;

public class Main extends Menu {
	public static final Action
		PLAY_ACTION = () -> {			
			Engine.setScene(Levels.get());
		},
		INFO_ACTION = () -> {
			Engine.setScene(Menus.INFO);
		},
		QUIT_ACTION = () -> {
			Engine.exit();
		};
	public Main() {
		Label label = new Label("Blud");
		Button play_button = new Button("Play", PLAY_ACTION);
		Button info_button = new Button("Info", INFO_ACTION);
		Button quit_button = new Button("Quit", QUIT_ACTION);
		
		label.loc.set(19,  6);
		label.dim.set(26,  8);
		play_button.loc.set(19, 19);
		play_button.dim.set(26,  8);
		info_button.loc.set(19, 29);
		info_button.dim.set(26,  8);
		quit_button.loc.set(19, 39);
		quit_button.dim.set(26,  8);
		
		children.add(label);
		children.add(play_button);
		children.add(info_button);
		children.add(quit_button);
	}
	
	@Override
	public void onAttach() {
		super.onAttach();
		if(!Menus.TRACK0.isPlaying())
			Menus.TRACK0.loop(1f);
	}
}
