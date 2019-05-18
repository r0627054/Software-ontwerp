package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A CreateFormDesignSubWindowChangeHandler is a ChangeHandler, specifically made for
 *  handling the creation of a FormDesignSubWindow.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class CreateFormDesignSubWindowChangeHandler implements ChangeHandlerInterface {

	/**
	 * Requests all the information in the domain and gives it to the view.
	 *  The view creates a formDesignSubWindow of the table with the given UUID.
	 * 
	 * @param evt          | The propertyChangeEvent containing all the information
	 *                     of the event.
	 * @param uiFacade     | The uiFacadeInterface used.
	 * @param domainFacade | The domainFacadeInterface used.
	 */
	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID tableId = evt.getSource();
		try {
			uifacade.createFormSubWindow(tableId, (String) evt.getOldValue(), domainfacade.getTableWithIds(tableId),
					domainfacade.isComputedTable(tableId));
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}
}
