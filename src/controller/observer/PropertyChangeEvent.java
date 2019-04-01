package controller.observer;

import java.util.UUID;

import controller.handlers.ChangeEventType;

/**
 * The propertyChange event contains all the information about the event which is raised. 
 * It contains the source (which is a UUID), the action (which is an ChangeEventType), oldValue (the old value of the property which is changed)
 *  and newValue (the new value of the property which is changed).
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class PropertyChangeEvent {

	/**
	 * The variable storing the Change event action of the event.
	 */
	private ChangeEventType action;
	
	/**
	 * The variable storing the UUID of which a the source which is changed.
	 */
	private UUID source;
	
	/**
	 * The variable storing the old value of the object of the source. 
	 */
	private Object oldValue;
	
	/**
	 * The variable storing the new value of the object of the source.
	 */
	private Object newValue;

	/**
	 * Initialises a new propertyChangeEvent with all the variables.
	 * @param source   The source (UUID) of the propertyChangeEvent.
	 * @param action   The action (UUID) of the propertyChangeEvent.
	 * @param oldValue The oldValue (UUID) of the propertyChangeEvent. 
	 * @param newValue The newValue (UUID) of the propertyChangeEvent.
	 * @effect All the variables are set using their setters.
	 *         |setAction(action);
	 *	       |setSource(source);
	 *	       |setOldValue(oldValue);
	 *         |setNewValue(newValue);
	 */
	public PropertyChangeEvent(UUID source, ChangeEventType action, Object oldValue, Object newValue) {
		setAction(action);
		setSource(source);
		setOldValue(oldValue);
		setNewValue(newValue);
	}

	/**
	 * Returns the action (ChangeEventType) of the event.
	 */
	public ChangeEventType getAction() {
		return action;
	}

	/**
	 * Sets the action of the event.
	 * @param action The action of the event.
	 * @post The action of the event equals the action parameter.
	 *       | new.getAction() == action
	 */
	private void setAction(ChangeEventType action) {
		this.action = action;
	}

	/**
	 * Returns the source (UUID) of the event.
	 */
	public UUID getSource() {
		return source;
	}

	/**
	 * Sets the source of the event.
	 * @param source The source of the event.
	 * @post The source of the event equals the source parameter.
	 *        | new.getSource() == source
	 */
	private void setSource(UUID source) {
		this.source = source;
	}

	/**
	 * Returns the old value of the action.
	 */
	public Object getOldValue() {
		return oldValue;
	}

	/**
	 * Sets the oldValue of the event.
	 * @param oldValue The old value of the event.
	 * @post The old value of the event equals the oldValue parameter.
	 *       |new.getOldValue() == oldValue
	 */
	private void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}

	/**
	 * Returns the new value of the event.
	 */
	public Object getNewValue() {
		return newValue;
	}

	/**
	 * Sets the new value of the event.
	 * @param newValue The new value of the event.
	 * @post The new value of the event equals the newValue parameter.
	 *       |new.getNewValue() == newValue
	 */
	private void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

}
