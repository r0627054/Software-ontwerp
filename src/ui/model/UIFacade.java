package ui.model;

import java.util.ArrayList;
import java.util.List;

import ui.model.viewmodes.TableRowsMode;
import ui.model.viewmodes.TablesModeView;
import ui.model.viewmodes.ViewMode;

public class UIFacade implements UIFacadeInterface {

	private ViewMode currentMode;
	
	private TablesModeView tablesModeView = new TablesModeView();
	private List<TablesModeView> tableModeViews= new ArrayList<>();
	private List<TableRowsMode> rowModeView= new ArrayList<>();
	
	public UIFacade() {
		
	}
	
	public void show() {
	}

	public void addWindowSwitchListeners() {

	}
	
	private void switchWindowsToTableView() {

	}
}
