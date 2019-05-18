package controller.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * The propertyChangeSupport used in the observer pattern.
 *  It stores a list of listeners and can fire a property change to their listeners.
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class PropertyChangeSupport {

	/**
	 * Variable storing the propertyChangeListeners.
	 */
	private List<PropertyChangeListener> listeners;
	
	/**
	 * Initialises a new PropertyChangeSupport.
	 *  The listeners is set to an empty ArrayList.
	 */
	public PropertyChangeSupport() {
		listeners = new ArrayList<>();
	}
	
	/**
	 * Adds a propertyChangeListener to the list.
	 * @param listener
	 *        | The propertyChangeListener which will be added to the list.
	 * @effect Adds a propertyChangeListener to the list.
	 *        | listeners.add(listener);
	 * @throws IllegalArgumentException when the listener equals null.
	 *        | listener == null
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if(listener == null) {
			throw new IllegalArgumentException("Cannot add a null listener");
		}
		this.getListeners().add(listener);
	}
	
	/**
	 * Removes a propertyChangeListener from list.
	 * @param listener
	 *        | The propertyChangeListener which will be removed to the list.
	 * @effect Adds a propertyChangeListener to the list.
	 *        | listeners.remove(listener);
	 * @throws IllegalArgumentException when the listener equals null.
	 *        | listener == null
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if(listener == null) {
			throw new IllegalArgumentException("Cannot remove a null listener");
		}
		this.getListeners().remove(listener);
	}

	/**
	 * Returns the list of listeners.
	 */
	private List<PropertyChangeListener> getListeners() {
		return listeners;
	}
	
	/**
	 * Notifies all the listeners that a property is changed.
	 * It gives the event to all the listeners.
	 * @param evt The propertyChangeEvent which will to all the listeners.
	 * @post All the listeners are notified.
	 *       | for(PropertyChangeListener listener: getListeners())
	 *       |    listener.propertyChange(evt)
	 */
	public void firePropertyChange(PropertyChangeEvent evt) {
		for(PropertyChangeListener listener: getListeners()) {
			listener.propertyChange(evt);
		}
	}
	
	/**
	 * Returns the size of the list of listeners.
	 */
	public int size() {
		return this.getListeners().size();
	}
}
