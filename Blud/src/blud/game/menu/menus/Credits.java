package blud.game.menu.menus;

import java.awt.Color;

import blud.core.Engine;
import blud.game.menu.Menu;
import blud.game.menu.component.Component;
import blud.game.sound.Sound;
import blud.game.sound.sounds.Sounds;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;

public class Credits extends Menu {
	public final Sprite
		splash = Sprites.get("Splash");
	public final Sound
		track = Sounds.get("Track4-3");
	public final String
		text =  
				" CREDITS  " +
				"          " +
				"CONCEPT   " +
				" CAEDEN   " +
				"          " +
				"ENGINE    " +
				" MCGEEK07 " +
				"          " +
				"DESIGN    " +
				" GHOSTTSO " +
				"          " +
				"MUSIC     " +
				" MCGEEK07 " + 
				"          " +
				"ASSETS    " +
				" GHOSTTSO " + 
				"          " +
				"LIGHTING  " +
				" MCGEEK07 " +
				"          " +
				"LEVELS    " +
				" GHOSTTSO " +
				"          " +
				"PLAYTEST  " +
				" CAEDEN   " +
				" SKULLGRIN" +
				" JUDGEFIRE" +
				"          " +
				"          " +
				"          " +
				"          " +
				"THANK YOU " +
				"   FOR    " + 
				" PLAYING  ";// +
//			    "          " +
//			    "          " +
//			    "          " +
//			    "          " +
//			    "          " +
//			    "          " +
//			    "          " +
//			    "          " +		
//			    "          " +	
//			    "          " +	
//			    "          " +	
//			    "          " +
//			    "          " +
//			    "          " +
//			    "SCREENPLAY" +
//			    " YOUR MOM " +
//			    "          " +
//				"EDITOR    " +
//				" YOURE MOM" + 
//				"          " +
//				"CARRY     " +
//				" HANZO    " +
//				" HANZO    " +
//				" HANZO    " +
//				" HANZO    " +
//				" HANZO    " +
//				"          " + 
//				"SUPPORT   " +
//				" MERCY    ";
				
				
	public float
		offset,
		scroll = 6f;
	
	@Override
	public void onKeyDnAction(int key) {
		if(splash.mode > 0)
			splash.stop();
		else
			Engine.setScene(Menus.MAIN);
	}
	
	
	@Override
	public void onRender(RenderContext context) {
		context.g2D.setColor(Color.BLACK);
		context.g2D.fillRect(
				0, 0,
				context.canvas_w,
				context.canvas_h
				);
		if(splash.mode > 0) {
			context.g2D.translate(
					context.canvas_w / 2,
					context.canvas_h / 2
					);
			
				splash.render(context);
		} else {
			context.g2D.translate(0, offset);
			Component.drawText(context, text, 0, 2, 2, 62, 4096, Component.WHITE, 0f);
		}
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		if(splash.mode > 0)
			splash.update(context);
		else
			offset -= context.dt * scroll;
	}
	
	@Override
	public void onAttach() {
		splash.frame = 0;
		splash.play(4f);
		offset = 64f;
		track.loop(.9f);
	}
	
	@Override
	public void onDetach() {
		track.stop();
	}
}
