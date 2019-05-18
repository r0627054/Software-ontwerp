package controller.observer;

/**
 * Observers in the observer pattern implement this interface.
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public interface PropertyChangeListener {

	/**
	 * The method which will be called when a property is changed.
	 * @param evt The propertyChangeEvent containing all the information of the event. 
	 */
	public void propertyChange(PropertyChangeEvent evt);
	
}
