package blud.game.menu.menus;

import blud.core.Engine;
import blud.game.menu.Menu;
import blud.game.sprite.sprites.Sprites;

public class Splash extends Menu {
	public Splash() {
		bg = Sprites.get("Splash");
		bg.play(4f);
	}
	
	@Override
	public void onKeyDnAction(int key) {
		bg.stop();
		Engine.setScene(Menus.MAIN);
	}	
	
	@Override
	public void onUpdate(UpdateContext context) {
		if(bg.frame + bg.speed * context.dt >= bg.spriteFrames.length) {
			bg.stop();
			Engine.setScene(Menus.MAIN);
		}
		super.onUpdate(context);		
	}
	
	@Override
	public void onAttach() {
		super.onAttach();
		if(!Menus.TRACK0.isPlaying())
			Menus.TRACK0.loop(.9f);
	}
}
