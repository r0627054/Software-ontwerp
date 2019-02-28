package ui.model.window;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class CanvasWindowRecording {
	
	ArrayList<RecordingItem> items = new ArrayList<>();
	
	CanvasWindowRecording() {}
	
	CanvasWindowRecording(String path) throws IOException {
		load(path);
	}
	
	void save(String path) throws IOException {
		try (PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(path)))) {
			save(path, writer);
		}
	}
	
	void save(String basePath, PrintWriter writer) throws IOException {
		int itemIndex = 0;
		for (RecordingItem item : items)
			item.save(basePath, itemIndex++, writer);
	}
	
	void load(String path) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
			load(path, reader);
		}
	}
	
	void load(String basePath, BufferedReader reader) throws IOException {
		Component dummyComponent = new JPanel();
		for (int itemIndex = 0;; itemIndex++) {
			String line = reader.readLine();
			if (line == null) break;
			String[] words = line.split(" ");
			switch (words[0]) {
			case "MouseEvent": {
				int id;
				switch (words[1]) {
				case "MOUSE_PRESSED": id = MouseEvent.MOUSE_PRESSED; break;
				case "MOUSE_CLICKED": id = MouseEvent.MOUSE_CLICKED; break;
				case "MOUSE_RELEASED": id = MouseEvent.MOUSE_RELEASED; break;
				case "MOUSE_DRAGGED": id = MouseEvent.MOUSE_DRAGGED; break;
				default: throw new AssertionError();
				}
				int x = Integer.parseInt(words[2]);
				int y = Integer.parseInt(words[3]);
				int clickCount = Integer.parseInt(words[4]);
				items.add(new MouseEventItem(id, x, y, clickCount));
				break;
			}
			case "KeyEvent": {
				int id;
				switch (words[1]) {
				case "KEY_PRESSED": id = KeyEvent.KEY_PRESSED; break;
				case "KEY_TYPED": id = KeyEvent.KEY_TYPED; break;
				default: throw new AssertionError();
				}
				int keyCode = Integer.parseInt(words[2]);
				char keyChar = (char)Integer.parseInt(words[3]);
				items.add(new KeyEventItem(id, keyCode, keyChar));
				break;
			}
			case "Paint": {
				String imagePath = PaintItem.imagePathOf(basePath, itemIndex);
				items.add(new PaintItem(ImageIO.read(new File(imagePath))));
				break;
			}
			default: throw new AssertionError();
			}
		}
	}
	
	void replay(CanvasWindow window) {
		int itemIndex = 0;
		for (RecordingItem item : items) {
			item.replay(itemIndex++, window);
		}
	}
	
}
