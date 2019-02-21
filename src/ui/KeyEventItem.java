package ui;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;

class KeyEventItem extends RecordingItem {
	int id;
	int keyCode;
	char keyChar;
	
	KeyEventItem(int id, int keyCode, char keyChar) {
		this.id = id;
		this.keyCode = keyCode;
		this.keyChar = keyChar;
	}

	@Override
	void save(String path, int itemIndex, PrintWriter writer) throws IOException {
		String id;
		switch (this.id) {
		case KeyEvent.KEY_PRESSED: id = "KEY_PRESSED"; break;
		case KeyEvent.KEY_TYPED: id = "KEY_TYPED"; break;
		default: id = "unknown"; break;
		}
		writer.println("KeyEvent " + id + " " + keyCode + " " + (int)keyChar);
	}

	@Override
	void replay(int itemIndex, CanvasWindow window) {
		window.handleKeyEvent(id, keyCode, keyChar);
	}
}
