package ui.model.view;

import java.util.List;

public class UIFacade implements UIFacadeInterface {
	
	private static UIFacade uifInstance = null;
	private View view;
	
	
	private UIFacade() {
		this.view = new View("Tablr");
	}
	/**
	* Creates an UIFacade instalce only once.
	* Returns the only existing instance.
	*
	* @post uifInstance is instansiated
	* | new.getInstance == UIFacadeInstance
	*
	* @notes
	* synchronized makes sure that every thread is synchronized and
	* prevents creating another instance in a other thread.
	**/
	
	public static synchronized UIFacade getInstance() {
		if (uifInstance == null) uifInstance = new UIFacade();
		return uifInstance;
	}
	
	public void show() {
		this.view.show();
	}
	
	@Override
	public void setTableNames(List<String> tableNames) {
		view.setTablesViewModeListValues(tableNames);
	}

}
