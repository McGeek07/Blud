package blud.game.sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Sprites {
	public static final String
		FILE_DIRECTORY = "Sprites" + File.separator,
		FILE_EXTENSION = ".png";
	public static final int
		TILE_SPRITE_W = 11, 
		TILE_SPRITE_H = 12;
	private static final HashMap<String, BufferedImage[]>
			 INDEX = new HashMap<String, BufferedImage[]>();
		
	protected Sprites() {
		//do nothing
	}
	
	public static final Sprite getTileSprite(String id) {
		BufferedImage[] frames = INDEX.get(id);
		if(frames == null) {
			try {
				BufferedImage image = ImageIO.read(new File(
						FILE_DIRECTORY + id + FILE_EXTENSION
						));
				frames = new BufferedImage[image.getWidth() / TILE_SPRITE_W];
				for(int i = 0; i < image.getWidth(); i += TILE_SPRITE_W)
					frames[i] = image.getSubimage(
							i,
							0,
							TILE_SPRITE_W,
							TILE_SPRITE_H
							);
				INDEX.put(id, frames);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		Sprite sprite = new Sprite();
		sprite.frames = frames;
		return sprite;
	}
}
