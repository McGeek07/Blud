package blud.game.sprite.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import blud.game.sprite.Sprite;
import blud.util.Util;

public class Sprites {
	public static final String
		INDEX = "index";
	protected static final HashMap<String, Sprite>
 		SPRITES = new HashMap<>();
	
	public static final void load() {
		LinkedList<String> list = new LinkedList<String>();
		Util.parseFromResource(Sprites.class, INDEX, list);
		for(String line: list)
			if(!line.startsWith("//")) {
				String[] temp = line.split("\\,");
				String
					name = temp.length > 0 ? temp[0].trim() : "",
					w    = temp.length > 1 ? temp[1].trim() : "",
					h 	 = temp.length > 2 ? temp[2].trim() : "";
				int
					_w = Util.stringToInt(w),
					_h = Util.stringToInt(h);
				load(name, _w, _h);
			}		
	}
	
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
			BufferedImage atlas = ImageIO.read(Sprites.class.getResource(name + ".png"));			
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
						pixel = spriteFrames[i].getRGB(x, y);
					if(((pixel >> 24) & 0xff) > 0) {
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
	
	public static final Sprite get(String name) {
		Sprite sprite = SPRITES.get(name);
		return sprite != null ?
				sprite.copy() :
				null;
	}
}
