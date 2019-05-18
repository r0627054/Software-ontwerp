package controller.handlers;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;
/**
 * 
 * A CloseSubWindowChangeHandler is a ChangeHandler, specifically made for
 *  handling the closing behaviour of a window.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class CloseSubWindowChangeHandler implements ChangeHandlerInterface {

	/**
	 * Closes the current window in the view.
	 *  At the moment of writing no change will to be made in the domain.
	 * 
	 * @param evt          | The propertyChangeEvent containing all the information
	 *                     of the event.
	 * @param uiFacade     | The uiFacadeInterface used.
	 * @param domainFacade | The domainFacadeInterface used.
	 */
	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		try {
			uifacade.closeCurrentSubWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
