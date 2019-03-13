package controller.handlers;

public enum ChangeEventType {

	REPAINT("repaint"),
	CHECKED("checked"),
	VALUE("value"),
	DEFAULT_VALUE("defaultValue"),
	DOUBLEClICK("doubleClick"),
	SWITCH_VIEWMODE("switchViewMode"),
	
	ROW_EDITED("rowEdited"),
	
	CREATE_TABLE("createTable"), 
	CREATE_ROW("createRow"),
	CREATE_COLUMN("createColumn"),
	
	DELETE_TABLE("deleteTable"),
	DELETE_ROW("deleteRow"),
	DELETE_COLUMN("deleteColumn"), 
	
	COLUMN_CHANGE_TYPE("columnChangeType"), 
	COLUMN_CHANGE_DEFAULT_VALUE("columnChangeDefaultValue"), 
	COLUMN_CHANGE_ALLOW_BLANKS("columnChangeAllowBlanks"), 
	COLUMN_CHANGE_NAME("columnChangeName");
	
	private final String eventString;

	private ChangeEventType(String eventString) {
		this.eventString = eventString;
	}

	public String getEventString() {
		return this.eventString;
	}

}
