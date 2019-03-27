package controller.observer;

import java.util.UUID;

import controller.handlers.ChangeEventType;

public class PropertyChangeEvent {

	private ChangeEventType action;
	private UUID source;
	private Object oldValue;
	private Object newValue;

	public PropertyChangeEvent(UUID source, ChangeEventType action, Object oldValue, Object newValue) {
		setAction(action);
		setSource(source);
		setOldValue(oldValue);
		setNewValue(newValue);
	}

	public ChangeEventType getAction() {
		return action;
	}

	private void setAction(ChangeEventType action) {
		this.action = action;
	}

	public UUID getSource() {
		return source;
	}

	private void setSource(UUID source) {
		this.source = source;
	}

	public Object getOldValue() {
		return oldValue;
	}

	private void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}

	public Object getNewValue() {
		return newValue;
	}

	private void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

}
