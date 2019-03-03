package ui.model.view;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import ui.model.viewmodes.TablesViewMode;
import ui.model.viewmodes.ViewMode;
import ui.model.window.CanvasWindow;

public class View extends CanvasWindow {

	private ViewMode currentMode;
	private Map<String, ViewMode> viewModes = new HashMap<String, ViewMode>();

	public View(String title) {
		super(title);
		initTestingModes();
	}

	private void initTestingModes() {
		TablesViewMode tablesViewMode1 = new TablesViewMode("Tables1", this);
		TablesViewMode tablesViewMode2 = new TablesViewMode("Tables2", this);
		addViewMode(tablesViewMode1);
		addViewMode(tablesViewMode2);
		changeModeTo("Tables1");
	}

	@Override
	public void paint(Graphics g) {
		this.currentMode.paint(g);
	}

	public void repaintTest() {
		this.repaint();
	}

	public ViewMode getCurrentMode() {
		return currentMode;
	}

	public void changeModeTo(String key) {
		ViewMode newCurrentMode = viewModes.get(key);
		if (newCurrentMode == null) {
			throw new IllegalArgumentException("No mode found for key");
		} else
			this.setCurrentMode(newCurrentMode);

	}

	public void addViewMode(ViewMode mode) {
		if (this.viewModes.containsKey(mode.getName())) {
			throw new IllegalArgumentException("Mode with duplicate name already exists");
		} else
			this.viewModes.put(mode.getName(), mode);
	}

	private void setCurrentMode(ViewMode currentMode) {
		this.currentMode = currentMode;
	}

	@Override
	protected void handleMouseEvent(int id, int x, int y, int clickCount) {
		currentMode.mouseClicked(id, x, y, clickCount);

		this.repaint();
		// Steven: Ik denk niet dat dit de juiste plaats is om te repainten?
		// Weet niet goed hoe ik terug vanaf de Components naar de View kan callen om te
		// repainten.
		// Eventueel Observer gebruiken?

		// Dit zorgt momenteel voor elke mouse click/drag/release voor een volledige
		// repaint
	}

	@Override
	protected void handleKeyEvent(int id, int keyCode, char keyChar) {
		currentMode.keyPressed(id, keyCode, keyChar);
		
		this.repaint();
		//Same as handleMouseEvent
		//Dit is enkel om te testen/tonen dat EditableTextField werkt!
	}

}
