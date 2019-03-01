package ui.model.view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.model.viewmodes.TablesViewMode;
import ui.model.viewmodes.ViewMode;
import ui.model.window.CanvasWindow;

public class View extends CanvasWindow {

	private ViewMode currentMode;
	private Map<String,ViewMode> viewModes = new HashMap<String,ViewMode>();
	
	
	public View(String title) {
		super(title);
		initTestingModes();
	}
	
	private void initTestingModes() {
		TablesViewMode tablesViewMode1 = new TablesViewMode("Tables1");
		TablesViewMode tablesViewMode2 = new TablesViewMode("Tables2");
		addViewMode(tablesViewMode1);
		addViewMode(tablesViewMode2);
		changeModeTo("Tables1");
	}

	public void paint(Graphics g) {
		this.currentMode.paint(g);
	}

	public ViewMode getCurrentMode() {
		return currentMode;
	}
	
	public void changeModeTo(String key) {
		ViewMode newCurrentMode = viewModes.get(key);
		if(newCurrentMode == null) {
			throw new IllegalArgumentException("No mode found for key");
		} else this.setCurrentMode(newCurrentMode);
		
	}
	
	public void addViewMode(ViewMode mode) {
		if(this.viewModes.containsKey(mode.getName())) {
			throw new IllegalArgumentException("Mode with duplicate name already exists");
		} else this.viewModes.put(mode.getName(), mode);
	}

	private void setCurrentMode(ViewMode currentMode) {
		this.currentMode = currentMode;
	}
	
	

}
