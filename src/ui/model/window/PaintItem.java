package ui.model.window;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

class PaintItem extends RecordingItem {
	BufferedImage image;
	
	PaintItem(BufferedImage image) {
		this.image = image;
	}
	
	static String imagePathOf(String basePath, int itemIndex) {
		return basePath + ".image" + itemIndex + ".png";
	}
	
	void save(String path, int itemIndex, PrintWriter writer) throws IOException {
		String imagePath = imagePathOf(path, itemIndex);
		javax.imageio.ImageIO.write(image, "PNG", new File(imagePath));
		writer.println("Paint");
	}
	
	void replay(int itemIndex, CanvasWindow window) {
		BufferedImage observedImage = window.captureImage();
		for (int y = 0; y < observedImage.getHeight(); y++) {
			for (int x = 0; x < observedImage.getWidth(); x++) {
				if (observedImage.getRGB(x, y) != image.getRGB(x, y)) {
					try {
						ImageIO.write(observedImage, "PNG", new File("observedImage"+itemIndex+".png"));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					throw new RuntimeException("Replay: Paint item " + itemIndex + " does not match at x=" + x + " and y=" + y + ".");
				}
			}
		}
	}
}
