package ui.model.view;

import java.util.ArrayList;
import java.util.List;

import ui.model.viewmodes.ViewMode;
import ui.model.window.CanvasWindow;

public class View extends CanvasWindow {

	private ViewMode currentMode;
	private List<ViewMode> viewModes = new ArrayList<>();
	
	
	public View(String title) {
		super(title);
	}

}
