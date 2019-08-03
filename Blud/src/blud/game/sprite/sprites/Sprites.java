package blud.game.sprite.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import blud.game.sprite.Sprite;

public class Sprites {
	protected static final HashMap<String, Sprite>
 		SPRITES = new HashMap<>();
	
	public static final Sprite load(String name, int w, int h) {
		try {
			BufferedImage atlas = ImageIO.read(Sprite.class.getResource("sprites/" + name + ".png"));
			Sprite sprite = new Sprite(
					name,
					createSpriteFrames(atlas, w, h),
					createShadowFrames(atlas, w, h)
					);
			SPRITES.put(name, sprite);
			return sprite;
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
	
	private static final BufferedImage[] createSpriteFrames(BufferedImage atlas, int w, int h) {
		int
			_w = atlas.getWidth()  / w,
			_h = atlas.getHeight() / h;
		BufferedImage[] sprite_frames = new BufferedImage[_w * _h];
		for(int x = 0; x < _w; x ++)
			for(int y = 0; y < _h; y ++)
				sprite_frames[_h * y + x] = atlas.getSubimage(
						x * w,
						y * h,
						w , h
						);
		return sprite_frames;
	}
	
	@SuppressWarnings("unused")
	private static final BufferedImage[] createShadowFrames(BufferedImage atlas, int w, int h) {
		int
			_w = atlas.getWidth()  / w,
			_h = atlas.getHeight() / h;
		BufferedImage[] shadow_frames = new BufferedImage[_w * _h];
		for(int x = 0; x < _w; x ++)
			for(int y = 0; y < _h; y ++)
				shadow_frames[_h * y + x] = atlas.getSubimage(
						x * w,
						y * h,
						w , h
						);
		for(int i = 0; i < shadow_frames.length; i ++) {
			BufferedImage shadow_frame = new BufferedImage(
					w, h, BufferedImage.TYPE_INT_ARGB
					);
			for(int x = 0; x < w; x ++)
				for(int y = 0; y < h; y ++) {
					int 
						p = shadow_frames[i].getRGB(x, y),
						a = (p >> 24) & 0xff,
						r = (p >> 16) & 0xff,
						g = (p >>  8) & 0xff,
						b = (p      ) & 0xff;
					if(a > 0)
						shadow_frame.setRGB(x, y, 255 << 24);
				}
			shadow_frames[i] = shadow_frame;
		}
		return shadow_frames;
	}
	
	public static final Sprite get(String id) {
		Sprite sprite = SPRITES.get(id);
		return sprite != null ?
				sprite.copy() :
				null;
	}
}
