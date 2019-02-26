package ui.model;

import java.io.IOException;
import java.io.PrintWriter;

abstract class RecordingItem {
	abstract void save(String path, int itemIndex, PrintWriter writer) throws IOException;
	abstract void replay(int itemIndex, CanvasWindow window);
}