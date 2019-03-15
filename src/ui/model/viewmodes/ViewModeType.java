package ui.model.viewmodes;

/**
 * An enumeration introducing the different types of viewModes a view can have.
 *  In its current form, the class supports, TablesViewMode, TableRowsViewMode and TableDesignViewMode.
 *   
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public enum ViewModeType {

	TABLESVIEWMODE("tablesViewMode"),
	TABLEROWSVIEWMODE("TableRowsViewMode"),
	TABLEDESIGNVIEWMODE("TableDesignViewMode");
	
	/**
	 * Variable storing the name of the viewMode.
	 */
	private String name;
	
	/**
	 * Initialise a new ViewModeType with the given name.
	 *  
	 * @param name
	 *        | the name of the viewMode
	 * @post the name of the viewMode equals the name of the parameter
	 *        | new.getName() == name
	 */
	private ViewModeType(String name) {
		this.name= name;
	}
	
	/**
	 * Returns the name of the view mode.
	 */
	public String getName() {
		return this.name;
	}
	
}
