package controller.handlers;

/**
 * An enumeration introducing the different types of change events.
 *  These are events called by components in the UI and handled by the changeHandlers.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public enum ChangeEventType {

	REPAINT("repaint"),
	
	OPEN_TABLEVIEWMODE("openTableViewMode"),
	SWITCH_VIEWMODE("switchViewMode"),
		
	CREATE_TABLE("createTable"), 
	CREATE_ROW("createRow"),
	CREATE_COLUMN("createColumn"),
	
	DELETE_TABLE("deleteTable"),
	DELETE_ROW("deleteRow"),
	DELETE_COLUMN("deleteColumn"), 
	
	COLUMN_CHANGE_NAME("columnChangeName"),
	COLUMN_CHANGE_TYPE("columnChangeType"), 
	COLUMN_CHANGE_DEFAULT_VALUE("columnChangeDefaultValue"), 
	COLUMN_CHANGE_ALLOW_BLANKS("columnChangeAllowBlanks"),
	
	TABLE_CHANGE_NAME("tableChangeName"),
	ROW_EDITED("rowEdited");
	
	/**
	 * Variable storing the event string.
	 */
	private final String eventString;

	/**
	 * Sets the eventString of the given ChangeEvent.
	 * @param eventString
	 *        | the eventString of the ChangeEvent.
	 * @post the eventString equals the given eventString
	 *        | this.eventString = eventString
	 */
	private ChangeEventType(String eventString) {
		this.eventString = eventString;
	}

	/**
	 * Returns the eventString of the ChangeEventType.
	 */
	public String getEventString() {
		return this.eventString;
	}

}
