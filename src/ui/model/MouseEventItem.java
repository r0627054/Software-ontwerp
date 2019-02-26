package ui.model;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;

class MouseEventItem extends RecordingItem {
	int id;
	int x;
	int y;
	int clickCount;
	
	MouseEventItem(int id, int x, int y, int clickCount) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.clickCount = clickCount;
	}

	@Override
	void save(String path, int itemIndex, PrintWriter writer) throws IOException {
		String id;
		switch (this.id) {
		case MouseEvent.MOUSE_CLICKED: id = "MOUSE_CLICKED"; break;
		case MouseEvent.MOUSE_PRESSED: id = "MOUSE_PRESSED"; break;
		case MouseEvent.MOUSE_RELEASED: id = "MOUSE_RELEASED"; break;
		case MouseEvent.MOUSE_DRAGGED: id = "MOUSE_DRAGGED"; break;
		default: id = "unknown"; break;
		}
		writer.println("MouseEvent " + id + " " + x + " " + y + " " + clickCount);
	}

	@Override
	void replay(int itemIndex, CanvasWindow window) {
		window.handleMouseEvent(id, x, y, clickCount);
	}
}
