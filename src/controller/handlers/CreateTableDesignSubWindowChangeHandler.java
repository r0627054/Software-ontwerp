package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A CreateTableDesignSubWindowChangeHandler is a ChangeHandler, specifically made for
 *  handling the creation of a tableDesignWindow.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class CreateTableDesignSubWindowChangeHandler implements ChangeHandlerInterface {

	/**
	 * Requests all the information in the domain and gives it to the view.
	 *  The view creates a tableDesignWindow of the table with the given UUID.
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
			uifacade.createTableDesignSubWindow(tableId, domainfacade.getTableNameOfId(tableId),
					domainfacade.getColumnCharacteristics(tableId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
