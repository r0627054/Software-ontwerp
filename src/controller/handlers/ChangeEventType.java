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
	REPAINT,
	
	OPEN_TABLEVIEWMODE,
	SWITCH_VIEWMODE,
		
	CREATE_TABLE, 
	CREATE_ROW,
	CREATE_COLUMN,
	
	DELETE_TABLE,
	DELETE_ROW,
	DELETE_COLUMN, 
	
	COLUMN_CHANGE_NAME,
	COLUMN_CHANGE_TYPE, 
	COLUMN_CHANGE_DEFAULT_VALUE, 
	COLUMN_CHANGE_ALLOW_BLANKS,
	
	TABLE_CHANGE_NAME,
	ROW_EDITED,
	
	CLOSE_SUBWINDOW;
}
