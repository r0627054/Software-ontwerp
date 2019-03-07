package controller.handlers;

public enum ChangeEventType {

	CHECKED("checked"),
	VALUE("value"),
	DEFAULT_VALUE("defaultValue");
	
	private final String eventString;
	
	private ChangeEventType(String eventString) {
		this.eventString = eventString;
	}
	
	public String getEventString() {
		return this.eventString;
	}
	
}
