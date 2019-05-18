package controller.handlers;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * The changeHandlerFactory is a (simple) factory class delegating the differentEventTypes to the corresponding handlers.
 *  
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public final class ChangeHandlerFactory {

	/**
	 * Method used for handling all the change events.
	 * It delegates the different events to the corresponding changeHandler.
	 * 
	 * @param evt
	 *        | The propertyChangeEvent containing the information about the event.
	 * @param uifacade
	 *        | The UIFacadeInterface used in the controller.
	 * @param domainfacade
	 *        | The DomainFacadeInterface used in the controller.
	 * @effect The handler is created and all the information is passed to the specific handler. This handler handles the change.
	 */
	public static void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade,
			DomainFacadeInterface domainfacade) {
		try {
			ChangeEventType value = evt.getAction();
			ChangeHandlerInterface handler = (ChangeHandlerInterface) value.getActionHandler().newInstance();

			if (handler != null) {
				handler.handleChange(evt, uifacade, domainfacade);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
