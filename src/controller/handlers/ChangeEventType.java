package controller.handlers;

public enum ChangeEventType {

	REPAINT("repaint"),
	CHECKED("checked"),
	VALUE("value"),
	DEFAULT_VALUE("defaultValue"),
	DOUBLEClICK("doubleClick"),
	SWITCH_VIEWMODE("switchViewMode"),
	CREATE_TABLE("createTable"), 
	DELETE_TABLE("deleteTable"),
	CREATE_COLUMN("createColumn"),
	DELETE_COLUMN("deleteColumn"), 
	CREATE_ROW("createRow"),
	COLUMN_CHANGE_TYPE("columnChangeType"), 
	COLUMN_CHANGE_DEFAULT_VALUE("columnChangeDefaultValue"), 
	COLUMN_CHANGE_ALLOW_BLANKS("columnChangeAllowBlanks"), 
	COLUMN_CHANGE_NAME("columnChangeName"),
	ROW_EDITED("rowEdited");
	
	private final String eventString;

	private ChangeEventType(String eventString) {
		this.eventString = eventString;
	}

	public String getEventString() {
		return this.eventString;
	}

}
