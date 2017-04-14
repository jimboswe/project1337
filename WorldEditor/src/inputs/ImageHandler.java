package inputs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageHandler implements ImageObserver {
	public static BufferedImage TABLE = null, GRASS = null;
	private static HashMap<Type, ImageCoord> img = new HashMap<Type, ImageCoord>();

	ImageHandler() {
	}

	public static void init() {
		TABLE = loadImage(new File("resources\\table.png"));
		GRASS = loadImage(new File("resources\\grass.png"));
		
		img.put(Type.TREE1, new ImageCoord(0, 0, 64, 64, 80));
		img.put(Type.TREE2, new ImageCoord(4, 0, 64, 64, 80));
		img.put(Type.TREE3, new ImageCoord(5, 0, 64, 64, 80));
		img.put(Type.TREE4, new ImageCoord(6, 0, 64, 64, 80));
		img.put(Type.TREE5, new ImageCoord(7, 0, 64, 64, 80));
		img.put(Type.TREE6, new ImageCoord(8, 0, 64, 64, 80));
		img.put(Type.TREE7, new ImageCoord(9, 0, 64, 64, 80));
		img.put(Type.STONE1, new ImageCoord(0, 1, 64, 64, 0));
		img.put(Type.STONE2, new ImageCoord(1, 1, 64, 64, 0));
		img.put(Type.STONE3, new ImageCoord(2, 1, 64, 64, 0));
		img.put(Type.STONE4, new ImageCoord(3, 1, 64, 64, 0));
		img.put(Type.ZOMBIE, new ImageCoord(0, 2, 64, 64, 200));
		img.put(Type.ARROW, new ImageCoord(0, 3, 64, 8, 200));
		img.put(Type.BOW, new ImageCoord(0, 4, 64, 64, 100));
		img.put(Type.BLOOD, new ImageCoord(0, 5, 64, 64, 50));
		img.put(Type.EXPLOSION, new ImageCoord(0, 6, 64, 64, 50));
		img.put(Type.PISTOL, new ImageCoord(0, 7, 64, 30, 80));
		img.put(Type.BULLET, new ImageCoord(0, 8, 4, 3, 80));
		img.put(Type.MUZZLEFLASH, new ImageCoord(0, 9, 34, 21, 80));
		img.put(Type.UZI, new ImageCoord(0, 10, 64, 38, 80));
		img.put(Type.GRASS, new ImageCoord(0, 0, GRASS.getWidth(), GRASS.getHeight(), 0));
	}

	public static BufferedImage getImage(Type type) {
		switch (type) {
		case GRASS:
			return GRASS;
		default:
			return TABLE;
		}
	}
	
	public static ImageCoord getImageCoord(Type type) {
		return img.get(type);
	}

	private static BufferedImage loadImage(File file) {
		BufferedImage source = null;
		try {
			source = ImageIO.read(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int color = source.getRGB(1, 1); // Hämtar färgen från 1, 1 på bilden (Innanför rutmönstret)
		Image image = makeColorTransparent(source, new Color(color));
		return imageToBufferedImage(image);
	}

	private static BufferedImage imageToBufferedImage(Image image) {

		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();

		return bufferedImage;
	}

	public static Image makeColorTransparent(BufferedImage im, final Color color) {
		RGBImageFilter filter = new RGBImageFilter() {

			// the color we are looking for... Alpha bits are set to opaque
			public int markerRGB = color.getRGB() | 0xFF000000;

			@Override
			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) {
					// Mark the alpha bits as zero - transparent
					return 0x00FFFFFF & rgb;
				} else {
					// nothing to do
					return rgb;
				}
			}
		};

		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
}
