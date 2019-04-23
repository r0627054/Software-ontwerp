package controller.handlers;

/**
 * An enumeration introducing the different types of change events.
 *  These are events called by components in the UI and handled by the changeHandlers.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public enum ChangeEventType {
	REPAINT(null),
			
	CREATE_TABLE(CreateTableChangeHandler.class), 
	CREATE_ROW(CreateRowChangeHandler.class),
	CREATE_COLUMN(CreateColumnChangeHandler.class),
	
	DELETE_TABLE(DeleteTableChangeHandler.class),
	DELETE_ROW(DeleteRowChangeHandler.class),
	DELETE_COLUMN(DeleteColumnChangeHandler.class), 
	
	COLUMN_CHANGE_NAME(ColumnNameChangeHandler.class),
	COLUMN_CHANGE_TYPE(ColumnTypeChangeHandler.class), 
	COLUMN_CHANGE_DEFAULT_VALUE(ColumnDefaultValueChangeHandler.class), 
	COLUMN_CHANGE_ALLOW_BLANKS(ColumnAllowBlanksChangeHandler.class),
	
	TABLE_CHANGE_NAME(TableNameChangeHandler.class),
	ROW_EDITED(RowEditedChangeHandler.class),
	
	OPEN_TABLESUBWINDOW(OpenTableSubWindowChangeHandler.class),
	CLOSE_SUBWINDOW(CloseSubWindowChangeHandler.class), 
	CREATE_TABLEROWSWINDOW(CreateTableRowsSubWindowChangeHandler.class), 
	CREATE_TABLEDESIGNWINDOW(CreateTableDesignSubWindowChangeHandler.class),
	CREATE_TABLESSUBWINDOW(CreateTablesSubWindowChangeHandler.class);
	
	Class<?> actionHandler;
	
	private ChangeEventType(Class<?> changeHandlerClass) {
		this.setActionHandler(changeHandlerClass);
	}

	protected Class<?> getActionHandler() {
		return actionHandler;
	}

	private void setActionHandler(Class<?> actionHandler) {
		this.actionHandler = actionHandler;
	}
	
}
