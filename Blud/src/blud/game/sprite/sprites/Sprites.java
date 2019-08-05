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
			BufferedImage[]
					spriteFrames = createSpriteFrames(name, w, h),
					whiteFrames = createWhiteFrames(spriteFrames),
					blackFrames = createBlackFrames(spriteFrames);
			Sprite sprite = new Sprite(
					name,
					w,
					h,
					spriteFrames,
					whiteFrames,
					blackFrames
					);
			SPRITES.put(name, sprite);
			return sprite;
	}
	
	private static final BufferedImage[] createSpriteFrames(String name, int w, int h) {
		try {
			BufferedImage atlas = ImageIO.read(Sprite.class.getResource("sprites/" + name + ".png"));			
			int
				xw = atlas.getWidth()  / w,
				yh = atlas.getHeight() / h;
			BufferedImage[] spriteFrames = new BufferedImage[xw * yh];
			for(int x = 0; x < xw; x ++)
				for(int y = 0; y < yh; y ++)
					spriteFrames[yh * y + x] = atlas.getSubimage(
							x * w,
							y * h,
							w , h
							);			
			return spriteFrames;
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
	
	private static final BufferedImage[] createWhiteFrames(BufferedImage[] spriteFrames) {
		return createColorFrames(spriteFrames, 255, 255, 255, 255);
	}
	
	private static final BufferedImage[] createBlackFrames(BufferedImage[] spriteFrames) {
		return createColorFrames(spriteFrames, 255,   0,   0,   0);
	}
	
	private static final BufferedImage[] createColorFrames(BufferedImage[] spriteFrames, int a, int r, int g, int b) {
		BufferedImage[] colorFrames = new BufferedImage[spriteFrames.length];
		for(int i = 0; i < spriteFrames.length; i ++) {
			int
				w = spriteFrames[i].getWidth() ,
				h = spriteFrames[i].getHeight();
			colorFrames[i] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			for(int x = 0; x < w; x ++)
				for(int y = 0; y < h; y ++) {
					int
						pixel = spriteFrames[i].getRGB(x, y),
						_a = (pixel >> 24) & 0xff,
						_r = (pixel >> 16) & 0xff,
						_g = (pixel >>  8) & 0xff,
						_b = (pixel      ) & 0xff;
					if(_a > 0) {
						pixel = 
								(a << 24) |
								(r << 16) |
								(g <<  8) |
								(b      ) ;
						colorFrames[i].setRGB(x, y, pixel);
					}						
				}					
		}
		return colorFrames;
	}
	
	public static final Sprite get(String id) {
		Sprite sprite = SPRITES.get(id);
		return sprite != null ?
				sprite.copy() :
				null;
	}
}
