package controller.handlers;

public enum ChangeEventType {

	CHECKED("checked"),
	VALUE("value"),
	DEFAULT_VALUE("defaultValue"),
	DOUBLEClICK("doubleClick"),
	CREATE_TABLE("createTable"), 
	DELETE_TABLE("deleteTable"),
	SWITCH_VIEWMODE("switchViewMode");
	
	private final String eventString;

	private ChangeEventType(String eventString) {
		this.eventString = eventString;
	}

	public String getEventString() {
		return this.eventString;
	}

}
