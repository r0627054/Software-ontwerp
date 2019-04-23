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
	
	/**
	 * Variable storing the actionHandler class.
	 */
	private Class<?> actionHandler;
	
	/**
	 * Initialise a changeEventType with the corresponding class.
	 * @param changeHandlerClass The class linked with the event.
	 * @effect The changeHanlerClass is set
	 *         | this.setActionHandler(changeHandlerClass)
	 */
	private ChangeEventType(Class<?> changeHandlerClass) {
		this.setActionHandler(changeHandlerClass);
	}

	/**
	 * Returns the class of the ChangeHandler event.
	 * @return the class of the ChangeHandler event.
	 */
	protected Class<?> getActionHandler() {
		return actionHandler;
	}

	/**
	 * Sets the actionHandler class.
	 * @param actionHandler The handler class to which it will be set.
	 * @post The actionHanlder variable equals the actionHandler parameter
	 *       | new.getActionHanlder() == actionHandler
	 */
	private void setActionHandler(Class<?> actionHandler) {
		this.actionHandler = actionHandler;
	}
	
}
