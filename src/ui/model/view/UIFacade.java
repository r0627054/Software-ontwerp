package ui.model.view;

public class UIFacade implements UIFacadeInterface {
	
	private static UIFacade uifInstance = null;
	private View view;
	
	
	private UIFacade() {
		this.view = new View("Tablr");
	}
	
	private static void createInstance() {
		if (uifInstance == null) uifInstance = new UIFacade();
	}
	
	public static UIFacade getInstance() {
		if (uifInstance == null) createInstance();
		return uifInstance;
	}
	
	public void show() {
		this.view.show();
	}

	public void addWindowSwitchListeners() {

	}
	
	private void switchWindowsToTableView() {
		
	}
}
